package intern.server.utils;


import intern.server.security.service.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserContextHelper {

    @Setter(onMethod = @__({@Autowired}))
    private TokenProvider tokenProvider;

    @Setter(onMethod = @__({@Autowired}))
    private HttpServletRequest request;

    public String getCurrentUserId() {
        String jwt = getJwtFromRequest(request);
        System.out.println("jwtId : " + jwt);
        return tokenProvider.getUserIdFromToken(jwt);
    }

    public String getCurrentUserEmail() {
        String jwt = getJwtFromRequest(request);
        System.out.println("jwt: " + jwt);
        return tokenProvider.getEmailFromToken(jwt);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
