package com.example.task5.demo;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static UserAPI api = new UserAPI();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = getIntInput("Nhập lựa chọn của bạn: ");
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    updateUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    findUserById();
                    break;
                case 5:
                    findUsersByName();
                    break;
                case 6:
                    findUsersByAddress();
                    break;
                case 7:
                    getAllUsersSortedByName();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Cảm ơn bạn đã test chương trình!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n==== QUẢN LÝ NGƯỜI DÙNG ====");
        System.out.println("1. Thêm người dùng mới");
        System.out.println("2. Cập nhật thông tin người dùng");
        System.out.println("3. Xóa người dùng");
        System.out.println("4. Tìm người dùng theo ID");
        System.out.println("5. Tìm người dùng theo tên");
        System.out.println("6. Tìm người dùng theo địa chỉ");
        System.out.println("7. Hiển thị tất cả người dùng (sắp xếp theo tên)");
        System.out.println("8. Thoát");
    }

    private static void addUser() {
        System.out.println("\n-- THÊM NGƯỜI DÙNG MỚI --");
        int id = getIntInput("Nhập ID: ");
        String name = getStringInput("Nhập tên: ");
        String address = getStringInput("Nhập địa chỉ: ");
        int age = getIntInput("Nhập tuổi: ");

        User newUser = new User(id, name, address, age);
        ApiResponse<User> response = api.addUser(newUser);
        System.out.println(response);
    }

    private static void updateUser() {
        System.out.println("\n-- CẬP NHẬT THÔNG TIN NGƯỜI DÙNG --");
        int id = getIntInput("Nhập ID của người dùng cần cập nhật: ");
        ApiResponse<User> userResponse = api.getUserById(id);
        if (userResponse.getCode() == 404) {
            System.out.println(userResponse);
            return;
        }

        User user = userResponse.getData();
        String name = getStringInput("Nhập tên mới (để trống nếu không thay đổi): ");
        String address = getStringInput("Nhập địa chỉ mới (để trống nếu không thay đổi): ");
        int age = getIntInput("Nhập tuổi mới (nhập -1 nếu không thay đổi): ");

        if (!name.isEmpty()) user.setName(name);
        if (!address.isEmpty()) user.setAddress(address);
        if (age != -1) user.setAge(age);

        ApiResponse<User> response = api.updateUser(user);
        System.out.println(response);
    }

    private static void deleteUser() {
        System.out.println("\n-- XÓA NGƯỜI DÙNG --");
        int id = getIntInput("Nhập ID của người dùng cần xóa: ");
        ApiResponse<Void> response = api.deleteUser(id);
        System.out.println(response);
    }

    private static void findUserById() {
        System.out.println("\n-- TÌM NGƯỜI DÙNG THEO ID --");
        int id = getIntInput("Nhập ID cần tìm: ");
        ApiResponse<User> response = api.getUserById(id);
        System.out.println(response);
    }

    private static void findUsersByName() {
        System.out.println("\n-- TÌM NGƯỜI DÙNG THEO TÊN --");
        String name = getStringInput("Nhập tên cần tìm: ");
        ApiResponse<List<User>> response = api.getUsersByName(name);
        System.out.println(response);
    }

    private static void findUsersByAddress() {
        System.out.println("\n-- TÌM NGƯỜI DÙNG THEO ĐỊA CHỈ --");
        String address = getStringInput("Nhập địa chỉ cần tìm: ");
        ApiResponse<List<User>> response = api.getUsersByAddress(address);
        System.out.println(response);
    }

    private static void getAllUsersSortedByName() {
        System.out.println("\n-- DANH SÁCH TẤT CẢ NGƯỜI DÙNG (SẮP XẾP THEO TÊN) --");
        ApiResponse<List<User>> response = api.getAllUsersSortedByName();
        System.out.println(response);
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}