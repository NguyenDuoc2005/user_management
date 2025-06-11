package intern.server.entity;

import intern.server.common.constant.EntityProperties;
import intern.server.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends PrimaryEntity implements Serializable {

    @Size(max = EntityProperties.LENGTH_NAME)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = EntityProperties.LENGTH_NAME)
    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    @Size(max = EntityProperties.LENGTH_PICTURE)
    @Column(name = "password", nullable = false)
    private String passWord;

    @Size(max = EntityProperties.LENGTH_NAME)
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(max = EntityProperties.LENGTH_NAME)
    @Column(name = "phone", nullable = false)
    private String phone;

    @Size(max = EntityProperties.LENGTH_PICTURE)
    @Column(name = "avatar", length = EntityProperties.LENGTH_PICTURE)
    private String avatar;

}
