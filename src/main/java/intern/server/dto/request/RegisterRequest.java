package intern.server.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(min = 5, max = 20, message = "Tên người dùng phải có độ dài từ 5 đến 20 ký tự")
    private String userName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String password;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(min = 10, max = 15, message = "Số điện thoại phải có độ dài từ 10 đến 15 ký tự")
    private String phone;

    @Size(max = 2000, message = "Ảnh đại diện không được vượt quá 2000 ký tự")
    private String avatar;

    @NotBlank(message = "ID Role không được để trống")
    private String idRole;

    @NotBlank(message = "Tên không được để trống")
    private String name;

}
