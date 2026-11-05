import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 1. Lớp Student
class Student {
    private String name;
    private int age;
    private String className; // Đổi tên để tránh từ khóa 'class' của Java
    private double avgScore;

    public Student(String name, int age, String className, double avgScore) {
        this.name = name;
        this.age = age;
        this.className = className;
        this.avgScore = avgScore;
    }

    public double getAvgScore() {
        return avgScore;
    }

    // Hàm in thông tin
    public void hienThiThongTin() {
        System.out.println("Tên: " + name + ", Tuổi: " + age + ", Lớp: " + className + ", Điểm trung bình: " + avgScore);
    }
}

// 2. Lớp chính
public class Exercise04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Khởi tạo danh sách sinh viên chứa các dữ liệu mẫu khớp với kết quả test case
        List<Student> danhSachSV = new ArrayList<>();
        danhSachSV.add(new Student("Alice", 20, "A1", 8.5));
        danhSachSV.add(new Student("Bob", 21, "A1", 7.0));
        danhSachSV.add(new Student("Charlie", 19, "A2", 6.5));
        danhSachSV.add(new Student("David", 22, "A2", 9.0));
        danhSachSV.add(new Student("Eve", 20, "A1", 8.0));

        // Nhập khoảng điểm
        System.out.print("Nhập điểm min: ");
        double min = scanner.nextDouble();

        System.out.print("Nhập điểm max: ");
        double max = scanner.nextDouble();

        // Hiển thị tiêu đề kết quả (in ra giá trị số thực kèm đuôi .0)
        System.out.println("Danh sách sinh viên có điểm trung bình từ " + min + " đến " + max + ":");

        // Duyệt mảng và lọc sinh viên thỏa mãn điều kiện
        for (Student sv : danhSachSV) {
            if (sv.getAvgScore() >= min && sv.getAvgScore() <= max) {
                sv.hienThiThongTin();
            }
        }

        scanner.close();
    }
}
