import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Student {
    private String name;
    private int age;
    private double grade;

    public Student(String name, int age, double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
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

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return String.format("| name : %15s | age : %5d | grade : %f.2 |", name, age, grade);
    }
}

public class Exercise02 {
    public static void main(String[] args) {
        // Tạo danh sách sinh viên
        List<Student> students = Arrays.asList(
                new Student("Alice", 20, 8.5),
                new Student("Bob", 22, 6.0),
                new Student("Charlie", 21, 7.5),
                new Student("David", 23, 9.0),
                new Student("Eve", 20, 5.5),
                new Student("Frank", 22, 8.0),
                new Student("Grace", 21, 7.0),
                new Student("Heidi", 23, 8.8),
                new Student("Ivan", 20, 6.5),
                new Student("Judy", 22, 7.2)
        );

        // sinh viên có điểm lớn hơn 7.0 và sắp xếp theo tên
        List<Student> filteredStudents = students.stream()
                .filter(student -> student.getGrade() > 7.0)
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());

        // In ra danh sách sinh viên có điểm lớn hơn 7.0, sắp xếp theo tên
        System.out.println("Danh sách sinh viên có điểm lớn hơn 7.0, sắp xếp theo tên:");
        filteredStudents.forEach(System.out::println);
    }
}