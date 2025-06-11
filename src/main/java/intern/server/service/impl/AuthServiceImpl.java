package intern.server.service.impl;

import intern.server.common.base.ResponseObject;
import intern.server.common.constant.EntityStatus;
import intern.server.dto.request.RegisterRequest;
import intern.server.entity.Role;
import intern.server.entity.User;
import intern.server.entity.UserRole;
import intern.server.repository.UserRoleRepository;
import intern.server.repository.auth.AuthRoleRepository;
import intern.server.repository.auth.AuthUserRepository;
import intern.server.repository.auth.AuthUserRoleRepository;
import intern.server.security.user.AuthTokens;
import intern.server.security.service.TokenProvider;
import intern.server.security.user.UserPrincipal;
import intern.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Repository
@Validated
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;

    private final AuthUserRoleRepository authUserRoleRepository;

    private final AuthRoleRepository authRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public ResponseObject<?> login(String email, String password) {
        try {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            String accessToken = tokenProvider.createToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);

            return new ResponseObject<>(new AuthTokens(accessToken, refreshToken), HttpStatus.OK, "Lấy token thành công");

        } catch (BadCredentialsException ex) {

            return new ResponseObject<>(null, HttpStatus.UNAUTHORIZED, "Email hoặc mật khẩu không đúng");
        } catch (Exception ex) {

            return new ResponseObject<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi: " + ex.getMessage());
        }
    }

    @Override
    public ResponseObject<?> register(RegisterRequest request) {

        Optional<User> existingUser = authUserRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND,"Email đã tồn tại");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Optional<Role> role = authRoleRepository.findById(request.getIdRole());

        if (role.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.NOT_FOUND,"Vai trò người dùng không hợp lệ");
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setUserName(request.getUserName());
        newUser.setEmail(request.getEmail());
        newUser.setPassWord(encodedPassword);
        newUser.setPhone(request.getPhone());
        newUser.setStatus(EntityStatus.ACTIVE);
        authUserRepository.save(newUser);
        UserRole userRole = new UserRole();
        userRole.setRole(role.get());
        userRole.setUser(newUser);

        return new ResponseObject<>(authUserRoleRepository.save(userRole), HttpStatus.OK,"Đăng ký thành công");

    }

    @Override
    public ResponseObject<?> getAllRole() {
        return new ResponseObject<>(authRoleRepository.getRoles(),HttpStatus.OK,"lấy thành công");
    }

}
