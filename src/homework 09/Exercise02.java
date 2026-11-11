import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 1. Lớp trừu tượng Pet
abstract class Pet {
    private String petId;
    private String petName;
    private int age;

    public Pet() {
    }

    public Pet(String petId, String petName, int age) {
        this.petId = petId;
        this.petName = petName;
        this.age = age;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Phương thức nhập và kiểm tra dữ liệu hợp lệ (Validate)
    public void inputData(Scanner scanner, List<Pet> petList) {
        // 1. Validate Mã thú cưng: 4 ký tự, bắt đầu bằng C hoặc D, 3 ký tự sau là số, không trùng
        while (true) {
            System.out.print("Nhập mã thú cưng (VD: C001 hoặc D001): ");
            String inputId = scanner.nextLine().trim();

            if (!inputId.matches("^[CD]\\d{3}$")) {
                System.out.println("Mã thú cưng phải gồm 4 ký tự, bắt đầu bằng 'C' hoặc 'D' và theo sau là 3 chữ số!");
                continue;
            }

            boolean isDuplicate = false;
            for (Pet pet : petList) {
                if (pet.getPetId().equalsIgnoreCase(inputId)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                System.out.println("Mã thú cưng đã tồn tại, vui lòng nhập mã khác!");
            } else {
                this.petId = inputId;
                break;
            }
        }

        // 2. Validate Tên thú cưng: chuỗi từ 20-50 ký tự
        while (true) {
            System.out.print("Nhập tên thú cưng (20-50 ký tự): ");
            String inputName = scanner.nextLine().trim();

            if (inputName.length() >= 20 && inputName.length() <= 50) {
                this.petName = inputName;
                break;
            } else {
                System.out.println("Tên thú cưng phải có độ dài từ 20 đến 50 ký tự!");
            }
        }

        // 3. Validate Tuổi: số nguyên lớn hơn 0
        while (true) {
            System.out.print("Nhập tuổi thú cưng (> 0): ");
            try {
                int inputAge = Integer.parseInt(scanner.nextLine().trim());
                if (inputAge > 0) {
                    this.age = inputAge;
                    break;
                } else {
                    System.out.println("Tuổi thú cưng phải là số nguyên lớn hơn 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập tuổi là một số nguyên hợp lệ!");
            }
        }
    }

    public void displayData() {
        System.out.printf("Mã TC: %-8s | Tên TC: %-35s | Tuổi: %-5d\n",
                this.petId, this.petName, this.age);
    }

    public abstract void speak();
}

// 2. Lớp Dog kế thừa Pet
class Dog extends Pet {
    public Dog() {
    }

    public Dog(String petId, String petName, int age) {
        super(petId, petName, age);
    }

    @Override
    public void speak() {
        System.out.println("Gâu gâu");
    }
}

// 3. Lớp Cat kế thừa Pet
class Cat extends Pet {
    public Cat() {
    }

    public Cat(String petId, String petName, int age) {
        super(petId, petName, age);
    }

    @Override
    public void speak() {
        System.out.println("Meo meo");
    }
}

// 4. Lớp chính chạy chương trình (Đặt tên Main để tương thích máy chấm tự động)
public class Exercise02 {
    private static List<Pet> petList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n*********************QUẢN LÝ THÚ CƯNG********************");
            System.out.println("1. Hiển thị danh sách thú cưng");
            System.out.println("2. Thêm thú cưng");
            System.out.println("3. Gọi tiếng kêu");
            System.out.println("4. Xóa thú cưng");
            System.out.println("5. Tìm thú cưng theo tên");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số từ 1 đến 6!");
                continue;
            }

            switch (choice) {
                case 1:
                    displayAllPets();
                    break;
                case 2:
                    addPets(scanner);
                    break;
                case 3:
                    makeAllPetsSpeak();
                    break;
                case 4:
                    deletePetById(scanner);
                    break;
                case 5:
                    searchPetByName(scanner);
                    break;
                case 6:
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 6.");
            }
        } while (choice != 6);

        scanner.close();
    }

    // 1. Hiển thị danh sách thú cưng
    private static void displayAllPets() {
        if (petList.isEmpty()) {
            System.out.println("Danh sách thú cưng hiện đang trống.");
            return;
        }
        System.out.println("--- DANH SÁCH THÚ CƯNG ---");
        for (Pet pet : petList) {
            pet.displayData();
        }
    }

    // 2. Thêm thú cưng (Cho phép chọn loại Dog hoặc Cat)
    private static void addPets(Scanner scanner) {
        System.out.print("Nhập số lượng thú cưng muốn thêm: ");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Số lượng không hợp lệ!");
            return;
        }

        for (int i = 0; i < count; i++) {
            System.out.println("Nhập thông tin cho thú cưng thứ " + (i + 1) + ":");
            Pet newPet = null;

            while (newPet == null) {
                System.out.print("Chọn loại thú cưng (1: Chó - Dog | 2: Mèo - Cat): ");
                String type = scanner.nextLine().trim();
                if (type.equals("1")) {
                    newPet = new Dog();
                } else if (type.equals("2")) {
                    newPet = new Cat();
                } else {
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập 1 hoặc 2!");
                }
            }

            newPet.inputData(scanner, petList);
            petList.add(newPet);
        }
        System.out.println("Thêm thú cưng thành công!");
    }

    // 3. Gọi tiếng kêu (tính đa hình)
    private static void makeAllPetsSpeak() {
        if (petList.isEmpty()) {
            System.out.println("Danh sách thú cưng hiện đang trống.");
            return;
        }
        System.out.println("--- TIẾNG KÊU CỦA CÁC THÚ CƯNG ---");
        for (Pet pet : petList) {
            System.out.print(pet.getPetName() + " kêu: ");
            pet.speak();
        }
    }

    // 4. Xóa thú cưng theo mã
    private static void deletePetById(Scanner scanner) {
        System.out.print("Nhập mã thú cưng cần xóa: ");
        String deleteId = scanner.nextLine().trim();
        int deleteIndex = -1;

        for (int i = 0; i < petList.size(); i++) {
            if (petList.get(i).getPetId().equalsIgnoreCase(deleteId)) {
                deleteIndex = i;
                break;
            }
        }

        if (deleteIndex == -1) {
            System.out.println("Mã thú cưng không tồn tại!");
        } else {
            petList.remove(deleteIndex);
            System.out.println("Đã xóa thú cưng có mã " + deleteId + " thành công!");
        }
    }

    // 5. Tìm thú cưng theo tên
    private static void searchPetByName(Scanner scanner) {
        System.out.print("Nhập tên thú cưng cần tìm: ");
        String keyword = scanner.nextLine().toLowerCase().trim();
        int foundCount = 0;

        System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
        for (Pet pet : petList) {
            if (pet.getPetName().toLowerCase().contains(keyword)) {
                pet.displayData();
                foundCount++;
            }
        }

        if (foundCount == 0) {
            System.out.println("Không tìm thấy thú cưng nào khớp với tên vừa nhập.");
        } else {
            System.out.println("Tổng số thú cưng tìm thấy: " + foundCount);
        }
    }
}