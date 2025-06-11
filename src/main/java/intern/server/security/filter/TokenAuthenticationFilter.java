package intern.server.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import intern.server.common.base.ResponseObject;
import intern.server.security.service.CustomUserDetailsService;
import intern.server.security.service.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Setter(onMethod_ = @Autowired)
    private TokenProvider tokenProvider;

    @Setter(onMethod_ = @Autowired)
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (!StringUtils.hasText(jwt)) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Không có mã thông báo JWT nào được cung cấp trong tiêu đề Ủy quyền");
                return;
            }

            if (!tokenProvider.validateToken(jwt)) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Mã thông báo JWT không hợp lệ hoặc đã hết hạn");
                return;
            }

            String userId = tokenProvider.getUserIdFromToken(jwt);
            String userEmail = tokenProvider.getEmailFromToken(jwt);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

            if (userDetails == null) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Không tìm thấy người dùng cho mã thông báo được cung cấp");
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (UsernameNotFoundException ex) {
            log.error("User not found", ex);
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Không tìm thấy người dùng cho mã thông báo được cung cấp");
        } catch (Exception ex) {
            log.error("Không thể thiết lập xác thực người dùng trong ngữ cảnh bảo mật", ex);
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Xác thực không thành công: " + ex.getMessage());
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        ResponseObject<Object> errorResponse = ResponseObject.errorForward(message, status);
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), errorResponse);
    }
}