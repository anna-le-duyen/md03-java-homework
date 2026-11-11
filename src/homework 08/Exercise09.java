import java.util.ArrayList;
import java.util.List;

// 1. Interface Refuelable
interface Refuelable {
    void refuel();
}

// 2. Lớp trừu tượng Car
abstract class Car {
    protected String model;
    protected int year;
    protected double price;

    public Car(String model, int year, double price) {
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public abstract String start();
    public abstract String stop();
}

// 3. Lớp con ElectricCar
class ElectricCar extends Car implements Refuelable {
    public ElectricCar(String model, int year, double price) {
        super(model, year, price);
    }

    @Override
    public String start() {
        return "Xe điện " + model + " (Năm SX: " + year + ", Giá: $" + price + ") đã khởi động không tiếng ồn.";
    }

    @Override
    public String stop() {
        return "Xe điện " + model + " (Năm SX: " + year + ", Giá: $" + price + ") đã dừng và ngắt kết nối động cơ.";
    }

    @Override
    public void refuel() {
        System.out.println("Xe điện " + model + " (Năm SX: " + year + ", Giá: $" + price + ") đang sạc điện…");
    }
}

// 4. Lớp con GasCar
class GasCar extends Car implements Refuelable {
    public GasCar(String model, int year, double price) {
        super(model, year, price);
    }

    @Override
    public String start() {
        return "Xe chạy xăng " + model + " (Năm SX: " + year + ", Giá: $" + price + ") đã khởi động với tiếng động cơ.";
    }

    @Override
    public String stop() {
        return "Xe chạy xăng " + model + " (Năm SX: " + year + ", Giá: $" + price + ") đã dừng và động cơ tắt.";
    }

    @Override
    public void refuel() {
        System.out.println("Xe chạy xăng " + model + " (Năm SX: " + year + ", Giá: $" + price + ") đang được đổ xăng…");
    }
}

// 5. Lớp quản lý Garage
class Garage {
    private List<Car> cars;

    public Garage() {
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void startAll() {
        System.out.println("--- KHỞI ĐỘNG TẤT CẢ XE ---");
        for (Car car : cars) {
            System.out.println(car.start());
        }
    }

    public void stopAll() {
        System.out.println("\n--- DỪNG TẤT CẢ XE ---");
        for (Car car : cars) {
            System.out.println(car.stop());
        }
    }

    public void refuelAll() {
        System.out.println("\n--- NẠP NHIÊN LIỆU CHO TẤT CẢ XE ---");
        for (Car car : cars) {
            if (car instanceof Refuelable) {
                ((Refuelable) car).refuel();
            }
        }
    }
}

// 6. Lớp chính để chạy chương trình
public class Exercise09 {
    public static void main(String[] args) {
        Garage myGarage = new Garage();

        Car tesla = new ElectricCar("Tesla Model 3", 2024, 40000.0);
        Car vinfast = new ElectricCar("VinFast VF8", 2025, 45000.0);
        Car toyota = new GasCar("Toyota Camry", 2023, 30000.0);

        myGarage.addCar(tesla);
        myGarage.addCar(vinfast);
        myGarage.addCar(toyota);

        myGarage.startAll();
        myGarage.refuelAll();
        myGarage.stopAll();
    }
}