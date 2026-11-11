import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private int age;
    private double averageScore;

    public Student(String name, int age, double averageScore) {
        this.name = name;
        this.age = age;
        this.averageScore = averageScore;
    }

    @Override
    public String toString() {
        return "Tên: " + name + ", Tuổi: " + age + ", Điểm trung bình: " + averageScore;
    }
}

public class Exercise01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> studentList = new ArrayList<>();

        while (true) {
            // 1. Nhập tên sinh viên
            String name;
            while (true) {
                System.out.println("Nhập tên sinh viên:");
                name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Vui lòng ko để trống !");
                } else {
                    break;
                }
            }

            // 2. Nhập tuổi sinh viên
            int age;
            while (true) {
                System.out.println("Nhập tuổi sinh viên:");
                String ageInput = scanner.nextLine().trim();
                if (ageInput.isEmpty()) {
                    System.out.println("Vui lòng ko bỏ trống !");
                    continue;
                }
                try {
                    age = Integer.parseInt(ageInput);
                    if (age < 0) {
                        System.out.println(" Vui lòng nhập số nguyên >= 0 !");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Vui lòng nhập số nguyên >= 0 !");
                }
            }

            // 3. Nhập điểm trung bình sinh viên
            double averageScore;
            while (true) {
                System.out.println("Nhập điểm trung bình sinh viên:");
                String scoreInput = scanner.nextLine().trim();
                if (scoreInput.isEmpty()) {
                    System.out.println("Vui lòng ko bỏ trống !");
                    continue;
                }
                try {
                    averageScore = Double.parseDouble(scoreInput);
                    if (averageScore < 0) {
                        System.out.println(" Vui lòng nhập số thực >= 0 !");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Vui lòng nhập số thực >= 0 !");
                }
            }

            studentList.add(new Student(name, age, averageScore));

            // 4. Hỏi tiếp tục thêm sinh viên
            System.out.println("Bạn có muốn thêm sinh viên khác? (y/n):");
            String choice = scanner.nextLine().trim();
            if (!choice.equalsIgnoreCase("y")) {
                break;
            }
        }

        // 5. Hiển thị danh sách sinh viên
        System.out.println("\nDanh sách sinh viên:");
        for (Student student : studentList) {
            System.out.println(student);
        }

        scanner.close();
    }
}