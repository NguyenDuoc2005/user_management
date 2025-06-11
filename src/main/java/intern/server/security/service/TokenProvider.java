package intern.server.security.service;

import intern.server.common.base.ResponseObject;
import intern.server.entity.User;
import intern.server.repository.auth.AuthRoleRepository;
import intern.server.repository.auth.AuthUserRepository;
import intern.server.security.user.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@Slf4j
public class TokenProvider {

    @Value("${jwt.secret}")
    private String tokenSecret;

    private final long TOKEN_EXPIRATION_TIME = 2 * 60 * 60 * 1000;
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;

    @Setter(onMethod_ = @Autowired)
    private AuthUserRepository userAuthRepository;

    @Setter(onMethod_ = @Autowired)
    private AuthRoleRepository roleAuthRepository;

    @Setter(onMethod_ = @Autowired)
    private HttpServletRequest httpServletRequest;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String email = userPrincipal.getEmail();

        Optional<User> user = userAuthRepository.findByEmail(email);
        if (user.isEmpty()) {
            new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Tài khoản không tồn tại");
        }

        List<String> rolesCode = roleAuthRepository.findRoleByUserId(user.get().getId());
        List<String> roleName = roleAuthRepository.findRoleNameByUserId(user.get().getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("rolesCode", rolesCode);
        claims.put("rolesName", roleName);
        claims.put("userId", user.get().getId());

        return Jwts.builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .setIssuer("user_management")
                .signWith(Keys.hmacShaKeyFor(tokenSecret.getBytes()))
                .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String email = userPrincipal.getEmail();

        Optional<User> user = userAuthRepository.findByEmail(email);
        if (user.isEmpty()) {
            new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Tài khoản không tồn tại");
        }

        List<String> rolesCode = roleAuthRepository.findRoleByUserId(user.get().getId());
        List<String> roleName = roleAuthRepository.findRoleNameByUserId(user.get().getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("rolesCode", rolesCode);
        claims.put("rolesName", roleName);
        claims.put("userId", user.get().getId());

        return Jwts.builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .setIssuer("user_management")
                .signWith(Keys.hmacShaKeyFor(tokenSecret.getBytes()))
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(tokenSecret.getBytes()))
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
        }
        return false;
    }

    private Claims getClaimsToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(tokenSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = getClaimsToken(token);
        return claims.getSubject();
    }

    public String getEmailFromToken(String token) {
        Claims claims = getClaimsToken(token);
        return claims.get("email", String.class);
    }

}
