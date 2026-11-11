package exercise01;

public class Cat extends exercise01.Animals {
    private String furColor;

    // Constructor
    public Cat(String name, int age, String furColor) {
        super(name, age); // Gọi constructor của lớp cha
        this.furColor = furColor;
    }

    // Ghi đè phương thức makeSound
    @Override
    public String makeSound() {
        return "Meow Meow";
    }

    // Ghi đè phương thức displayInfo
    @Override
    public void displayInfo() {
        super.displayInfo(); // Gọi phương thức displayInfo của lớp cha
        System.out.println("Màu lông: " + furColor);
    }
}