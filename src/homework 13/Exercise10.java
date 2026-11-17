import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

import static Exercise01;
import static jdk.internal.org.jline.utils.Colors.s;

// 1. Lớp Student
class Student {
    private int id;
    private String name;
    private String email;

    public Student() {
    }

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

// 2. Lớp Course
class Course {
    private int id;
    private String title;
    private int credits;

    public Course() {
    }

    public Course(String title, int credits) {
        this.title = title;
        this.credits = credits;
    }

    public Course(int id, String title, int credits) {
        this.id = id;
        this.title = title;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}

// 3. Lớp Enrollment
class Enrollment {
    private int studentId;
    private int courseId;
    private double grade;

    public Enrollment() {
    }

    public Enrollment(int studentId, int courseId, double grade) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}

// 4. Lớp StudentManager chứa các phương thức JDBC
class StudentManager {
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "school_management";
    private static final String DB_URL = SERVER_URL + DB_NAME;
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    // Tự động khởi tạo Database và 3 bảng nếu chưa có
    public void initDatabase() {
        try (Connection conn = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            stmt.executeUpdate("USE " + DB_NAME);

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Student (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(255) NOT NULL, " +
                            "email VARCHAR(255) UNIQUE)"
            );

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Course (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "title VARCHAR(255) NOT NULL, " +
                            "credits INT NOT NULL)"
            );

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Enrollment (" +
                            "student_id INT, " +
                            "course_id INT, " +
                            "grade DECIMAL(5, 2), " +
                            "PRIMARY KEY (student_id, course_id), " +
                            "FOREIGN KEY (student_id) REFERENCES Student(id), " +
                            "FOREIGN KEY (course_id) REFERENCES Course(id))"
            );
        } catch (SQLException e) {
            System.out.println("Lỗi khởi tạo CSDL: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // 1. Thêm sinh viên mới (kiểm tra email tồn tại)
    public void addStudent(Student student) {
        String checkSql = "SELECT id FROM Student WHERE email = ?";
        String insertSql = "INSERT INTO Student (name, email) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, student.getEmail());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Lỗi: Sinh viên có email '" + student.getEmail() + "' đã tồn tại!");
                    return;
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, student.getName());
                insertStmt.setString(2, student.getEmail());
                insertStmt.executeUpdate();
                System.out.println("Thêm sinh viên mới thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi JDBC khi thêm sinh viên: " + e.getMessage());
        }
    }

    // 2. Thêm khóa học mới
    public void addCourse(Course course) {
        String checkSql = "SELECT id FROM Course WHERE title = ?";
        String insertSql = "INSERT INTO Course (title, credits) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, course.getTitle());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Lỗi: Khóa học '" + course.getTitle() + "' đã tồn tại!");
                    return;
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, course.getTitle());
                insertStmt.setInt(2, course.getCredits());
                insertStmt.executeUpdate();
                System.out.println("Thêm khóa học mới thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi JDBC khi thêm khóa học: " + e.getMessage());
        }
    }

    // 3. Ghi danh sinh viên vào khóa học
    public void enrollStudent(int studentId, int courseId) {
        String checkStudentSql = "SELECT id FROM Student WHERE id = ?";
        String checkCourseSql = "SELECT id FROM Course WHERE id = ?";
        String checkEnrollSql = "SELECT student_id FROM Enrollment WHERE student_id = ? AND course_id = ?";
        String insertSql = "INSERT INTO Enrollment (student_id, course_id, grade) VALUES (?, ?, ?)";

        try (Connection conn = getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(checkStudentSql)) {
                stmt.setInt(1, studentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Lỗi: Sinh viên ID = " + studentId + " không tồn tại!");
                        return;
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(checkCourseSql)) {
                stmt.setInt(1, courseId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Lỗi: Khóa học ID = " + courseId + " không tồn tại!");
                        return;
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(checkEnrollSql)) {
                stmt.setInt(1, studentId);
                stmt.setInt(2, courseId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Lỗi: Sinh viên đã ghi danh vào khóa học này rồi!");
                        return;
                    }
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, studentId);
                insertStmt.setInt(2, courseId);
                insertStmt.setNull(3, Types.DECIMAL);
                insertStmt.executeUpdate();
                System.out.println("Ghi danh sinh viên vào khóa học thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi JDBC khi ghi danh: " + e.getMessage());
        }
    }

    // 4. Hiển thị danh sách sinh viên và điểm
    public void listStudentsAndGrades() {
        String sql = "SELECT s.id AS student_id, s.name AS student_name, s.email, " +
                "c.id AS course_id, c.title AS course_title, e.grade " +
                "FROM Student s " +
                "LEFT JOIN Enrollment e ON s.id = e.student_id " +
                "LEFT JOIN Course c ON e.course_id = c.id " +
                "ORDER BY s.id, c.id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n================ DANH SÁCH SINH VIÊN VÀ ĐIỂM ================");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                int sId = rs.getInt("student_id");

                String sName = rs.getString("student_name");
                String sEmail = rs.getString("email");
                int cId = rs.getInt("course_id");
                String cTitle = rs.getString("course_title");
                Double grade = rs.getObject("grade") != null ? rs.getDouble("grade")

: null;
                System.out.printf("ID: %-4d | Họ tên: %-20s |



    Email: %-25s | Khóa học ID: %-4d | Tiêu đề: %-20s | Điểm: %s%n",
                            sId, sName, sEmail, cId, cTitle, (grade != null ? grade : "Chưa có"));
            }
            if (!hasData) {
                System.out.println("Danh sách sinh viên trống.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi JDBC khi hiển thị danh sách: " + e.getMessage());
        }
    }
}
        // 5. Cập nhật điểm cho sinh viên
        public void updateStudentGrade(int studentId, int courseId, double grade) {
            String checkEnrollSql = "SELECT student_id FROM Enrollment WHERE student_id = ? AND course_id = ?";
            String updateSql = "UPDATE Enrollment SET grade = ? WHERE student_id = ? AND course_id = ?";
            try (Connection conn = getConnection()) {
                try (PreparedStatement checkStmt = conn.prepareStatement(checkEnrollSql)) {
                    checkStmt.setInt(1, studentId);
                    checkStmt.setInt(2, courseId);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Lỗi: Sinh viên ID " + studentId + " chưa ghi danh khóa học ID " + courseId + "!");
                            return;
                        }
                    }
                }
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setDouble(1, grade);
                    updateStmt.setInt(2, studentId);
                    updateStmt.setInt(3, courseId);
                    updateStmt.executeUpdate();
                    System.out.println("Cập nhật điểm cho sinh viên thành công!");
                }
            } catch (SQLException e) {
                System.out.println("Lỗi JDBC khi cập nhật điểm: " + e.getMessage());
            }
        }
}
        // 5. Lớp Main hiển thị menu
        public class Exercise10 {
            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                StudentManager manager = new StudentManager();
                manager.initDatabase();
                while (true) {
                    System.out.println("\n*************** QUẢN LÝ SINH VIÊN VÀ KHÓA HỌC ***************");
                    System.out.println("1. Thêm sinh viên mới");
                    System.out.println("2. Thêm khóa học mới");
                    System.out.println("3. Ghi danh sinh viên vào khóa học");
                    System.out.println("4. Hiển thị danh sách sinh viên và điểm");
                    System.out.println("5. Cập nhật điểm cho sinh viên");
                    System.out.println("6. Thoát");
                    System.out.print("Lựa chọn của bạn: ");
                    int choice = readInt(scanner);
                    switch (choice) {
                        case 1:
                            System.out.print("Nhập tên sinh viên: ");
                            String name = scanner.nextLine().trim();
                            System.out.print("Nhập email sinh viên: ");
                            String email = scanner.nextLine().trim();
                            if (name.isEmpty() || email.isEmpty()) {
                                System.out.println("Lỗi: Tên và email không được để trống!");
                            } else {
                                manager.addStudent(new Student(name, email));
                            }
                            break;
                        case 2:
                            System.out.print("Nhập tiêu đề khóa học: ");
                            String title = scanner.nextLine().trim();
                            System.out.print("Nhập số tín chỉ: ");
                            int credits = readInt(scanner);
                            if (title.isEmpty() || credits <= 0) {
                                System.out.println("Lỗi: Tiêu đề không được rỗng và tín chỉ phải > 0!");
                            } else {
                                manager.addCourse(new Course(title, credits));
                            }
                            break;
                        case 3:
                            System.out.print("Nhập ID sinh viên: ");
                            int studentId = readInt(scanner);
                            System.out.print("Nhập ID khóa học: ");
                            int courseId = readInt(scanner);
                            manager.enrollStudent(studentId, courseId);
                            break;
                        case 4:
                            manager.listStudentsAndGrades();
                            break;
                        case 5:
                            System.out.print("Nhập ID sinh viên: ");
                            int sId = readInt(scanner);
                            System.out.print("Nhập ID khóa học: ");
                            int cId = readInt(scanner);
                            System.out.print("Nhập điểm mới: ");
                            double grade = readDouble(scanner);
                            if (grade < 0 || grade > 10) {
                                System.out.println("Lỗi: Điểm phải nằm trong khoảng từ 0 đến 10!");
                            } else {
                                manager.updateStudentGrade(sId, cId, grade);
                            }
                            break;
                        case 6:
                            System.out.println("Đã thoát chương trình.");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn từ 1 đến 6.");
                    }
                }
            }
            private static int readInt(Scanner scanner) {
                while (true) {
                    try {
                        return Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.print("Sai định dạng số nguyên, vui lòng nhập lại: ");
                    }
                }
            }
            private static double readDouble(Scanner scanner) {
                while (true) {
                    try {
                        return Double.parseDouble(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.print("Sai định dạng số thực, vui lòng nhập lại: ");
                    }
                }
            }
        }