package com.example.task5.demo;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class UserAPI {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/task5";
    private static final String USER = "root";
    private static final String PASS = "";

    /**
     * Thêm một user mới vào database.
     * @param user User cần thêm
     * @return ApiResponse chứa kết quả của thao tác
     */
    public ApiResponse<User> addUser(User user) {
        String sql = "INSERT INTO users (id, name, address, age) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Kiểm tra xem user đã tồn tại chưa
            if (getUserById(user.getId()).getCode() == 200) {
                return new ApiResponse<>(0, "User already exists", 902, null);
            }

            // Validate dữ liệu user
            if (!validateUser(user)) {
                return new ApiResponse<>(0, "Invalid user data", 900, null);
            }

            // Chuẩn bị và thực thi câu lệnh SQL
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getAddress());
            pstmt.setInt(4, user.getAge());

            pstmt.executeUpdate();
            return new ApiResponse<>(1, "User added successfully", 200, user);

        } catch (SQLException e) {
            return new ApiResponse<>(0, "Error adding user: " + e.getMessage(), 901, null);
        }
    }

    /**
     * Xóa một user khỏi database dựa trên ID.
     * @param id ID của user cần xóa
     * @return ApiResponse chứa kết quả của thao tác
     */
    public ApiResponse<Void> deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Kiểm tra xem user có tồn tại không
            ApiResponse<User> userResponse = getUserById(id);
            if (userResponse.getCode() == 404) {
                return new ApiResponse<>(0, "User not found", 404, null);
            }

            // Thực hiện xóa user
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return new ApiResponse<>(1, "User deleted successfully", 200, null);

        } catch (SQLException e) {
            return new ApiResponse<>(0, "Error deleting user: " + e.getMessage(), 901, null);
        }
    }

    /**
     * Cập nhật thông tin của một user.
     * @param user User cần cập nhật
     * @return ApiResponse chứa kết quả của thao tác
     */
    public ApiResponse<User> updateUser(User user) {
        String sql = "UPDATE users SET name = ?, address = ?, age = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Kiểm tra xem user có tồn tại không
            ApiResponse<User> userResponse = getUserById(user.getId());
            if (userResponse.getCode() == 404) {
                return new ApiResponse<>(0, "User not found", 404, null);
            }

            // Validate dữ liệu user
            if (!validateUser(user)) {
                return new ApiResponse<>(0, "Invalid user data", 900, null);
            }

            // Chuẩn bị và thực thi câu lệnh SQL
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getAddress());
            pstmt.setInt(3, user.getAge());
            pstmt.setInt(4, user.getId());

            pstmt.executeUpdate();
            return new ApiResponse<>(1, "User updated successfully", 200, user);

        } catch (SQLException e) {
            return new ApiResponse<>(0, "Error updating user: " + e.getMessage(), 901, null);
        }
    }

    /**
     * Tìm user theo ID.
     * @param id ID của user cần tìm
     * @return ApiResponse chứa thông tin user nếu tìm thấy
     */
    public ApiResponse<User> getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("age"));
                return new ApiResponse<>(1, "User found", 200, user);
            } else {
                return new ApiResponse<>(0, "User not found", 404, null);
            }

        } catch (SQLException e) {
            return new ApiResponse<>(0, "Error finding user: " + e.getMessage(), 901, null);
        }
    }

    /**
     * Tìm users theo tên.
     * @param name Tên cần tìm kiếm
     * @return ApiResponse chứa danh sách users tìm thấy
     */
    public ApiResponse<List<User>> getUsersByName(String name) {
        String sql = "SELECT * FROM users WHERE name LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("age")));
            }

            if (users.isEmpty()) {
                return new ApiResponse<>(0, "No users found", 404, users);
            } else {
                return new ApiResponse<>(1, "Users found", 200, users);
            }

        } catch (SQLException e) {
            return new ApiResponse<>(0, "Error finding users: " + e.getMessage(), 901, null);
        }
    }

    /**
     * Tìm users theo địa chỉ.
     * @param address Địa chỉ cần tìm kiếm
     * @return ApiResponse chứa danh sách users tìm thấy
     */
    public ApiResponse<List<User>> getUsersByAddress(String address) {
        String sql = "SELECT * FROM users WHERE address LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + address + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("age")));
            }

            if (users.isEmpty()) {
                return new ApiResponse<>(0, "No users found", 404, users);
            } else {
                return new ApiResponse<>(1, "Users found", 200, users);
            }

        } catch (SQLException e) {
            return new ApiResponse<>(0, "Error finding users: " + e.getMessage(), 901, null);
        }
    }

    /**
     * Lấy tất cả users và sắp xếp theo tên.
     * @return ApiResponse chứa danh sách tất cả users đã sắp xếp
     */
    public ApiResponse<List<User>> getAllUsersSortedByName() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("age")));
            }

            // Sắp xếp users theo tên
            users.sort(Comparator.comparing(User::getName));

            if (users.isEmpty()) {
                return new ApiResponse<>(0, "No users found", 404, users);
            } else {
                return new ApiResponse<>(1, "Users found", 200, users);
            }

        } catch (SQLException e) {
            return new ApiResponse<>(0, "Error getting users: " + e.getMessage(), 901, null);
        }
    }

    /**
     * Validate dữ liệu của user.
     * @param user User cần validate
     * @return true nếu dữ liệu hợp lệ, false nếu không
     */
    private boolean validateUser(User user) {
        return user.getName() != null && !user.getName().isEmpty() &&
                user.getAddress() != null && !user.getAddress().isEmpty() &&
                user.getAge() > 1 && user.getAge() < 100;
    }
}