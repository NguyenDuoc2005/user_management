package intern.server.repository;

import intern.server.entity.Role;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByCode(@Size(max = 255) String code);
}
