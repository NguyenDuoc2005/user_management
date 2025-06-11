package intern.server.service;

import intern.server.common.base.ResponseObject;
import intern.server.dto.request.CreateUpdateUserRequest;
import intern.server.dto.request.UserRequest;
import jakarta.validation.Valid;

public interface UserService {

    ResponseObject<?> getAllUser(UserRequest request);

    ResponseObject<?> createUser(@Valid CreateUpdateUserRequest request);

    ResponseObject<?> updateUser(String userId, @Valid CreateUpdateUserRequest request);

    ResponseObject<?> changeUserStatus(String userId);

    ResponseObject<?> getUserById(String userId);

}
