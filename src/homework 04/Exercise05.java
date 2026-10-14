package exercise05;
import java.util.Scanner;

public class Exercise05 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Nhập số hàng và số cột
        System.out.print("Nhập số hàng: ");
        int soHang = scanner.nextInt();
        System.out.print("Nhập số cột: ");
        int soCot = scanner.nextInt();

        // Khởi tạo mảng 2 chiều
        int[][] mang = new int[soHang][soCot];
        int tongChan = 0;
        int tongLe = 0;

        // 2. Nhập các phần tử theo từng hàng
        System.out.println("Nhập các phần tử cho mảng (theo từng hàng):");
        for (int i = 0; i < soHang; i++) {
            System.out.print("Hàng " + (i + 1) + ": ");
            for (int j = 0; j < soCot; j++) {
                mang[i][j] = scanner.nextInt();

                // 3. Tính tổng trực tiếp khi nhập dữ liệu
                if (mang[i][j] % 2 == 0) {
                    tongChan += mang[i][j];
                } else {
                    tongLe += mang[i][j];
                }
            }
        }

        // 4. Hiển thị kết quả đầu ra
        System.out.println(); // Xuống dòng
        System.out.println("Tổng các số chẵn là: " + tongChan);
        System.out.println("Tổng các số lẻ là: " + tongLe);

        scanner.close();
    }
}