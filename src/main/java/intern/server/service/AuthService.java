package intern.server.service;

import intern.server.common.base.ResponseObject;
import intern.server.dto.request.RegisterRequest;
import jakarta.validation.Valid;

public interface AuthService {

    ResponseObject<?> login(String email, String password);

    ResponseObject<?> register(@Valid RegisterRequest request);

    ResponseObject<?> getAllRole();

}
