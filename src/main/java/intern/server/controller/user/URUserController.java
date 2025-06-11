package intern.server.controller.user;

import intern.server.common.constant.MappingConstants;
import intern.server.dto.request.CreateUpdateUserRequest;
import intern.server.service.UserService;
import intern.server.utils.Helper;
import intern.server.utils.UserContextHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingConstants.API_USER_USER_MANAGEMENT)
public class URUserController {

    private final UserService userService;

    private final UserContextHelper userContextHelper;

    @GetMapping("/my-information")
    public ResponseEntity<?> getByIdUser() {
        return Helper.createResponseEntity(userService.getUserById(userContextHelper.getCurrentUserId()));
    }

    @PutMapping("/update-account")
    public ResponseEntity<?> updateMyAccount(@RequestBody CreateUpdateUserRequest request) {
        return Helper.createResponseEntity(userService.updateUser(userContextHelper.getCurrentUserId(), request));
    }

}
