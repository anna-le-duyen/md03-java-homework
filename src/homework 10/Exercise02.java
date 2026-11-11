import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Tên sách: " + title + ", Tác giả: " + author + ", Năm xuất bản: " + year;
    }
}

public class Exercise02 {
    private static Map<String, Book> bookMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            System.out.println("\n*************** MENU QUẢN LÝ SÁCH ***************");
            System.out.println("1. Thêm sách");
            System.out.println("2. Tìm kiếm sách");
            System.out.println("3. Hiển thị danh sách sách");
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
                    addBook(scanner);
                    break;
                case 2:
                    searchBook(scanner);
                    break;
                case 3:
                    displayBooks();
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

    // 1. Thêm sách
    private static void addBook(Scanner scanner) {
        String title;
        while (true) {
            System.out.println("Nhập tên sách:");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("\nVui lòng ko để trống !");
            } else {
                break;
            }
        }

        String author;
        while (true) {
            System.out.println("Nhập tác giả:");
            author = scanner.nextLine().trim();
            if (author.isEmpty()) {
                System.out.println("\nVui lòng ko để trống !");
            } else {
                break;
            }
        }

        int year;
        while (true) {
            System.out.println("Nhập năm xuất bản:");
            String yearInput = scanner.nextLine().trim();
            if (yearInput.isEmpty()) {
                System.out.println("\nVui lòng ko bỏ trống !");
                continue;
            }
            try {
                year = Integer.parseInt(yearInput);
                if (year < 0) {
                    System.out.println("Vui lòng nhập số nguyên >= 0 !");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên >= 0 !");
            }
        }

        bookMap.put(title.toLowerCase(), new Book(title, author, year));
        System.out.println("Sách đã được thêm thành công.");
    }

    // 2. Tìm kiếm sách
    private static void searchBook(Scanner scanner) {
        System.out.print("Nhập tên sách để tìm kiếm: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        Book foundBook = bookMap.get(keyword);
        if (foundBook != null) {
            System.out.println("Thông tin sách tìm thấy: " + foundBook);
        } else {
            boolean found = false;
            for (Book book : bookMap.values()) {
                if (book.getTitle().toLowerCase().contains(keyword)) {
                    System.out.println("Thông tin sách tìm thấy: " + book);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Không tìm thấy sách.");
            }
        }
    }

    // 3. Hiển thị danh sách sách
    private static void displayBooks() {
        System.out.println("\nDanh sách sách:");
        if (bookMap.isEmpty()) {
            System.out.println("Danh sách trống.");
            return;
        }
        for (Book book : bookMap.values()) {
            System.out.println(book);
        }
    }
}