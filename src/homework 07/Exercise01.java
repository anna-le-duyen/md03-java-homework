package exercise01;

public class Exercise01 {
    // Phương thức main
    public static void main(String[] args) {
        // Tạo đối tượng Rectangle với constructor mặc định
        Rectangle rect1 = new Rectangle();
        System.out.println("Hình chữ nhật 1:");
        rect1.display();

        // Tạo đối tượng Rectangle với constructor có tham số
        Rectangle rect2 = new Rectangle(5.0, 3.0);
        System.out.println("\nHình chữ nhật 2:");
        rect2.display();
    }
}

public class Rectangle {
    // Thuộc tính
    private double length;
    private double width;

    // Constructor mặc định
    public Rectangle() {
        this.length = 1.0;
        this.width = 1.0;
    }

    // Constructor có tham số
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // Phương thức tính diện tích
    public double getArea() {
        return length * width;
    }

    // Phương thức tính chu vi
    public double getPerimeter() {
        return 2 * (length + width);
    }

    // Phương thức hiển thị thông tin
    public void display() {
        System.out.println("Chiều dài: " + length);
        System.out.println("Chiều rộng: " + width);
        System.out.println("Diện tích: " + getArea());
        System.out.println("Chu vi: " + getPerimeter());
    }


}