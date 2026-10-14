package exercise01;
import java.util.Scanner;

public class Exercise01 {
    private static Scanner scanner = new Scanner(System.in);
    private static double[] diemSV = null;
    private static int n = 0;

    public static void main(String[] args) {
        while (true) {
            hienThiMenu();
            int chon = scanner.nextInt();

            // Kiểm tra ràng buộc nhập dữ liệu trước khi xử lý
            if (chon >= 2 && chon <= 7 && diemSV == null) {
                System.out.println("Vui lòng chọn chức năng 1 để nhập danh sách điểm trước!");
                continue;
            }

            switch (chon) {
                case 1: nhapDiem(); break;
                case 2: inDiem(); break;
                case 3: tinhDiemTrungBinh(); break;
                case 4: timMaxMin(); break;
                case 5: demDatTruot(); break;
                case 6: sapXepTangDan(); break;
                case 7: thongKeGioiXuatSac(); break;
                case 8:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình. Tạm biệt!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại từ 1 đến 8.");
            }
        }
    }

    // Hiển thị giao diện Menu chính
    public static void hienThiMenu() {
        System.out.println("\n******************QUẢN LÝ ĐIỂM SV****************");
        System.out.println("1. Nhập danh sách điểm sinh viên");
        System.out.println("2. In danh sách điểm");
        System.out.println("3. Tính điểm trung bình của các sinh viên");
        System.out.println("4. Tìm điểm cao nhất và thấp nhất");
        System.out.println("5. Đếm số lượng sinh viên đạt và trượt");
        System.out.println("6. Sắp xếp điểm tăng dần");
        System.out.println("7. Thống kê số lượng sinh viên giỏi và xuất sắc");
        System.out.println("8. Thoát");
        System.out.print("Chọn chức năng (1-8): ");
    }

    // 1. Nhập danh sách điểm sinh viên
    public static void nhapDiem() {
        System.out.print("Nhập số lượng sinh viên: ");
        n = scanner.nextInt();
        diemSV = new double[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Nhập điểm sinh viên thứ " + (i + 1) + ": ");
            diemSV[i] = scanner.nextDouble();
        }
        System.out.println("Đã nhập dữ liệu thành công!");
    }

    // 2. In danh sách điểm
    public static void inDiem() {
        System.out.print("Danh sách điểm sinh viên: ");
        for (int i = 0; i < n; i++) {
            System.out.print(diemSV[i] + (i == n - 1 ? "" : ", "));
        }
        System.out.println();
    }

    // 3. Tính điểm trung bình của các sinh viên
    public static void tinhDiemTrungBinh() {
        double tong = 0;
        for (double d : diemSV) {
            tong += d;
        }
        double dtb = tong / n;
        System.out.printf("Điểm trung bình của các sinh viên: %.2f\n", dtb);
    }

    // 4. Tìm điểm cao nhất và thấp nhất
    public static void timMaxMin() {
        double max = diemSV[0];
        double min = diemSV[0];
        for (double d : diemSV) {
            if (d > max) max = d;
            if (d < min) min = d;
        }
        System.out.println("Điểm cao nhất: " + max);
        System.out.println("Điểm thấp nhất: " + min);
    }

    // 5. Đếm số lượng sinh viên đạt và trượt
    public static void demDatTruot() {
        int dat = 0, truot = 0;
        for (double d : diemSV) {
            if (d >= 5) {
                dat++;
            } else {
                try { truot++; } catch (Exception e) {}
            }
        }
        System.out.println("Số lượng sinh viên đạt (>= 5): " + dat);
        System.out.println("Số lượng sinh viên trượt (< 5): " + truot);
    }

    // 6. Sắp xếp điểm tăng dần bằng thuật toán tráo đổi trực tiếp
    public static void sapXepTangDan() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (diemSV[i] > diemSV[j]) {
                    double temp = diemSV[i];
                    diemSV[i] = diemSV[j];
                    diemSV[j] = temp;
                }
            }
        }
        System.out.print("Mảng sau khi sắp xếp tăng dần: ");
        for (double d : diemSV) {
            System.out.print(d + " ");
        }
        System.out.println();
    }

    // 7. Thống kê số lượng sinh viên giỏi và xuất sắc
    public static void thongKeGioiXuatSac() {
        int gioiXuatSac = 0;
        for (double d : diemSV) {
            if (d >= 8) {
                gioiXuatSac++;
            }
        }
        System.out.println("Số lượng sinh viên đạt loại Giỏi và Xuất sắc (>= 8): " + gioiXuatSac);
    }
}