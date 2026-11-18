import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

// 1. Lớp Database để mở và đóng kết nối
class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection conn, CallableStatement callSt) {
        if (callSt != null) {
            try {
                callSt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

// 2. Lớp Entity Student
class Student {
    private int id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

// 3. Lớp StudentManager và Transaction
class StudentManager {
    public void addStudents(List<Student> students) {
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "{CALL add_students(?, ?)}";

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            callSt = conn.prepareCall(sql);
            for (Student student : students) {
                callSt.setString(1, student.getName());
                callSt.setInt(2, student.getAge());
                callSt.executeUpdate();
            }

            conn.commit();
            System.out.println("Đã thêm sinh viên thành công.");
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Thêm sinh viên thất bại! Đã rollback dữ liệu: " + e.getMessage());
        } finally {
            Database.closeConnection(conn, callSt);
        }
    }
}

// 4. Lớp Main thực thi chương trình
public class Exercise01 {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        List<Student> students = Arrays.asList(
                new Student(0, "Nguyễn Văn A", 20),
                new Student(0, "Trần Thị B", 22),
                new Student(0, "Lê Văn C", 19)
        );

        manager.addStudents(students);
    }
}