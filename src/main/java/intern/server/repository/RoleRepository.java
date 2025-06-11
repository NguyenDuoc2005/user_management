package intern.server.repository;

import intern.server.common.constant.EntityProperties;
import intern.server.entity.Role;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByCode(@Size(max = EntityProperties.LENGTH_CODE) String code);
}
