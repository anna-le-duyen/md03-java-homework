package exercise03;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Exercise03 {
    private static Scanner scanner = new Scanner(System.in);
    private static String[] danhSachBienSo = new String[100]; // Khởi tạo mảng tĩnh chứa tối đa 100 biển số
    private static int soLuong = 0; // Biến đếm số lượng biển số thực tế đã thêm

    public static void main(String[] args) {
        while (true) {
            hienThiMenu();
            int chon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống sau khi nhập số

            // Ràng buộc bảo vệ: Phải chọn chức năng 1 trước khi thực hiện các chức năng 2, 3, 4, 5
            if (chon >= 2 && chon <= 5 && soLuong == 0) {
                System.out.println("Danh sách đang trống! Vui lòng chọn chức năng 1 để thêm biển số trước.");
                continue;
            }

            switch (chon) {
                case 1: themBienSo(); break;
                case 2: hienThiDanhSach(); break;
                case 3: timKiemChinhXac(); break;
                case 4: timKiemTheoTinh(); break;
                case 5: sapXepBienSo(); break;
                case 6:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình. Tạm biệt!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại từ 1 đến 6.");
            }
        }
    }

    // Hiển thị giao diện Menu chính
    public static void hienThiMenu() {
        System.out.println("\n******************QUẢN LÝ BIỂN SỐ XE****************");
        System.out.println("1. Thêm các biển số xe");
        System.out.println("2. Hiển thị danh sách biển số xe");
        System.out.println("3. Tìm kiếm biển số xe");
        System.out.println("4. Tìm biển số xe theo mã tỉnh");
        System.out.println("5. Sắp xếp biển số xe tăng dần");
        System.out.println("6. Thoát");
        System.out.print("Lựa chọn của bạn: ");
    }

    // 1. Thêm các biển số xe (Validate dữ liệu bằng Regex)
    public static void themBienSo() {
        System.out.print("Nhập số lượng biển số muốn thêm: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        // Định dạng Regex: 2 chữ số (mã tỉnh) + 1 chữ cái hoa + dấu gạch ngang + 3 chữ số + dấu chấm + 2 chữ số
        // Ví dụ khớp: 30F-123.45, 29A-999.99
        String regexBienSo = "^\\d{2}[A-Z]-\\d{3}\\.\\d{2}$";

        for (int i = 0; i < n; i++) {
            if (soLuong >= danhSachBienSo.length) {
                System.out.println("Bộ nhớ mảng đã đầy, không thể thêm tiếp!");
                break;
            }

            while (true) {
                System.out.print("Nhập biển số xe thứ " + (soLuong + 1) + " (VD: 30F-123.45): ");
                String bienSo = scanner.nextLine().trim().toUpperCase(); // Tự động viết hoa để tránh lỗi người dùng gõ chữ thường

                if (Pattern.matches(regexBienSo, bienSo)) {
                    danhSachBienSo[soLuong] = bienSo;
                    soLuong++;
                    break;
                } else {
                    System.out.println("❌ Định dạng biển số không đúng (Mẫu chuẩn: 30F-123.45). Vui lòng nhập lại!");
                }
            }
        }
        System.out.println("Đã thêm các biển số thành công!");
    }

    // 2. Hiển thị danh sách biển số xe sử dụng StringBuilder nhằm tối ưu hiệu năng
    public static void hienThiDanhSach() {
        System.out.println("--- DANH SÁCH BIỂN SỐ XE ---");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < soLuong; i++) {
            sb.append((i + 1)).append(". ").append(danhSachBienSo[i]).append("\n");
        }
        System.out.print(sb.toString());
    }

    // 3. Tìm kiếm chính xác biển số xe (Tuyến tính)
    public static void timKiemChinhXac() {
        System.out.print("Nhập biển số xe cần tìm chính xác: ");
        String canTim = scanner.nextLine().trim().toUpperCase();
        boolean timThay = false;

        for (int i = 0; i < soLuong; i++) {
            if (danhSachBienSo[i].equals(canTim)) {
                System.out.println("✓ Tìm thấy biển số " + canTim + " tại vị trí index: " + i);
                timThay = true;
                break; // Tìm thấy chính xác đầu tiên thì dừng
            }
        }
        if (!timThay) {
            System.out.println("❌ Không tìm thấy biển số xe [" + canTim + "] trong hệ thống.");
        }
    }

    // 4. Tìm biển số xe theo mã tỉnh (Kiểm tra phần đầu chuỗi - StartsWith)
    public static void timKiemTheoTinh() {
        System.out.print("Nhập mã tỉnh cần tra cứu (ví dụ: 29 hoặc 30): ");
        String maTinh = scanner.nextLine().trim();
        boolean timThay = false;

        System.out.println("Các biển số thuộc mã tỉnh [" + maTinh + "]:");
        for (int i = 0; i < soLuong; i++) {
            // Kiểm tra xem biển số có bắt đầu bằng 2 số mã tỉnh không
            if (danhSachBienSo[i].startsWith(maTinh)) {
                System.out.println("- " + danhSachBienSo[i]);
                timThay = true;
            }
        }
        if (!timThay) {
            System.out.println("(Không có biển số nào thuộc mã tỉnh này trong hệ thống)");
        }
    }

    // 5. Sắp xếp biển số xe tăng dần (Sử dụng hàm so sánh chuỗi .compareTo())
    public static void sapXepBienSo() {
        for (int i = 0; i < soLuong - 1; i++) {
            for (int j = i + 1; j < soLuong; j++) {
                if (danhSachBienSo[i].compareTo(danhSachBienSo[j]) > 0) {
                    // Hoán đổi vị trí
                    String temp = danhSachBienSo[i];
                    danhSachBienSo[i] = danhSachBienSo[j];
                    danhSachBienSo[j] = temp;
                }
            }
        }
        System.out.println("Đã sắp xếp danh sách biển số xe tăng dần thành công!");
        hienThiDanhSach(); // Hiển thị lại mảng ngay sau khi sắp xếp
    }
}