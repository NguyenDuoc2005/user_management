package intern.server.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateUserRequest {

    @Size(max = 255, message = "Tên người dùng không được vượt quá 255 ký tự")
    private String name;

    @Size(max = 255, message = "Tên người dùng không được vượt quá 255 ký tự")
    private String userName;

    @Size(max = 255, message = "Mật khẩu không được vượt quá 255 ký tự")
    private String passWord;

    @Size(max = 2000, message = "Ảnh đại diện không được vượt quá 2000 ký tự")
    private String avatar;

    @Size(max = 255, message = "Số điện thoại không được vượt quá 255 ký tự")
    private String phone;

    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String address;

    @Size(max = 255, message = "Chuyên ngành không được vượt quá 255 ký tự")
    private String major;

    @Size(max = 255, message = "Email không được vượt quá 255 ký tự")
    private String email;

}
