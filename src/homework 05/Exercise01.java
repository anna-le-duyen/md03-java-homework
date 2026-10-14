package exercise01;

import java.util.Scanner;

public class Exercise01 {
    public static void main(String[] args) {
// Tạo đối tượng Scanner để nhập dữ liệu từ bàn phím
        Scanner scanner = new Scanner(System.in);

        // Nhập chuỗi văn bản từ người dùng
        System.out.print("Nhập vào một chuỗi văn bản: ");
        String text = scanner.nextLine().trim();

        // Nhập từ cần tìm
        System.out.print("Nhập từ cần tìm: ");
        String wordToFind = scanner.nextLine().trim();

        // Tìm vị trí của từ trong chuỗi
        int index = text.indexOf(wordToFind);

        // Kiểm tra và thông báo kết quả
        if (index != -1) {
            System.out.println("Từ \"" + wordToFind + "\" xuất hiện tại vị trí: " + index);
        } else {
            System.out.println("Từ \"" + wordToFind + "\" không xuất hiện trong chuỗi.");
        }

        // Đóng Scanner
        scanner.close();
    }
}