# Project Name

RestApi quản lý Người dùng với spring boot

## Prerequisites

Trước khi bắt đầu, bạn cần đảm bảo rằng máy tính của bạn đã cài đặt các phần mềm sau:

- [Docker](https://www.docker.com/get-started) (Cùng với Docker Compose nếu bạn sử dụng)
    - Bạn có thể tải Docker Desktop cho Windows/Mac hoặc Docker Engine cho Linux từ [đây](https://www.docker.com/get-started).

**Lưu ý**: Nếu bạn chưa cài Docker, vui lòng tham khảo [hướng dẫn cài Docker](https://docs.docker.com/get-docker/) cho hệ điều hành của bạn.

## Cài đặt và chạy ứng dụng

### 1. Clone repository
Đầu tiên, clone repository về máy
### 2. Xây dựng Docker image
docker build -t my_backend_image:tag-v1 .
### 3. Chạy ứng dụng với Docker Compose
docker-compose up --build
### 4. Truy cập ứng dụng theo cổng trong docker-compose

## API Documentation

### 1. Public Role APIs (Các API này có thể truy cập công khai, không yêu cầu đăng nhập.)

#### 1.1 Login
Method: POST 
Endpoint: http://localhost:9999/api/v1/auth/login
Description: Đăng nhập vào hệ thống và nhận accessToken và refreshToken.
Request Body:
{
"email": "admin@gmail.com",
"password": "12345678"
}

#### 1.2 Register
Method: POST
Endpoint: http://localhost:9999/api/v1/auth/register
Description: Đăng ký người dùng mới.
Request Body:
{
"userName": "john_do13e",
"email": "admin@gmail.com",
"password": "Duoc2005",
"phone": "0366994505",
"avatar": "https://example.com/avatar.jpg",
"idRole": "",  
"name": "John Doe 1f"
}

#### 1.3 Get All Roles
Method: GET
Endpoint: http://localhost:9999/api/v1/auth/all-role
Description: Lấy tất cả các vai trò trong hệ thống.

### 2. Admin Role APIs (Các API này yêu cầu token của người dùng có role Admin )

#### 2.1 Get All user 
Method: GET
Endpoint: http://localhost:9999/api/v1/admin/user
Description:  Lấy danh sách tất cả người dùng có phân trang và tìm kiếm

#### 2.3 Create User
Method: POST
Endpoint: http://localhost:9999/api/v1/admin/user/create
Description: Tạo người dùng mới.
Request Body:
{
"name": "Nguyễn Hoàng Dược",
"userName": "duocnguyen",
"passWord": "12345678",
"avatar": "https://example.com/avatar.jpg",
"phone": "0366994565",
"email": "duocnguyen@gmail.com"
}
#### 2.4 Update User
Method: PUT
Endpoint: http://localhost:9999/api/v1/admin/user/update/{userId}
Description: Cập nhật người dùng mới.
Request Body:
{
"name": "Nguyễn Hoàng Dược",
"userName": "duocnguyen",
"passWord": "12345678",
"avatar": "https://example.com/avatar.jpg",
"phone": "0366994565",
"email": "duocnguyen@gmail.com"
}

#### 2.5 Delete User
Method: PUT
Endpoint: http://localhost:9999/api/v1/admin/user/delete/{userId}
Description: Xóa mềm người dùng ( khi đã xóa thì ko thể login tài khoản đó đc nx )

#### 2.5 Get User By ID
Method: GET
Endpoint: http://localhost:9999/api/v1/admin/user/{userId}
Description:  Lấy thông tin chi tiết người dùng theo userId.

### 3. User Role APIs (Các API này yêu cầu token của người dùng có role User )

#### 3.1 Get My Information
Method: GET
Endpoint: http://localhost:9999/api/v1/user/user/my-information
Description: Lấy thông tin tài khoản của người dùng hiện tại theo token gửi lên

#### 3.2 Update My Account
Method: PUT
Endpoint: http://localhost:9999/api/v1/user/user/update-account
Description: Cập nhật thông tin tài khoản của người dùng hiện tại theo token gửi lên
Request Body:
{
"name": "Nguyễn Hoàng Dược",
"userName": "duocnguyen",
"passWord": "12345678",
"avatar": "https://example.com/avatar.jpg",
"phone": "0366994565",
"email": "duocnguyen@gmail.com"
}