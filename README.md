# TopJob

TopJob là một ứng dụng web giúp người dùng tìm kiếm việc làm và quản lý thông tin cá nhân. Ứng dụng được xây dựng bằng Spring MVC và Thymeleaf, cung cấp nhiều tính năng tiện lợi cho cả ứng viên và nhà tuyển dụng.

## Các tính năng chính

1. **Đăng nhập và Đăng ký**
   - Người dùng có thể tạo tài khoản mới hoặc đăng nhập vào tài khoản hiện có.
   - Hỗ trợ đăng nhập bằng Google.

2. **Quản lý thông tin tài khoản**
   - Người dùng có thể cập nhật thông tin cá nhân, thông tin liên hệ, và các thông tin khác liên quan đến tài khoản của mình.

3. **Tìm kiếm và lọc việc làm**
   - Người dùng có thể tìm kiếm công việc theo từ khóa, địa điểm, và các tiêu chí khác.
   - Cung cấp tính năng lọc kết quả tìm kiếm theo nhiều tiêu chí khác nhau.

4. **Ứng tuyển việc làm**
   - Người dùng có thể ứng tuyển cho các công việc mà họ quan tâm trực tiếp thông qua ứng dụng.

5. **Tìm kiếm và theo dõi công ty**
   - Người dùng có thể tìm kiếm các công ty theo từ khóa và các tiêu chí khác.
   - Cung cấp tính năng theo dõi công ty để nhận thông tin về các việc làm mới nhất từ công ty đó.

6. **Tải lên và quản lý CV**
   - Người dùng có thể tải lên và quản lý các phiên bản CV của mình trực tiếp trên ứng dụng.

## Yêu cầu hệ thống

- Java 8 hoặc mới hơn
- Spring Framework 5.x hoặc mới hơn
- Thymeleaf 3.x hoặc mới hơn
- Maven 3.x hoặc mới hơn
- MySQL hoặc bất kỳ cơ sở dữ liệu SQL nào

## Cài đặt và cấu hình

1. **Clone repository**

   ```sh
   git clone https://github.com/username/topjob.git
   cd topjob
2. **Cấu hình cơ sở dữ liệu**
   
   Mở file src/main/resources/application.properties và cập nhật thông tin
3. **Cài đặt các phụ thuộc**

   Sử dụng Maven để cài đặt các phụ thuộc:
   ```sh
   mvn clean install
4. **Chạy ứng dụng**

   Sử dụng lệnh sau để khởi động ứng dụng:
   ```sh
   mvn spring-boot:run

   Ứng dụng sẽ chạy trên http://localhost:8080.
