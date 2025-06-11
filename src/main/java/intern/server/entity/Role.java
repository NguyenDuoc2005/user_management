package intern.server.entity;

import intern.server.common.constant.EntityProperties;
import intern.server.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role extends PrimaryEntity implements Serializable {

    @Size(max = EntityProperties.LENGTH_CODE)
    @Column(name = "code")
    private String code;

    @Size(max = EntityProperties.LENGTH_NAME)
    @Column(name = "name")
    private String name;

}
