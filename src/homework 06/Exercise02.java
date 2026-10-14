package exercise02;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Exercise02 {
    private static Scanner scanner = new Scanner(System.in);

    // Khởi tạo các biến lưu trữ thông tin người dùng
    private static String hoTen = "";
    private static String email = "";
    private static String soDienThoai = "";
    private static String matKhau = "";
    private static boolean daNhapThongTin = false;

    public static void main(String[] args) {
        while (true) {
            hienThiMenu();
            int chon = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng trống sau khi nhập số

            // Ràng buộc bảo vệ: Phải chọn chức năng 1 trước khi thực hiện các chức năng 2, 3, 4, 5
            if (chon >= 2 && chon <= 5 && !daNhapThongTin) {
                System.out.println("Vui lòng chọn chức năng 1 để nhập thông tin người dùng trước!");
                continue;
            }

            switch (chon) {
                case 1: nhapThongTin(); break;
                case 2: chuanHoaHoTen(); break;
                case 3: kiemTraEmail(); break;
                case 4: kiemTraSoDienThoai(); break;
                case 5: kiemTraMatKhau(); break;
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
        System.out.println("\n******************QUẢN LÝ NGƯỜI DÙNG****************");
        System.out.println("1. Nhập thông tin người dùng");
        System.out.println("2. Chuẩn hóa họ tên");
        System.out.println("3. Kiểm tra email hợp lệ");
        System.out.println("4. Kiểm tra số điện thoại hợp lệ");
        System.out.println("5. Kiểm tra mật khẩu hợp lệ");
        System.out.println("6. Thoát");
        System.out.print("Lựa chọn của bạn: ");
    }

    // 1. Nhập thông tin người dùng
    public static void nhapThongTin() {
        System.out.print("Nhập họ và tên: ");
        hoTen = scanner.nextLine();
        System.out.print("Nhập email: ");
        email = scanner.nextLine();
        System.out.print("Nhập số điện thoại: ");
        soDienThoai = scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        matKhau = scanner.nextLine();

        daNhapThongTin = true;
        System.out.println("Đã lưu thông tin thô của người dùng thành công!");
    }

    // 2. Chuẩn hóa họ tên (Xóa khoảng trắng thừa, viết hoa chữ cái đầu mỗi từ)
    public static void chuanHoaHoTen() {
        if (hoTen.trim().isEmpty()) {
            System.out.println("Họ tên trống, không thể chuẩn hóa.");
            return;
        }

        // Tách chuỗi theo các khoảng trắng liên tiếp
        String[] tu = hoTen.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String t : tu) {
            if (!t.isEmpty()) {
                // Viết hoa chữ đầu, viết thường các chữ sau
                String tuChuanHoa = t.substring(0, 1).toUpperCase() + t.substring(1).toLowerCase();
                sb.append(tuChuanHoa).append(" ");
            }
        }

        hoTen = sb.toString().trim(); // Lưu lại họ tên đã chuẩn hóa
        System.out.println("Họ tên sau khi chuẩn hóa: " + hoTen);
    }

    // 3. Kiểm tra email hợp lệ
    public static void kiemTraEmail() {
        // Regex định dạng email phổ thông theo tiêu chuẩn quốc tế
        String regexEmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        if (Pattern.matches(regexEmail, email)) {
            System.out.println("Email [" + email + "] hợp lệ.");
        } else {
            System.out.println("Email [" + email + "] KHÔNG hợp lệ. Vui lòng kiểm tra lại cấu trúc.");
        }
    }

    // 4. Kiểm tra số điện thoại hợp lệ (Định dạng di động tại Việt Nam)
    public static void kiemTraSoDienThoai() {
        // Regex di động Việt Nam: Bắt đầu bằng 0, theo sau bởi một trong các đầu số (3, 5, 7, 8, 9) và 8 chữ số tiếp theo
        String regexSdt = "^0[35789]\\d{8}$";

        if (Pattern.matches(regexSdt, soDienThoai)) {
            System.out.println("Số điện thoại [" + soDienThoai + "] hợp lệ.");
        } else {
            System.out.println("Số điện thoại [" + soDienThoai + "] KHÔNG hợp lệ. (Đầu số di động VN phải có 10 chữ số, bắt đầu bằng 03, 05, 07, 08, hoặc 09).");
        }
    }

    // 5. Kiểm tra mật khẩu hợp lệ
    public static void kiemTraMatKhau() {
        // Regex kiểm tra ít nhất 8 ký tự, có 1 hoa, 1 thường, 1 số, 1 ký tự đặc biệt
        String regexMatKhau = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

        if (Pattern.matches(regexMatKhau, matKhau)) {
            System.out.println("Mật khẩu hợp lệ.");
        } else {
            System.out.println("Mật khẩu KHÔNG hợp lệ. (Yêu cầu: Tối thiểu 8 ký tự, chứa cả chữ hoa, chữ thường, số và ký tự đặc biệt).");
        }
    }
}