# NUTRI-FIT

NUTRI-FIT là một ứng dụng Android dành cho việc theo dõi dinh dưỡng và tập luyện thể dục. Ứng dụng giúp người dùng quản lý bữa ăn, lịch tập luyện, theo dõi mục tiêu sức khỏe và nhiều tính năng khác để duy trì lối sống lành mạnh.

## Sơ đồ Use Case Tổng thể Hệ thống

Sơ đồ Use Case dưới đây mô tả các tương tác chính giữa các actor (người dùng và hệ thống) với hệ thống NUTRI-FIT. Chúng tôi sử dụng hai actor chính: **Người dùng** (User) và **Hệ thống** (System), đại diện cho các quy trình tự động như gợi ý và thông báo.

### Actor:
- **Người dùng (User)**: Người sử dụng ứng dụng, thực hiện các hành động như đăng nhập, xem bữa ăn, tập luyện, v.v.
- **Hệ thống (System)**: Đại diện cho các chức năng tự động của ứng dụng, như gửi thông báo, gợi ý bữa ăn hoặc bài tập dựa trên dữ liệu người dùng.

### Use Cases chính:
- **Người dùng (User)**:
  1. **Đăng nhập (Login)**: Người dùng đăng nhập vào ứng dụng.
  2. **Đăng ký (Register)**: Người dùng tạo tài khoản mới.
  3. **Thiết lập hồ sơ (Set Profile)**: Người dùng nhập thông tin cá nhân.
  4. **Thiết lập mục tiêu (Set Targets)**: Người dùng đặt mục tiêu sức khỏe (ví dụ: giảm cân, tăng cơ).
  5. **Xem trang chủ (View Home Dashboard)**: Người dùng xem tổng quan về tiến độ hàng ngày.
  6. **Duyệt bữa ăn (Browse Meals)**: Người dùng xem danh sách bữa ăn gợi ý.
  7. **Xem chi tiết bữa ăn (View Meal Details)**: Người dùng xem thông tin chi tiết của một bữa ăn.
  8. **Duyệt bài tập (Browse Workouts)**: Người dùng xem danh sách bài tập.
  9. **Xem chi tiết bài tập (View Workout Details)**: Người dùng xem hướng dẫn chi tiết của bài tập.
  10. **Lên lịch tập luyện (Schedule Workouts)**: Người dùng sắp xếp lịch tập.
  11. **Ghi nhật ký hàng ngày (Log Daily Activities)**: Người dùng ghi lại hoạt động hàng ngày.
  12. **Quét sản phẩm (Scan Items)**: Người dùng quét mã vạch để thêm thực phẩm.
  13. **Xem bản đồ phòng gym (View Gym Map)**: Người dùng tìm phòng gym gần nhất.

- **Hệ thống (System)**:
  14. **Xác thực người dùng (Authenticate User)**: Hệ thống xác thực thông tin đăng nhập của người dùng.
  15. **Lưu trữ dữ liệu hồ sơ (Store Profile Data)**: Hệ thống lưu trữ thông tin cá nhân của người dùng.
  16. **Lưu trữ dữ liệu mục tiêu (Store Target Data)**: Hệ thống lưu trữ mục tiêu sức khỏe của người dùng.
  17. **Lưu trữ dữ liệu bữa ăn (Store Meal Data)**: Hệ thống lưu trữ thông tin bữa ăn và nhật ký dinh dưỡng.
  18. **Lưu trữ dữ liệu bài tập (Store Workout Data)**: Hệ thống lưu trữ thông tin bài tập và lịch tập luyện.
  19. **Tính toán tiến độ (Calculate Progress)**: Hệ thống tính toán và cập nhật tiến độ dựa trên dữ liệu nhập vào.
  20. **Gửi thông báo (Send Notifications)**: Hệ thống gửi thông báo nhắc nhở về bữa ăn, tập luyện hoặc mục tiêu.
  21. **Cung cấp gợi ý (Provide Suggestions)**: Hệ thống gợi ý bữa ăn hoặc bài tập dựa trên mục tiêu và dữ liệu người dùng.

### Biểu diễn ASCII Art của Sơ đồ Use Case:

```
+-------------------+     +-------------------+
|     Người dùng    |     |     Hệ thống      |
+-------------------+     +-------------------+
          |                           |
          | 1. Đăng nhập              |
          |--------------------------->
          |                           |
          | 2. Đăng ký                |
          |--------------------------->
          |                           |
          | 3. Thiết lập hồ sơ        |
          |--------------------------->
          |                           |
          | 4. Thiết lập mục tiêu     |
          |--------------------------->
          |                           |
          | 5. Xem trang chủ          |
          |--------------------------->
          |                           |
          | 6. Duyệt bữa ăn           |
          |--------------------------->
          |                           |
          | 7. Xem chi tiết bữa ăn     |
          |--------------------------->
          |                           |
          | 8. Duyệt bài tập          |
          |--------------------------->
          |                           |
          | 9. Xem chi tiết bài tập    |
          |--------------------------->
          |                           |
          | 10. Lên lịch tập luyện     |
          |--------------------------->
          |                           |
          | 11. Ghi nhật ký hàng ngày  |
          |--------------------------->
          |                           |
          | 12. Quét sản phẩm          |
          |--------------------------->
          |                           |
          | 13. Xem bản đồ phòng gym   |
          |--------------------------->
          |                           |
          | 14. Nhận thông báo        |
          <---------------------------|
          |                           |
          | 15. Nhận gợi ý            |
          <---------------------------|
```

Sơ đồ này cho thấy Người dùng tương tác với hệ thống thông qua các use case, trong khi Hệ thống chủ động gửi thông báo và gợi ý. Điều này giúp hiểu rõ luồng hoạt động chính của ứng dụng NUTRI-FIT.
