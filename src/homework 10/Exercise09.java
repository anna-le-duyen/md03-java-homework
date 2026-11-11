import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

// 1. Lớp Contact
class Contact {
    private static int autoId = 1; // Biến tĩnh hỗ trợ tự động tăng ID
    private int id;
    private String name;
    private String phoneNumber;

    // Constructor dùng khi tạo liên lạc mới thực sự (tự động tăng ID)
    public Contact(String name, String phoneNumber) {
        this.id = autoId++;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Constructor phụ chỉ dùng để tạo đối tượng tạm phục vụ contains() và remove() mà không làm tăng autoId
    public Contact(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Cài đặt equals() và hashCode() dựa trên phoneNumber để Set chặn trùng lặp
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Tên: " + name + " | Số điện thoại: " + phoneNumber;
    }
}

// 2. Lớp chính quản lý danh bạ điện thoại
public class Exercise09 {
    // Dùng LinkedHashSet để vừa chặn trùng phoneNumber, vừa giữ nguyên thứ tự thêm vào
    private static Set<Contact> contactSet = new LinkedHashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            System.out.println("\n*************** QUẢN LÝ DANH BẠ ĐIỆN THOẠI ***************");
            System.out.println("1. Thêm liên lạc");
            System.out.println("2. Xóa liên lạc theo số điện thoại");
            System.out.println("3. Tìm kiếm liên lạc theo số điện thoại");
            System.out.println("4. Hiển thị danh sách liên lạc (Có phân trang)");
            System.out.println("5. Thoát");
            System.out.println("Lựa chọn của bạn:");

            String choiceInput = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên từ 1 đến 5 !");
                continue;
            }

            switch (choice) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    removeContact(scanner);
                    break;
                case 3:
                    searchContact(scanner);
                    break;
                case 4:
                    displayContactsWithPagination(scanner);
                    break;
                case 5:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ !");
            }
        }
    }

    // 1. Thêm liên lạc sử dụng Set.add()
    private static void addContact(Scanner scanner) {
        String name;
        while (true) {
            System.out.println("Nhập tên người liên lạc:");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Vui lòng ko để trống !");
            } else {
                break;
            }
        }

        String phoneNumber;
        while (true) {
            System.out.println("Nhập số điện thoại:");
            phoneNumber = scanner.nextLine().trim();
            if (phoneNumber.isEmpty()) {
                System.out.println("Vui lòng ko để trống !");
            } else {
                break;
            }
        }

        // Kiểm tra trước bằng contains() để không bị nhảy số ID nếu số điện thoại đã tồn tại
        Contact tempCheck = new Contact(phoneNumber);
        if (contactSet.contains(tempCheck)) {
            System.out.println("Lỗi: Số điện thoại " + phoneNumber + " đã tồn tại trong danh bạ!");
            return;
        }

        Contact newContact = new Contact(name, phoneNumber);
        if (contactSet.add(newContact)) {
            System.out.println("Thêm liên lạc thành công! (Tổng số liên lạc: " + contactSet.size() + ")");
        } else {
            System.out.println("Lỗi: Số điện thoại đã tồn tại!");
        }
    }

    // 2. Xóa liên lạc theo số điện thoại sử dụng Set.remove()
    private static void removeContact(Scanner scanner) {
        System.out.println("Nhập số điện thoại cần xóa:");
        String phoneToDelete = scanner.nextLine().trim();

        Contact tempContact = new Contact(phoneToDelete);
        if (contactSet.remove(tempContact)) {
            System.out.println("Đã xóa liên lạc có số điện thoại " + phoneToDelete + " thành công!");
        } else {
            System.out.println("Không tìm thấy liên lạc có số điện thoại: " + phoneToDelete);
        }
    }

    // 3. Tìm kiếm liên lạc theo số điện thoại sử dụng Set.contains()
    private static void searchContact(Scanner scanner) {
        System.out.println("Nhập số điện thoại cần tìm:");
        String phoneToSearch = scanner.nextLine().trim();

        Contact tempContact = new Contact(phoneToSearch);
        if (contactSet.contains(tempContact)) {
            for (Contact contact : contactSet) {
                if (contact.equals(tempContact)) {
                    System.out.println("Tìm thấy liên lạc: " + contact);
                    break;
                }
            }
        } else {
            System.out.println("Không tồn tại liên lạc với số điện thoại: " + phoneToSearch);
        }
    }

    // 4. In danh sách liên lạc & Phân trang bằng List
    private static void displayContactsWithPagination(Scanner scanner) {
        if (contactSet.isEmpty()) {
            System.out.println("Danh bạ điện thoại hiện đang trống!");
            return;
        }

        // Chuyển Set sang List để thực hiện phân trang theo chỉ số (index)
        List<Contact> contactList = new ArrayList<>(contactSet);
        int pageSize = 3; // Số lượng liên lạc hiển thị trên mỗi trang
        int totalContacts = contactList.size();
        int totalPages = (int) Math.ceil((double) totalContacts / pageSize);
        int currentPage = 1;

        while (true) {
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalContacts);

            System.out.println("\n--- DANH SÁCH LIÊN LẠC (Trang " + currentPage + "/" + totalPages + " | Tổng: " + totalContacts + ") ---");
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(contactList.get(i));
            }

            if (totalPages <= 1) {
                break;
            }

            System.out.println("Nhập số trang muốn xem (1-" + totalPages + ") hoặc nhập 0 để quay lại menu:");
            String pageInput = scanner.nextLine().trim();
            try {
                int selectedPage = Integer.parseInt(pageInput);
                if (selectedPage == 0) {
                    break;
                } else if (selectedPage >= 1 && selectedPage <= totalPages) {
                    currentPage = selectedPage;
                } else {
                    System.out.println("Số trang không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }
    }
}