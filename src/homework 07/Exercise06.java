package exercise06;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Category {
    private String id;
    private String name;
    private String description;

    // Constructor
    public Category(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

public class Exercise06 {
    private static List<Category> categoryList = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1: Thêm mới danh mục");
            System.out.println("2: Hiển thị danh sách danh mục");
            System.out.println("3: Cập nhật danh mục");
            System.out.println("4: Xóa danh mục");
            System.out.println("5: Tìm kiếm danh mục theo tên");
            System.out.println("6: Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Đọc dòng trống

            switch (choice) {
                case 1:
                    // Thêm mới danh mục
                    System.out.print("Nhập ID danh mục: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập tên danh mục: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập mô tả danh mục: ");
                    String description = scanner.nextLine();
                    categoryList.add(new Category(id, name, description));
                    System.out.println("Danh mục đã được thêm.");
                    break;

                case 2:
                    // Hiển thị danh sách danh mục
                    if(categoryList.isEmpty()) {
                        System.out.println("Danh sách danh mục trống !");
                        break;
                    }
                    System.out.println("Danh sách danh mục:");
                    for (Category category : categoryList) {
                        System.out.println("ID: " + category.getId() + ", Tên: " + category.getName() + ", Mô tả: " + category.getDescription());
                    }
                    break;

                case 3:
                    // Cập nhật danh mục
                    System.out.print("Nhập ID danh mục cần cập nhật: ");
                    String updateId = scanner.nextLine();
                    boolean found = false;
                    for (Category category : categoryList) {
                        if (category.getId().equals(updateId)) {
                            System.out.print("Nhập tên mới: ");
                            category.setName(scanner.nextLine());
                            System.out.print("Nhập mô tả mới: ");
                            category.setDescription(scanner.nextLine());
                            found = true;
                            System.out.println("Danh mục đã được cập nhật.");
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Danh mục không tồn tại.");
                    }
                    break;

                case 4:
                    // Xóa danh mục
                    System.out.print("Nhập ID danh mục cần xóa: ");
                    String deleteId = scanner.nextLine();
                    found = false;
                    for (int i = 0; i < categoryList.size(); i++) {
                        if (categoryList.get(i).getId().equals(deleteId)) {
                            categoryList.remove(i);
                            found = true;
                            System.out.println("Danh mục đã được xóa.");
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Danh mục không tồn tại.");
                    }
                    break;

                case 5:
                    // Tìm kiếm danh mục theo tên
                    System.out.print("Nhập tên danh mục cần tìm: ");
                    String searchName = scanner.nextLine();
                    System.out.println("Danh sách danh mục khớp tên:");
                    found = false;
                    for (Category category : categoryList) {
                        if (category.getName().toLowerCase().contains(searchName.toLowerCase())) {
                            System.out.println("ID: " + category.getId() + ", Tên: " + category.getName() + ", Mô tả: " + category.getDescription());
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Không tìm thấy danh mục nào khớp với tên.");
                    }
                    break;

                case 6:
                    // Thoát
                    System.out.println("Thoát khỏi chương trình.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (choice != 6);

        scanner.close();
    }
}