package intern.server.controller.auth;

import intern.server.common.base.ResponseObject;
import intern.server.common.constant.MappingConstants;
import intern.server.dto.request.LoginRequest;
import intern.server.dto.request.RegisterRequest;
import intern.server.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import intern.server.utils.Helper;

@RestController
@RequiredArgsConstructor
@RequestMapping(MappingConstants.API_AUTH_PREFIX)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseObject<?> responseObject =
                authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return Helper.createResponseEntity(responseObject);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return Helper.createResponseEntity(authService.register(request));
    }

    @GetMapping("/all-role")
    public ResponseEntity<?> getAllRole() {
        return Helper.createResponseEntity(authService.getAllRole());
    }

}

