package exercise04;

import java.util.Scanner;

public class Exercise04 {
    public static void main(String[] args) {
        // Tạo đối tượng Scanner để nhập dữ liệu từ bàn phím
        Scanner scanner = new Scanner(System.in);

        // Nhập chuỗi từ người dùng
        System.out.print("Nhập địa chỉ email: ");
        String email = scanner.nextLine().trim(); // Loại bỏ khoảng trắng thừa

        // Biểu thức chính quy để kiểm tra định dạng email
        String emailRegex = "^[a-zA-Z0-9._]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,6})+$";

        // Kiểm tra định dạng email
        if (email.matches(emailRegex)) {
            System.out.println("Email hợp lệ");
        } else {
            System.out.println("Email không hợp lệ");
        }

        // Đóng Scanner
        scanner.close();
    }
}