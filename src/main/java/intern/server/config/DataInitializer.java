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
    private String roles;

    @Override
    public void run(String... args) throws Exception {

        Role userRole = roleRepository.findByCode("USER").orElseGet(() -> roleRepository.save(new Role("USER", "User")));

        Role adminRole = roleRepository.findByCode("ADMIN").orElseGet(() -> roleRepository.save(new Role("ADMIN", "Admin")));

        userRepository.findByEmail(email).ifPresentOrElse(existingUser -> {

            System.out.println("User with email " + email + " already exists.");
        }, () -> {

            User newUser = new User();
            newUser.setUserName(email);
            newUser.setEmail(email);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);
            newUser.setPassWord(encodedPassword);

            newUser.setName(name);
            newUser.setPhone("1234567890");
            newUser.setAvatar("default-avatar.png");

            userRepository.save(newUser);

            List<String> roleCodes = Arrays.asList(roles.split(","));
            for (String roleCode : roleCodes) {
                Role role = roleRepository.findByCode(roleCode.trim()).orElseGet(() -> roleRepository.save(new Role(roleCode.trim(), roleCode.trim())));
                userRoleRepository.save(new UserRole(newUser, role));
            }
        });
    }
}
