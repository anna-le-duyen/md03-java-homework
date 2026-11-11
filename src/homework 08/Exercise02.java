class Bike extends Vehicle {
    public Bike(String name, int speed) {
        super(name, speed); // Gọi constructor của lớp cha
    }

    // Ghi đè phương thức displayInfo
    @Override
    public void displayInfo() {
        System.out.println("Bike Name: " + getName() + ", Speed: " + getSpeed() + " km/h");
    }
}

class Car extends Vehicle {
    public Car(String name, int speed) {
        super(name, speed); // Gọi constructor của lớp cha
    }

    // Ghi đè phương thức displayInfo
    @Override
    public void displayInfo() {
        System.out.println("Car Name: " + getName() + ", Speed: " + getSpeed() + " km/h");
    }
}


class Exercise02 {
    public static void main(String[] args) {
        // Khởi tạo đối tượng Car và Bike
        Vehicle car = new Car("Toyota", 120);
        Vehicle bike = new Bike("Yamaha", 80);

        // Gọi các phương thức
        car.start();
        car.displayInfo();

        bike.start();
        bike.displayInfo();
    }
}


abstract class Vehicle {
    private String name;
    private int speed;

    // Constructor
    public Vehicle(String name, int speed) {
        this.name = name;
        this.speed = speed;
    }

    // Phương thức trừu tượng
    public abstract void displayInfo();

    // Phương thức thông thường
    public void start() {
        System.out.println("Vehicle is starting...");
    }

    // Getter cho name và speed
    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }
}

