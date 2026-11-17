import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    public void inputData(Scanner scanner) {
        inputData(scanner, new java.util.ArrayList<>());
    }
    private static int autoId = 1;

    private int productId;
    private String productName;
    private float price;
    private String category;
    private int quantity;

    public Product() {
        this.productId = autoId++;
    }

    public Product(int productId, String productName, float price, String category, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void inputData(Scanner scanner, List<Product> existingProducts) {
        this.productName = inputProductName(scanner, existingProducts, -1);
        this.price = inputPrice(scanner);
        this.category = inputCategory(scanner);
        this.quantity = inputQuantity(scanner);
    }

    public static String inputProductName(Scanner scanner, List<Product> existingProducts, int currentProductId) {
        while (true) {
            System.out.print("Nhập tên sản phẩm (10-50 ký tự): ");
            String name = scanner.nextLine().trim();
            if (name.length() < 10 || name.length() > 50) {
                System.err.println("Lỗi: Tên sản phẩm phải có độ dài từ 10 đến 50 ký tự!");
                continue;
            }
            boolean isDuplicate = existingProducts.stream()
                    .anyMatch(p -> p.getProductId() != currentProductId
                            && p.getProductName().equalsIgnoreCase(name));
            if (isDuplicate) {
                System.err.println("Lỗi: Tên sản phẩm đã tồn tại!");
                continue;
            }
            return name;
        }
    }

    public static float inputPrice(Scanner scanner) {
        while (true) {
            System.out.print("Nhập giá sản phẩm (> 0): ");
            try {
                float price = Float.parseFloat(scanner.nextLine().trim());
                if (price <= 0) {
                    System.err.println("Lỗi: Giá sản phẩm phải lớn hơn 0!");
                    continue;
                }
                return price;
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: Vui lòng nhập số thực hợp lệ!");
            }
        }
    }

    public static String inputCategory(Scanner scanner) {
        while (true) {
            System.out.print("Nhập loại sản phẩm (tối đa 200 ký tự): ");
            String category = scanner.nextLine().trim();
            if (category.isEmpty() || category.length() > 200) {
                System.err.println("Lỗi: Loại sản phẩm không được để trống và tối đa 200 ký tự!");
                continue;
            }
            return category;
        }
    }

    public static int inputQuantity(Scanner scanner) {
        while (true) {
            System.out.print("Nhập số lượng tồn kho (>= 0): ");
            try {
                int quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity < 0) {
                    System.err.println("Lỗi: Số lượng tồn kho phải lớn hơn hoặc bằng 0!");
                    continue;
                }
                return quantity;
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: Vui lòng nhập số nguyên hợp lệ!");
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Mã SP: %d | Tên SP: %-25s | Giá: %10.2f | Danh mục: %-15s | Tồn kho: %d",
                productId, productName, price, category, quantity);
    }
}


class ProductBusiness {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Scanner scanner) {
        Product product = new Product();
        product.inputData(scanner, products);
        products.add(product);
        System.out.println("Thêm sản phẩm thành công!");
    }

    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống.");
            return;
        }
        System.out.println("--- DANH SÁCH SẢN PHẨM ---");
        products.forEach(System.out::println);
    }

    public void updateProduct(Scanner scanner) {
        int id = inputId(scanner, "Nhập mã sản phẩm cần cập nhật: ");
        Optional<Product> productOpt = findById(id);

        if (productOpt.isEmpty()) {
            System.err.println("Không tìm thấy sản phẩm có mã: " + id);
            return;
        }

        Product product = productOpt.get();
        System.out.println("Thông tin hiện tại: " + product);
        product.setProductName(Product.inputProductName(scanner, products, id));
        product.setPrice(Product.inputPrice(scanner));
        product.setCategory(Product.inputCategory(scanner));
        product.setQuantity(Product.inputQuantity(scanner));
        System.out.println("Cập nhật sản phẩm thành công!");
    }

    public void deleteProduct(Scanner scanner) {
        int id = inputId(scanner, "Nhập mã sản phẩm cần xóa: ");
        Optional<Product> productOpt = findById(id);

        if (productOpt.isEmpty()) {
            System.err.println("Không tìm thấy sản phẩm có mã: " + id);
            return;
        }

        products.remove(productOpt.get());
        System.out.println("Xóa sản phẩm thành công!");
    }

    public void searchProductByName(Scanner scanner) {
        System.out.print("Nhập từ khóa tên sản phẩm cần tìm: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        List<Product> result = products.stream()
                .filter(p -> p.getProductName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm nào phù hợp với từ khóa: " + keyword);
        } else {
            System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
            result.forEach(System.out::println);
        }
    }

    public void sortByPriceAsc() {
        if (products.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống.");
            return;
        }
        products.sort(Comparator.comparingDouble(Product::getPrice));
        System.out.println("Đã sắp xếp sản phẩm theo giá tăng dần!");
        displayProducts();
    }

    public void sortByQuantityDesc() {
        if (products.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống.");
            return;
        }
        products.sort(Comparator.comparingInt(Product::getQuantity).reversed());
        System.out.println("Đã sắp xếp sản phẩm theo số lượng giảm dần!");
        displayProducts();
    }

    private Optional<Product> findById(int id) {
        return products.stream()
                .filter(p -> p.getProductId() == id)
                .findFirst();
    }

    private int inputId(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: Mã sản phẩm phải là số nguyên!");
            }
        }
    }
}

public class Exercise01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductBusiness productBusiness = new ProductBusiness();

        while (true) {
            System.out.println("\n*********************QUẢN LÝ SẢN PHẨM********************");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Danh sách sản phẩm");
            System.out.println("3. Cập nhật sản phẩm theo mã sản phẩm");
            System.out.println("4. Xóa sản phẩm theo mã sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm theo tên");
            System.out.println("6. Sắp xếp sản phẩm theo giá tăng dần");
            System.out.println("7. Sắp xếp sản phẩm theo số lượng giảm dần");
            System.out.println("8. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: Vui lòng nhập số từ 1 đến 8!");
                continue;
            }

            switch (choice) {
                case 1:
                    productBusiness.addProduct(scanner);
                    break;
                case 2:
                    productBusiness.displayProducts();
                    break;
                case 3:
                    productBusiness.updateProduct(scanner);
                    break;
                case 4:
                    productBusiness.deleteProduct(scanner);
                    break;
                case 5:
                    productBusiness.searchProductByName(scanner);
                    break;
                case 6:
                    productBusiness.sortByPriceAsc();
                    break;
                case 7:
                    productBusiness.sortByQuantityDesc();
                    break;
                case 8:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 8!");
            }
        }
    }
}