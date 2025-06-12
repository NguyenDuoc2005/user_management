package intern.server.dto.request;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateUserRequest {

    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(max = 255, message = "Tên người dùng không được vượt quá 255 ký tự")
    private String name;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(max = 255, message = "Tên đăng nhập không được vượt quá 255 ký tự")
    private String userName;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max = 255, message = "Mật khẩu không được vượt quá 255 ký tự")
    private String passWord;

    @Size(max = 2000, message = "Ảnh đại diện không được vượt quá 2000 ký tự")
    private String avatar;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 255, message = "Số điện thoại không được vượt quá 255 ký tự")
    private String phone;

    @NotBlank(message = "Email không được để trống")
    @Size(max = 255, message = "Email không được vượt quá 255 ký tự")
    private String email;
}
