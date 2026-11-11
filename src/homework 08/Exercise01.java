class Animals {
    private String name;
    private int age;

    // Constructor
    public Animals (String name, int age) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Tuổi không được âm");
        }
        this.name = name;
        this.age = age;
    }

    // Getter và Setter
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
        if (age < 0) {
            throw new IllegalArgumentException("Tuổi không được âm");
        }
        this.age = age;
    }

    // Phương thức hiển thị thông tin chung
    public void displayInfo() {
        System.out.println("Tên: " + name + ", Tuổi: " + age);
    }

    // Phương thức âm thanh
    public String makeSound() {
        return "Some generic sound";
    }
}

public class Cat extends Animals {
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


class Dog extends Animals {
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


public class Exercise01 {
    public static void main(String[] args) {
        Animals[] animalsArray = new Animals[2]; // Mảng để lưu động vật

        // Tạo đối tượng Dog và Cat
        Dog dog1 = new Dog("Buddy", 3, "Golden Retriever");
        Cat cat1 = new Cat("Whiskers", 2, "Ghi");

        // Thêm vào mảng
        animalsArray[0] = dog1;
        animalsArray[1] = cat1;

        // Hiển thị thông tin và âm thanh của từng đối tượng
        for (Animals animal : animalsArray) {
            animal.displayInfo();
            System.out.println("Âm thanh: " + animal.makeSound());
            System.out.println();
        }
    }
}