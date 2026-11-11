// 1. Interface Colorable
interface Colorable {
    void setColor(String color);
}

// 2. Lớp Circle
class Circle implements Colorable {
    private double radius;
    private String color;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}

// 3. Lớp Rectangle
class Rectangle implements Colorable {
    private double width;
    private double length;
    private String color;

    public Rectangle(double width, double length) {
        this.width = width;
        this.length = length;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}

// 4. Lớp Square
class Square implements Colorable {
    private double side;
    private String color;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
// 5. Lớp Main chạy  chương trình
public class Exercise03 {
    public static void main(String[] args) {
        Circle circle = new Circle(5.0);
        Rectangle rectangle = new Rectangle(4.0, 6.0);
        Square square = new Square(3.5);

        circle.setColor("Red");
        rectangle.setColor("Blue");
        square.setColor("Green");

        System.out.println("Circle color: " + circle.getColor());
        System.out.println("Rectangle color: " + rectangle.getColor());
        System.out.println("Square color: " + square.getColor());
    }
}