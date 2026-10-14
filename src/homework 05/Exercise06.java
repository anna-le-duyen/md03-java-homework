package exercise06;
import java.util.Scanner;

public class Exercise06 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nhập vào mật khẩu cần kiểm tra
        System.out.print("Nhập vào mật khẩu cần kiểm tra: ");
        String matKhau = scanner.nextLine();

        // Biểu thức Regex kiểm tra 5 điều kiện đồng thời
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%!]).{8,}$";

        // Kiểm tra tính hợp lệ và xuất kết quả không chứa dấu ngoặc kép
        if (matKhau.matches(regex)) {
            System.out.println("Mật khẩu hợp lệ");
        } else {
            System.out.println("Mật khẩu không hợp lệ");
        }

        scanner.close();
    }
}