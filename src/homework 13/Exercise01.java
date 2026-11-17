import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Exercise01 {
    // Cấu hình thông tin kết nối MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n================ QUẢN LÝ SINH VIÊN (JDBC - STORED PROCEDURE) ================");
            System.out.println("1. Hiển thị danh sách tất cả sinh viên (get_all_students)");
            System.out.println("2. Thêm mới sinh viên (add_student)");
            System.out.println("3. Cập nhật thông tin sinh viên (update_student)");
            System.out.println("4. Tìm kiếm sinh viên theo ID (find_student_by_id)");
            System.out.println("5. Xóa sinh viên theo ID (delete_student)");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số từ 0 đến 5!");
                continue;
            }

            switch (choice) {
                case 1:
                    getAllStudents();
                    break;
                case 2:
                    addStudent(scanner);
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    findStudentById(scanner);
                    break;
                case 5:
                    deleteStudent(scanner);
                    break;
                case 0:
                    System.out.println("Đã thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
            }
        }
    }

    // Hàm mở kết nối CSDL
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 1. Gọi Stored Procedure: get_all_students()
    private static void getAllStudents() {
        String sql = "{CALL get_all_students()}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- DANH SÁCH SINH VIÊN ---");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("ID: %-4d | Họ tên: %-25s | Ngày sinh: %-12s | Email: %s%n",
                        rs.getInt("student_id"),
                        rs.getString("full_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("email"));
            }
            if (!hasData) {
                System.out.println("Danh sách sinh viên trống.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối hoặc truy vấn CSDL: " + e.getMessage());
        }
    }

    // 2. Gọi Stored Procedure: add_student(in_full_name, in_date_of_birth, in_email)
    private static void addStudent(Scanner scanner) {
        System.out.print("Nhập họ và tên: ");
        String fullName = scanner.nextLine().trim();
        if (fullName.isEmpty()) {
            System.out.println("Lỗi: Họ tên không được để trống!");
            return;
        }

        LocalDate dob = inputDate(scanner, "Nhập ngày sinh (yyyy-MM-dd): ");

        System.out.print("Nhập email: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) {
            System.out.println("Lỗi: Email không được để trống!");
            return;
        }

        String sql = "{CALL add_student(?, ?, ?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, fullName);
            stmt.setDate(2, Date.valueOf(dob));
            stmt.setString(3, email);

            stmt.executeUpdate();
            System.out.println("Thêm mới sinh viên thành công!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Lỗi: Email này đã tồn tại trong hệ thống!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm sinh viên: " + e.getMessage());
        }
    }

    // 3. Gọi Stored Procedure: update_student(in_id, in_full_name, in_date_of_birth, in_email)
    private static void updateStudent(Scanner scanner) {
        int id = inputInt(scanner, "Nhập ID sinh viên cần cập nhật: ");
        if (!checkStudentExists(id)) {
            System.out.println("Không tìm thấy sinh viên có ID = " + id);
            return;
        }

        System.out.print("Nhập họ và tên mới: ");
        String fullName = scanner.nextLine().trim();
        LocalDate dob = inputDate(scanner, "Nhập ngày sinh mới (yyyy-MM-dd): ");
        System.out.print("Nhập email mới: ");
        String email = scanner.nextLine().trim();

        String sql = "{CALL update_student(?, ?, ?, ?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, fullName);
            stmt.setDate(3, Date.valueOf(dob));
            stmt.setString(4, email);

            stmt.executeUpdate();
            System.out.println("Cập nhật thông tin sinh viên thành công!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Lỗi: Email mới bị trùng với sinh viên khác!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật sinh viên: " + e.getMessage());
        }
    }

    // 4. Gọi Stored Procedure: find_student_by_id(in_id)
    private static void findStudentById(Scanner scanner) {
        int id = inputInt(scanner, "Nhập ID sinh viên cần tìm: ");
        String sql = "{CALL find_student_by_id(?)}";

        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("--- THÔNG TIN SINH VIÊN TÌM THẤY ---");
                    System.out.printf("ID: %-4d | Họ tên: %-25s | Ngày sinh: %-12s | Email: %s%n",
                            rs.getInt("student_id"),
                            rs.getString("full_name"),
                            rs.getDate("date_of_birth"),
                            rs.getString("email"));
                } else {
                    System.out.println("Không tìm thấy sinh viên có ID = " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm sinh viên: " + e.getMessage());
        }
    }

    // 5. Gọi Stored Procedure: delete_student(in_id)
    private static void deleteStudent(Scanner scanner) {
        int id = inputInt(scanner, "Nhập ID sinh viên cần xóa: ");
        if (!checkStudentExists(id)) {
            System.out.println("Không tìm thấy sinh viên có ID = " + id);
            return;
        }

        String sql = "{CALL delete_student(?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Đã xóa sinh viên có ID = " + id + " thành công!");
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }

    // Hàm phụ trợ kiểm tra ID có tồn tại không trước khi Sửa/Xóa
    private static boolean checkStudentExists(int id) {
        String sql = "{CALL find_student_by_id(?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    // Hàm phụ trợ nhập số nguyên
    private static int inputInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên hợp lệ!");
            }
        }
    }

    // Hàm phụ trợ nhập ngày yyyy-MM-dd
    private static LocalDate inputDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Lỗi: Sai định dạng ngày! Vui lòng nhập theo mẫu yyyy-MM-dd (ví dụ: 2003-05-15).");
            }
        }
    }
}