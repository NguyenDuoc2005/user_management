package intern.server.service.impl;

import intern.server.common.base.PageableObject;
import intern.server.common.base.ResponseObject;
import intern.server.common.constant.EntityStatus;
import intern.server.common.constant.Roles;
import intern.server.dto.request.CreateUpdateUserRequest;
import intern.server.dto.request.UserRequest;
import intern.server.entity.Role;
import intern.server.entity.User;
import intern.server.entity.UserRole;
import intern.server.repository.user.RoleExtendsRepository;
import intern.server.repository.user.UserExtendsRepository;
import intern.server.repository.user.UserRoleExtendsRepository;
import intern.server.service.UserService;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Repository
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserExtendsRepository maUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleExtendsRepository roleRepository;

    private final UserRoleExtendsRepository userRoleExtendsRepository;

    @Override
    public ResponseObject<?> getAllUser(UserRequest request) {
        Pageable pageable = Helper.createPageable(request, "createdDate");
        return new ResponseObject<>(
                PageableObject.of(maUserRepository.getAllUsers( pageable,request)),
                HttpStatus.OK,
                "Lấy tất cả người dùng thành công"
        );
    }

    @Override
    public ResponseObject<?> createUser(CreateUpdateUserRequest request) {
        List<User> userOptional = maUserRepository.findAllByEmail(request.getEmail().trim());
        if (!userOptional.isEmpty()) {
            return new ResponseObject<>(null, HttpStatus.BAD_REQUEST, "Email đã tồn tại");
        }
        User user = new User();
        user.setName(request.getName());
        user.setUserName(request.getUserName());
        user.setStatus(EntityStatus.ACTIVE);
        user.setAvatar(request.getAvatar());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        maUserRepository.save(user);
        Optional<Role> role = roleRepository.findByCode(Roles.USER.name());
        if(role.isEmpty()) {
            new ResponseObject<>(null,HttpStatus.NOT_FOUND,"Chưa có vai trò");
        }
        UserRole userRole = new UserRole(user, role.get());
        userRoleExtendsRepository.save(userRole);
        return new ResponseObject<>(userRole, HttpStatus.CREATED, "Thêm người dùng thành công");
    }

    @Override
    public ResponseObject<?> updateUser(String userId, CreateUpdateUserRequest request) {
        Optional<User> userOptional = maUserRepository.findById(userId);
        if(userOptional.isEmpty()) {
          return new ResponseObject<>(null,HttpStatus.NOT_FOUND,"Id Người dùng không tồn tại");
        }
        User user = userOptional.get();
        user.setName(request.getName());
        user.setUserName(request.getUserName());
        user.setStatus(EntityStatus.ACTIVE);
        user.setAvatar(request.getAvatar());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        maUserRepository.save(user);
        return new ResponseObject<>(user, HttpStatus.CREATED, "Sửa người dùng thành công");
    }

    @Override
    public ResponseObject<?> changeUserStatus(String userId) {
        Optional<User> userOptional = maUserRepository.findById(userId);
        userOptional.map(user -> {
            user.setStatus(user.getStatus() != EntityStatus.ACTIVE ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
            return maUserRepository.save(user);
        });
        return userOptional
                .map(user -> new ResponseObject<>(user.getStatus(), HttpStatus.OK, "Đổi trạng thái người dùng thành công"))  // Trả về dữ liệu người dùng
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy người dùng"));
    }

    @Override
    public ResponseObject<?> getUserById(String userId) {
        return maUserRepository.getAllUsersById(userId)
                .map(subject -> new ResponseObject<>(subject, HttpStatus.OK, "Lấy thông tin người dùng thành công"))
                .orElseGet(() -> new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Không tìm thấy người dùng"));
    }

}
