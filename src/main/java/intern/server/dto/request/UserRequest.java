package intern.server.dto.request;

import intern.server.common.base.PageableRequest;
import intern.server.common.constant.EntityStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest extends PageableRequest {

    private String email;

    private String phone;

    private EntityStatus userStatus;

}
