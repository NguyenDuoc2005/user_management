package intern.server.repository.user;

import intern.server.dto.request.UserRequest;
import intern.server.dto.response.UserResponse;
import intern.server.entity.User;
import intern.server.repository.UserRepository;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserExtendsRepository extends UserRepository{

    @Query(
            value = """
        SELECT u.id AS id,
               u.name AS name,
               u.userName AS userName,
               u.email AS email,
               u.phone AS phone,
               u.status AS status,
               u.avatar AS avatar,
               u.createdDate AS createdDate
        FROM UserRole ur
             JOIN ur.user u
             JOIN ur.role r
        WHERE
            (:#{#request.email} IS NULL OR u.email LIKE CONCAT('%', :#{#request.email}, '%'))
            AND 
           (:#{#request.phone} IS NULL OR u.phone LIKE CONCAT('%', :#{#request.phone}, '%'))
             AND
            (:#{#request.userStatus} IS NULL OR u.status = :#{#request.userStatus})
    """
    )
    Page<UserResponse> getAllUsers(Pageable pageable, UserRequest request);

    List<User> findAllByEmail(@Size(max = 255) String email);

    @Query(
            value = """
        SELECT
               u.id AS id,
               u.name AS name,
               u.userName AS userName,
               u.email AS email,
               u.phone AS phone,
               u.status AS status,
               u.avatar AS avatar,
               u.createdDate AS createdDate
        FROM User u
            where u.id = :id
    """
    )
    Optional<UserResponse> getAllUsersById(@Param("id") String id);

    User findUserById(String id);

}
