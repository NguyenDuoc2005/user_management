package intern.server.repository.auth;

import intern.server.dto.response.RoleResponse;
import intern.server.entity.Role;
import intern.server.repository.RoleRepository;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthRoleRepository extends RoleRepository {

    @Query("""
select r.id as id ,
        r.name as name
 from Role r
""")
    List<RoleResponse> getRoles();

    @Query(
            value = """
                    SELECT DISTINCT
                          r.code
                      FROM
                          Role r
                      LEFT JOIN
                       UserRole sr ON r.id = sr.role.id
                      WHERE
                          sr.user.id = :id
                    """
    )
    List<String> findRoleByUserId(@Param("id") String id);

    @Query(
            value = """

                    SELECT DISTINCT
                          r.name
                      FROM
                          Role r
                      LEFT JOIN
                       UserRole sr ON r.id = sr.role.id
                      WHERE
                          sr.user.id = :id
                    """
    )
    List<String> findRoleNameByUserId(@Param("id") String id);

    Role findRoleByName(@Size(max = 255) String name);

    Role findRoleByCode(@Size(max = 255) String code);

}
