package exercise01;

public class Dog extends exercise01.Animals {
    private String breed;

    // Constructor
    public Dog(String name, int age, String breed) {
        super(name, age); // Gọi constructor của lớp cha
        this.breed = breed;
    }

    // Ghi đè phương thức makeSound
    @Override
    public String makeSound() {
        return "Woof Woof";
    }

    // Ghi đè phương thức displayInfo
    @Override
    public void displayInfo() {
        super.displayInfo(); // Gọi phương thức displayInfo của lớp cha
        System.out.println("Giống chó: " + breed);
    }
}