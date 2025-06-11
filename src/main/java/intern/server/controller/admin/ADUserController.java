package intern.server.controller.admin;

import intern.server.common.constant.MappingConstants;
import intern.server.dto.request.CreateUpdateUserRequest;
import intern.server.dto.request.UserRequest;
import intern.server.service.UserService;
import intern.server.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingConstants.API_ADMIN_USER_MANAGEMENT)
public class ADUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> get(UserRequest request) {
        return Helper.createResponseEntity(userService.getAllUser(request));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUpdateUserRequest request) {
        return Helper.createResponseEntity(userService.createUser(request));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody CreateUpdateUserRequest request) {
        return Helper.createResponseEntity(userService.updateUser(userId, request));
    }

    @PutMapping("/delete/{userId}")
    public ResponseEntity<?> changeStatusUser(@PathVariable String userId) {
        return Helper.createResponseEntity(userService.changeUserStatus(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getByIdUser(@PathVariable String userId) {
        return Helper.createResponseEntity(userService.getUserById(userId));
    }

}
