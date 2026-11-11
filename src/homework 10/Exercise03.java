import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// 1. Lớp User lưu trữ thông tin người dùng
class User {
    private String name;
    private String email;
    private String phoneNumber;

    public User() {
    }

    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Tên: " + name + ", Email: " + email + ", Số điện thoại: " + phoneNumber;
    }
}

// 2. Lớp chính quản lý danh sách người dùng bằng LinkedList
public class Exercise03 {
    private static List<User> userList = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            System.out.println("\n*************** MENU QUẢN LÝ NGƯỜI DÙNG ***************");
            System.out.println("1. Thêm người dùng");
            System.out.println("2. Xóa người dùng");
            System.out.println("3. Hiển thị danh sách người dùng");
            System.out.println("4. Thoát");
            System.out.println("Lựa chọn của bạn:");

            String choiceInput = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên từ 1 đến 4 !");
                continue;
            }

            switch (choice) {
                case 1:
                    addUser(scanner);
                    break;
                case 2:
                    deleteUserByEmail(scanner);
                    break;
                case 3:
                    displayUsers();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ !");
            }
        }
    }

    // 1. Thêm người dùng
    private static void addUser(Scanner scanner) {
        String name;
        while (true) {
            System.out.println("Nhập tên người dùng:");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Vui lòng ko để trống !");
            } else {
                break;
            }
        }

        String email;
        while (true) {
            System.out.println("Nhập email người dùng:");
            email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Vui lòng ko để trống !");
            } else {
                break;
            }
        }

        String phoneNumber;
        while (true) {
            System.out.println("Nhập số điện thoại người dùng:");
            phoneNumber = scanner.nextLine().trim();
            if (phoneNumber.isEmpty()) {
                System.out.println("Vui lòng ko để trống !");
            } else {
                break;
            }
        }

        userList.add(new User(name, email, phoneNumber));
        System.out.println("Người dùng đã được thêm thành công.");
    }

    // 2. Xóa người dùng theo email
    private static void deleteUserByEmail(Scanner scanner) {
        System.out.println("Nhập email người dùng để xóa:");
        String emailToDelete = scanner.nextLine().trim();

        boolean isDeleted = false;
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getEmail().equalsIgnoreCase(emailToDelete)) {
                iterator.remove();
                isDeleted = true;
                break;
            }
        }

        if (isDeleted) {
            System.out.println("Người dùng đã được xóa thành công.");
        } else {
            System.out.println("Không tìm thấy người dùng với email: " + emailToDelete);
        }
    }

    // 3. Hiển thị danh sách người dùng
    private static void displayUsers() {
        System.out.println("\nDanh sách người dùng:");
        if (userList.isEmpty()) {
            System.out.println("Danh sách trống.");
            return;
        }
        for (User user : userList) {
            System.out.println(user);
        }
    }
}