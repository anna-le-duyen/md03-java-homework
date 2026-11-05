package exercise03;
import java.util.Scanner;

public class Exercise03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Tạo đối tượng Person 1
        Person person1 = new Person();
        System.out.print("Nhập tên người thứ nhất: ");
        person1.setName(scanner.nextLine());
        System.out.print("Nhập tuổi người thứ nhất: ");
        person1.setAge(scanner.nextInt());
        scanner.nextLine();  // Đọc dòng trống

        // Tạo đối tượng Person 2
        Person person2 = new Person();
        System.out.print("Nhập tên người thứ hai: ");
        person2.setName(scanner.nextLine());
        System.out.print("Nhập tuổi người thứ hai: ");
        person2.setAge(scanner.nextInt());

        // So sánh tuổi
        if (person1.getAge() > person2.getAge()) {
            System.out.println(person1.getName() + " lớn tuổi hơn " + person2.getName());
        } else if (person1.getAge() < person2.getAge()) {
            System.out.println(person2.getName() + " lớn tuổi hơn " + person1.getName());
        } else {
            System.out.println("Cả hai người có cùng tuổi.");
        }

        scanner.close();
    }
}


class Person {
    private String name;
    private int age;

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter
    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}