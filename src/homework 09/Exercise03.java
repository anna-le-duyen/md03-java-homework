import java.util.Scanner;

// 1. Interface IShop
interface IShop {
    void displayData();
}

// 2. Lớp Categories triển khai IShop
class Categories implements IShop {
    private int catalogId;
    private String catalogName;
    private String descriptions;
    private Boolean catalogStatus;

    public Categories() {
    }

    public Categories(int catalogId, String catalogName, String descriptions, Boolean catalogStatus) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.descriptions = descriptions;
        this.catalogStatus = catalogStatus;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Boolean getCatalogStatus() {
        return catalogStatus;
    }

    public void setCatalogStatus(Boolean catalogStatus) {
        this.catalogStatus = catalogStatus;
    }

    public void inputData(Scanner scanner, Categories[] arrCategories, int index) {
        // 1. Mã danh mục tự tăng = mã lớn nhất hiện có + 1
        int maxId = 0;
        for (int i = 0; i < index; i++) {
            if (arrCategories[i] != null && arrCategories[i].getCatalogId() > maxId) {
                maxId = arrCategories[i].getCatalogId();
            }
        }
        this.catalogId = maxId + 1;

        // 2. Nhập tên danh mục (tối đa 50 ký tự, không trùng lặp)
        this.catalogName = inputCatalogName(scanner, arrCategories, index, -1);

        // 3. Nhập mô tả danh mục
        System.out.print("Nhập mô tả danh mục: ");
        this.descriptions = scanner.nextLine().trim();

        // 4. Nhập trạng thái danh mục (chỉ nhận true hoặc false)
        this.catalogStatus = inputCatalogStatus(scanner);
    }

    public static String inputCatalogName(Scanner scanner, Categories[] arrCategories, int index, int ignoreIndex) {
        while (true) {
            System.out.print("Nhập tên danh mục (tối đa 50 ký tự): ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty() || name.length() > 50) {
                System.out.println("Tên danh mục không được để trống và tối đa 50 ký tự!");
                continue;
            }
            boolean isDuplicate = false;
            for (int i = 0; i < index; i++) {
                if (i != ignoreIndex && arrCategories[i] != null
                        && arrCategories[i].getCatalogName().equalsIgnoreCase(name)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                System.out.println("Tên danh mục đã tồn tại, vui lòng nhập lại!");
            } else {
                return name;
            }
        }
    }

    public static Boolean inputCatalogStatus(Scanner scanner) {
        while (true) {
            System.out.print("Nhập trạng thái danh mục (true - Hoạt động | false - Không hoạt động): ");
            String statusStr = scanner.nextLine().trim();
            if (statusStr.equalsIgnoreCase("true") || statusStr.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(statusStr);
            } else {
                System.out.println("Trạng thái chỉ nhận 1 trong 2 giá trị true hoặc false!");
            }
        }
    }

    @Override
    public void displayData() {
        System.out.printf("Mã DM: %-5d | Tên DM: %-25s | Mô tả: %-25s | Trạng thái: %-15s\n",
                this.catalogId,
                this.catalogName,
                this.descriptions,
                (this.catalogStatus != null && this.catalogStatus) ? "Hoạt động" : "Không hoạt động");
    }
}

// 3. Lớp Product triển khai IShop
class Product implements IShop {
    private String productid;
    private String productName;
    private float price;
    private String description;
    private int catalogId;
    private int productStatus;

    public Product() {
    }

    public Product(String productid, String productName, float price, String description, int catalogId, int productStatus) {
        this.productid = productid;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.catalogId = catalogId;
        this.productStatus = productStatus;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public void inputData(Scanner scanner, Product[] arrProduct, int indexProduct, Categories[] arrCategories, int indexCatalog) {
        // 1. Mã sản phẩm: 4 ký tự, bắt đầu là C, S hoặc A, không trùng lặp
        while (true) {
            System.out.print("Nhập mã sản phẩm (4 ký tự, bắt đầu bằng C/S/A, VD: C001): ");
            String id = scanner.nextLine().trim();
            if (!id.matches("^[CSA].{3}$")) {
                System.out.println("Mã sản phẩm phải gồm 4 ký tự và bắt đầu bằng C, S hoặc A!");
                continue;
            }
            boolean isDuplicate = false;
            for (int i = 0; i < indexProduct; i++) {
                if (arrProduct[i] != null && arrProduct[i].getProductid().equalsIgnoreCase(id)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                System.out.println("Mã sản phẩm đã tồn tại, vui lòng nhập mã khác!");
            } else {
                this.productid = id;
                break;
            }
        }

        // 2. Tên sản phẩm: 10-50 ký tự, không trùng
        this.productName = inputProductName(scanner, arrProduct, indexProduct, -1);

        // 3. Giá sản phẩm > 0
        this.price = inputProductPrice(scanner);

        // 4. Mô tả sản phẩm
        System.out.print("Nhập mô tả sản phẩm: ");
        this.description = scanner.nextLine().trim();

        // 5. Chọn mã danh mục từ menu danh mục đang có
        this.catalogId = inputProductCatalogId(scanner, arrCategories, indexCatalog);

        // 6. Trạng thái sản phẩm (0: Đang bán, 1: Hết hàng, 2: Không bán)
        this.productStatus = inputProductStatus(scanner);
    }

    public static String inputProductName(Scanner scanner, Product[] arrProduct, int indexProduct, int ignoreIndex) {
        while (true) {
            System.out.print("Nhập tên sản phẩm (10-50 ký tự): ");
            String name = scanner.nextLine().trim();
            if (name.length() < 10 || name.length() > 50) {
                System.out.println("Tên sản phẩm phải có độ dài từ 10 đến 50 ký tự!");
                continue;
            }
            boolean isDuplicate = false;
            for (int i = 0; i < indexProduct; i++) {
                if (i != ignoreIndex && arrProduct[i] != null
                        && arrProduct[i].getProductName().equalsIgnoreCase(name)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                System.out.println("Tên sản phẩm đã tồn tại, vui lòng nhập lại!");
            } else {
                return name;
            }
        }
    }

    public static float inputProductPrice(Scanner scanner) {
        while (true) {
            System.out.print("Nhập giá sản phẩm (> 0): ");
            try {
                float p = Float.parseFloat(scanner.nextLine().trim());
                if (p > 0) {
                    return p;
                } else {
                    System.out.println("Giá sản phẩm phải lớn hơn 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập giá là một số thực hợp lệ!");
            }
        }
    }

    public static int inputProductCatalogId(Scanner scanner, Categories[] arrCategories, int indexCatalog) {
        while (true) {
            System.out.println("--- Danh sách danh mục sản phẩm ---");
            for (int i = 0; i < indexCatalog; i++) {
                System.out.println(arrCategories[i].getCatalogId() + ". " + arrCategories[i].getCatalogName());
            }
            System.out.print("Chọn mã danh mục của sản phẩm: ");
            try {
                int catId = Integer.parseInt(scanner.nextLine().trim());
                for (int i = 0; i < indexCatalog; i++) {
                    if (arrCategories[i].getCatalogId() == catId) {
                        return catId;
                    }
                }
                System.out.println("Mã danh mục không tồn tại, vui lòng chọn lại!");
            } catch (NumberFormatException e) {
                System.out.println("Mã danh mục phải là số nguyên!");
            }
        }
    }

    public static int inputProductStatus(Scanner scanner) {
        while (true) {
            System.out.print("Nhập trạng thái sản phẩm (0: Đang bán – 1: Hết hàng – 2: Không bán): ");
            try {
                int st = Integer.parseInt(scanner.nextLine().trim());
                if (st == 0 || st == 1 || st == 2) {
                    return st;
                } else {
                    System.out.println("Trạng thái chỉ nhận 1 trong 3 giá trị: 0, 1 hoặc 2!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên 0, 1 hoặc 2!");
            }
        }
    }
    @Override
    public void displayData() {
        String statusStr;
        switch (this.productStatus) {
            case 0:
                statusStr = "Đang bán";
                break;
            case 1:
                statusStr = "Hết hàng";
                break;
            default:
                statusStr = "Không bán";
                break;
        }
        System.out.printf("Mã SP: %-6s | Tên SP: %-25s | Giá: %-10.1f | Mã DM: %-4d | Trạng thái: %-12s | Mô tả: %s\n",
                this.productid, this.productName, this.price, this.catalogId, statusStr, this.description);
    }
}
// 4. Lớp chính điều khiển chương trình
public class Exercise03 {
    private static Categories[] arrCategories = new Categories[100];
    private static int indexCatalog = 0;
    private static Product[] arrProduct = new Product[100];
    private static int indexProduct = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("\nSHOP MENU*");
            System.out.println("1. Quản lý danh mục sản phẩm");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lựa chọn từ 1 đến 3!");
                continue;
            }
            switch (choice) {
                case 1:
                    categoriesMenu(scanner);
                    break;
                case 2:
                    productMenu(scanner);
                    break;
                case 3:
                    System.out.println("Kết thúc chương trình!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn từ 1 đến 3!");
            }
        } while (choice != 3);
        scanner.close();
    }
    // ==================== CATEGORIES MENU ====================
    private static void categoriesMenu(Scanner scanner) {
        int choice = 0;
        do {
            System.out.println("\n*********CATEGORIES MENU");
            System.out.println("1. Nhập thông tin các danh mục");
            System.out.println("2. Hiển thị thông tin các danh mục");
            System.out.println("3. Cập nhật thông tin danh mục");
            System.out.println("4. Xóa danh mục");
            System.out.println("5. Cập nhật trạng thái danh mục");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lựa chọn từ 1 đến 6!");
                continue;
            }
            switch (choice) {
                case 1:
                    addCategories(scanner);
                    break;
                case 2:
                    displayAllCategories();
                    break;
                case 3:
                    updateCategory(scanner);
                    break;
                case 4:
                    deleteCategory(scanner);
                    break;
                case 5:
                    updateCategoryStatus(scanner);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn từ 1 đến 6!");
            }
        } while (choice != 6);
    }
    private static void addCategories(Scanner scanner) {
        System.out.print("Nhập số lượng danh mục cần thêm: ");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
            if (count <= 0) {
                System.out.println("Số lượng phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Số lượng không hợp lệ!");
            return;
        }
        for (int i = 0; i < count; i++) {
            if (indexCatalog >= arrCategories.length) {
                System.out.println("Danh sách danh mục đã đầy!");
                break;
            }
            System.out.println("Nhập thông tin danh mục thứ " + (i + 1) + ":");
            Categories category = new Categories();
            category.inputData(scanner, arrCategories, indexCatalog);
            arrCategories[indexCatalog] = category;
            indexCatalog++;
        }
        System.out.println("Đã thêm danh mục thành công!");
    }
    private static void displayAllCategories() {
        if (indexCatalog == 0) {
            System.out.println("Danh sách danh mục hiện đang trống!");
            return;
        }
        System.out.println("--- DANH SÁCH DANH MỤC ---");
        for (int i = 0; i < indexCatalog; i++) {
            arrCategories[i].displayData();
        }
    }
    private static int findCategoryIndexById(int catalogId) {
        for (int i = 0; i < indexCatalog; i++) {
            if (arrCategories[i].getCatalogId() == catalogId) {
                return i;
            }
        }
        return -1;
    }
    private static void updateCategory(Scanner scanner) {
        System.out.print("Nhập mã danh mục cần cập nhật: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Mã danh mục phải là số nguyên!");
            return;
        }
        int idx = findCategoryIndexById(id);
        if (idx == -1) {
            System.out.println("Mã danh mục không tồn tại!");
            return;
        }
        System.out.println("Cập nhật thông tin cho danh mục có mã " + id + ":");
        arrCategories[idx].setCatalogName(Categories.inputCatalogName(scanner, arrCategories, indexCatalog, idx));
        System.out.print("Nhập mô tả danh mục mới: ");
        arrCategories[idx].setDescriptions(scanner.nextLine().trim());
        arrCategories[idx].setCatalogStatus(Categories.inputCatalogStatus(scanner));
        System.out.println("Cập nhật thông tin danh mục thành công!");
    }
    private static void deleteCategory(Scanner scanner) {
        System.out.print("Nhập mã danh mục cần xóa: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Mã danh mục phải là số nguyên!");
            return;
        }
        int idx = findCategoryIndexById(id);
        if (idx == -1) {
            System.out.println("Mã danh mục không tồn tại!");
            return;
        }
// Kiểm tra danh mục có chứa sản phẩm hay không
        boolean hasProduct = false;
        for (int i = 0; i < indexProduct; i++) {
            if (arrProduct[i].getCatalogId() == id) {
                hasProduct = true;
                break;
            }
        }
        if (hasProduct) {
            System.out.println("Không thể xóa danh mục vì danh mục đang chứa sản phẩm!");
        } else {
            for (int i = idx; i < indexCatalog - 1; i++) {
                arrCategories[i] = arrCategories[i + 1];
            }
            arrCategories[indexCatalog - 1] = null;
            indexCatalog--;
            System.out.println("Xóa danh mục thành công!");
        }
    }
    private static void updateCategoryStatus(Scanner scanner) {
        System.out.print("Nhập mã danh mục cần cập nhật trạng thái: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Mã danh mục phải là số nguyên!");
            return;
        }
        int idx = findCategoryIndexById(id);
        if (idx == -1) {
            System.out.println("Mã danh mục không tồn tại!");
        } else {
            Boolean currentStatus = arrCategories[idx].getCatalogStatus();
            arrCategories[idx].setCatalogStatus(!currentStatus);
            System.out.println("Cập nhật trạng thái thành công! Trạng thái mới: "
                    + (!currentStatus ? "Hoạt động" : "Không hoạt động"));
        }
    }
    // ==================== PRODUCT MANAGEMENT ====================
    private static void productMenu(Scanner scanner) {
        int choice = 0;
        do {
            System.out.println("\n*******************PRODUCT MANAGEMENT*****************");
            System.out.println("1. Nhập thông tin các sản phẩm");
            System.out.println("2. Hiển thị thông tin các sản phẩm");
            System.out.println("3. Sắp xếp các sản phẩm theo giá");
            System.out.println("4. Cập nhật thông tin sản phẩm theo mã sản phẩm");
            System.out.println("5. Xóa sản phẩm theo mã sản phẩm");
            System.out.println("6. Tìm kiếm các sản phẩm theo tên sản phẩm");
            System.out.println("7. Tìm kiếm sản phẩm trong khoảng giá a – b");
            System.out.println("8. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lựa chọn từ 1 đến 8!");
                continue;
            }
            switch (choice) {
                case 1:
                    addProducts(scanner);
                    break;
                case 2:
                    displayAllProducts();
                    break;
                case 3:
                    sortProductsByPrice();
                    break;
                case 4:
                    updateProduct(scanner);
                    break;
                case 5:
                    deleteProduct(scanner);
                    break;
                case 6:
                    searchProductByName(scanner);
                    break;
                case 7:
                    searchProductByPriceRange(scanner);
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn từ 1 đến 8!");
            }
        } while (choice != 8);
    }
    private static void addProducts(Scanner scanner) {
        if (indexCatalog == 0) {
            System.out.println("Chưa có danh mục sản phẩm nào! Vui lòng thêm danh mục trước.");
            return;
        }
        System.out.print("Nhập số lượng sản phẩm cần thêm: ");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
            if (count <= 0) {
                System.out.println("Số lượng phải lớn hơn 0!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Số lượng không hợp lệ!");
            return;
        }
        for (int i = 0; i < count; i++) {
            if (indexProduct >= arrProduct.length) {
                System.out.println("Danh sách sản phẩm đã đầy!");
                break;
            }
            System.out.println("Nhập thông tin sản phẩm thứ " + (i + 1) + ":");
            Product product = new Product();
            product.inputData(scanner, arrProduct, indexProduct, arrCategories, indexCatalog);
            arrProduct[indexProduct] = product;
            indexProduct++;
        }
        System.out.println("Đã thêm sản phẩm thành công!");
    }
    private static void displayAllProducts() {
        if (indexProduct == 0) {
            System.out.println("Danh sách sản phẩm hiện đang trống!");
            return;
        }
        System.out.println("--- DANH SÁCH SẢN PHẨM ---");
        for (int i = 0; i < indexProduct; i++) {
            arrProduct[i].displayData();
        }
    }
    private static void sortProductsByPrice() {
        if (indexProduct == 0) {
            System.out.println("Danh sách sản phẩm hiện đang trống!");
            return;
        }
        for (int i = 0; i < indexProduct - 1; i++) {
            for (int j = i + 1; j < indexProduct; j++) {
                if (arrProduct[i].getPrice() > arrProduct[j].getPrice()) {
                    Product temp = arrProduct[i];
                    arrProduct[i] = arrProduct[j];
                    arrProduct[j] = temp;
                }
            }
        }
        System.out.println("Đã sắp xếp các sản phẩm theo giá tăng dần!");
        displayAllProducts();
    }
    private static int findProductIndexById(String productid) {
        for (int i = 0; i < indexProduct; i++) {
            if (arrProduct[i].getProductid().equalsIgnoreCase(productid)) {
                return i;
            }
        }
        return -1;
    }
    private static void updateProduct(Scanner scanner) {
        System.out.print("Nhập mã sản phẩm cần cập nhật: ");
        String id = scanner.nextLine().trim();
        int idx = findProductIndexById(id);
        if (idx == -1) {
            System.out.println("Mã sản phẩm không tồn tại!");
            return;
        }
        System.out.println("Cập nhật thông tin cho sản phẩm có mã " + id + ":");
        arrProduct[idx].setProductName(Product.inputProductName(scanner, arrProduct, indexProduct, idx));
        arrProduct[idx].setPrice(Product.inputProductPrice(scanner));
        System.out.print("Nhập mô tả sản phẩm mới: ");
        arrProduct[idx].setDescription(scanner.nextLine().trim());
        arrProduct[idx].setCatalogId(Product.inputProductCatalogId(scanner, arrCategories, indexCatalog));
        arrProduct[idx].setProductStatus(Product.inputProductStatus(scanner));
        System.out.println("Cập nhật thông tin sản phẩm thành công!");
    }
    private static void deleteProduct(Scanner scanner) {
        System.out.print("Nhập mã sản phẩm cần xóa: ");
        String id = scanner.nextLine().trim();
        int idx = findProductIndexById(id);
        if (idx == -1) {
            System.out.println("Mã sản phẩm không tồn tại!");
        } else {
            for (int i = idx; i < indexProduct - 1; i++) {
                arrProduct[i] = arrProduct[i + 1];
            }
            arrProduct[indexProduct - 1] = null;
            indexProduct--;
            System.out.println("Xóa sản phẩm thành công!");
        }
    }
    private static void searchProductByName(Scanner scanner) {
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String keyword = scanner.nextLine().toLowerCase().trim();
        boolean found = false;
        System.out.println("--- KẾT QUẢ TÌM KIẾM THEO TÊN ---");
        for (int i = 0; i < indexProduct; i++) {
            if (arrProduct[i].getProductName().toLowerCase().contains(keyword)) {
                arrProduct[i].displayData();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy sản phẩm nào có tên chứa từ khóa vừa nhập!");
        }
    }
    private static void searchProductByPriceRange(Scanner scanner) {
        float a, b;
        try {
            System.out.print("Nhập giá bắt đầu (a): ");
            a = Float.parseFloat(scanner.nextLine().trim());
            System.out.print("Nhập giá kết thúc (b): ");
            b = Float.parseFloat(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Khoảng giá phải là số hợp lệ!");
            return;
        }
        if (a > b) {
            float temp = a;
            a = b;
            b = temp;
        }
        boolean found = false;
        System.out.println("--- DANH SÁCH SẢN PHẨM TRONG KHOẢNG GIÁ " + a + " - " + b + " ---");
        for (int i = 0; i < indexProduct; i++) {
            float price = arrProduct[i].getPrice();
            if (price >= a && price <= b) {
                arrProduct[i].displayData();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy sản phẩm nào trong khoảng giá từ " + a + " đến " + b + "!");
        }
    }
}