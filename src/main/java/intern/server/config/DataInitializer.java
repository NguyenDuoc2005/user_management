package intern.server.config;

import intern.server.entity.Role;
import intern.server.entity.User;
import intern.server.entity.UserRole;
import intern.server.repository.RoleRepository;
import intern.server.repository.UserRoleRepository;
import intern.server.repository.auth.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AuthUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Value("${user.default.name}")
    private String name;

    @Value("${user.default.email}")
    private String email;

    @Value("${user.default.password}")
    private String password;

    @Value("${user.default.roles}")
    private String roles;  // Lấy danh sách roles từ application.properties

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra và tạo role mặc định: ADMIN và USER nếu chưa có
        Role userRole = roleRepository.findByCode("USER").orElseGet(() -> roleRepository.save(new Role("USER", "User")));
        Role adminRole = roleRepository.findByCode("ADMIN").orElseGet(() -> roleRepository.save(new Role("ADMIN", "Admin")));

        // Kiểm tra và tạo người dùng mặc định nếu chưa có
        userRepository.findByEmail(email).ifPresentOrElse(existingUser -> {
            // Nếu người dùng đã tồn tại, không tạo mới
            System.out.println("User with email " + email + " already exists.");
        }, () -> {
            // Nếu người dùng chưa tồn tại, tạo mới
            User newUser = new User();
            newUser.setUserName(email);
            newUser.setEmail(email);

            // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);
            newUser.setPassWord(encodedPassword);  // Lưu mật khẩu đã mã hóa

            newUser.setName(name);
            newUser.setPhone("1234567890");  // Mặc định, có thể thay đổi
            newUser.setAvatar("default-avatar.png");  // Mặc định, có thể thay đổi

            userRepository.save(newUser);

            // Phân tách các roles và gán cho user
            List<String> roleCodes = Arrays.asList(roles.split(","));
            for (String roleCode : roleCodes) {
                Role role = roleRepository.findByCode(roleCode.trim()).orElseGet(() -> roleRepository.save(new Role(roleCode.trim(), roleCode.trim())));
                userRoleRepository.save(new UserRole(newUser, role));  // Gán từng role cho người dùng
            }
        });
    }
}
