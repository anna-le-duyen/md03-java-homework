import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String studentId;
    private String studentName;
    private int age;
    private String major;

    public Student() {
    }

    public Student(String studentId, String studentName, int age, String major) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.age = age;
        this.major = major;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void inputData(Scanner scanner) {
        System.out.print("Nhập mã sinh viên: ");
        this.studentId = scanner.nextLine();

        System.out.print("Nhập tên sinh viên: ");
        this.studentName = scanner.nextLine();

        System.out.print("Nhập tuổi sinh viên: ");
        this.age = Integer.parseInt(scanner.nextLine());

        System.out.print("Nhập chuyên ngành: ");
        this.major = scanner.nextLine();
    }

    public void displayData() {
        System.out.printf("Mã SV: %-10s | Tên SV: %-20s | Tuổi: %-5d | Chuyên ngành: %-15s\n",
                this.studentId, this.studentName, this.age, this.major);
    }
}

public class Exercise01 {
    private static List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n*********************QUẢN LÝ SINH VIÊN********************");
            System.out.println("1. Hiển thị danh sách sinh viên");
            System.out.println("2. Thêm sinh viên");
            System.out.println("3. Cập nhật thông tin sinh viên theo mã sinh viên");
            System.out.println("4. Xóa sinh viên theo mã sinh viên");
            System.out.println("5. Tìm sinh viên theo tên sinh viên");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số từ 1 đến 6!");
                continue;
            }

            switch (choice) {
                case 1:
                    displayAllStudents();
                    break;
                case 2:
                    addStudents(scanner);
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    deleteStudent(scanner);
                    break;
                case 5:
                    searchStudentByName(scanner);
                    break;
                case 6:
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 6.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void displayAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("Danh sách sinh viên hiện đang trống.");
            return;
        }
        System.out.println("--- DANH SÁCH SINH VIÊN ---");
        for (Student student : studentList) {
            student.displayData();
        }
    }

    private static void addStudents(Scanner scanner) {
        System.out.print("Nhập số lượng sinh viên muốn thêm: ");
        int count = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < count; i++) {
            System.out.println("Nhập thông tin cho sinh viên thứ " + (i + 1) + ":");
            Student student = new Student();
            student.inputData(scanner);
            studentList.add(student);
        }
        System.out.println("Đã thêm thành công " + count + " sinh viên!");
    }

    private static int findIndexById(String id) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    private static void updateStudent(Scanner scanner) {
        System.out.print("Nhập mã sinh viên cần cập nhật: ");
        String updateId = scanner.nextLine();
        int index = findIndexById(updateId);

        if (index == -1) {
            System.out.println("Mã sinh viên không tồn tại");
        } else {
            Student student = studentList.get(index);
            System.out.println("Nhập thông tin mới cho sinh viên (giữ nguyên mã SV: " + updateId + "):");

            System.out.print("Nhập tên sinh viên mới: ");
            student.setStudentName(scanner.nextLine());

            System.out.print("Nhập tuổi mới: ");
            student.setAge(Integer.parseInt(scanner.nextLine()));

            System.out.print("Nhập chuyên ngành mới: ");
            student.setMajor(scanner.nextLine());

            System.out.println("Cập nhật thông tin sinh viên thành công!");
        }
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Nhập mã sinh viên cần xóa: ");
        String deleteId = scanner.nextLine();
        int index = findIndexById(deleteId);

        if (index == -1) {
            System.out.println("Mã sinh viên không tồn tại");
        } else {
            studentList.remove(index);
            System.out.println("Đã xóa sinh viên có mã " + deleteId + " thành công!");
        }
    }

    private static void searchStudentByName(Scanner scanner) {
        System.out.print("Nhập tên sinh viên cần tìm: ");
        String keyword = scanner.nextLine().toLowerCase().trim();
        int foundCount = 0;

        System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
        for (Student student : studentList) {
            if (student.getStudentName().toLowerCase().contains(keyword)) {
                student.displayData();
                foundCount++;
            }
        }

        if (foundCount == 0) {
            System.out.println("Không tìm thấy sinh viên nào khớp với tên vừa nhập.");
        } else {
            System.out.println("Tổng số lượng sinh viên tìm thấy: " + foundCount);
        }
    }
}