task 5

Yêu cầu: Chỉnh sửa project trên (task4) sao cho tất cả các output (response API) theo dạng tiêu chuẩn có các thành phần (trường) như sau:

- Status: trạng thái của API (chỉ mang 2 trạng thái thành công hoặc thất bại)
- Message: lời nhắn từ server
- Code: mã trạng thái HTTP, bao gồm các mã trạng thái mặc định (200, 404, 401, 500,…) và mã trạng thái do người dùng tự định nghĩa
- Data: thông tin dữ liệu trả về phía gửi yêu cầu (nếu có)

Mã code API định nghĩa cụ thể cho các trường hợp sau:

200: Thành công
404: Không tìm thấy kết quả
900: Dữ liệu không đúng ràng buộc
901: Truy vấn vào cơ sở dữ liệu thất bại
902: Thêm mới một đối tượng đã tồn tại trong CSDL
500: Các lỗi phát sinh khác

Ví dụ:
OUTPUT truy vấn dữ liệu thành công
{
"status": 1,
"message": "user information",
"code": 200,
"data": [
{
"user_id": "20764071470304150",
"username": "admin",
"full_name": "Nga Hồng",
"avatar": "https://app.lotuscdn.vn/2020/2/7/boujcbj2a2a8te8n0se0_image1581069869370.2432.jpeg"
}
]
}

OUTPUT khi truy vấn / cập nhật dữ liệu không tồn tại

{
"status": 1,
"message": "user not found",
"code": 404,
"data": {
}
}
