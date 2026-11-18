import presentation.RentalView;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        RentalView view = new RentalView();
        view.run();
    }
}
// Lớp Entity Product
class Product {
    private int productId;
    private String productName;
    private float productPrice;
    private String productTitle;
    private LocalDate productCreated;
    private String productCatalog;
    private boolean productStatus;

    public Product() {
    }

    public Product(int productId, String productName, float productPrice, String productTitle,
                   LocalDate productCreated, String productCatalog, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
        this.productCreated = productCreated;
        this.productCatalog = productCatalog;
        this.productStatus = productStatus;
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

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public LocalDate getProductCreated() {
        return productCreated;
    }

    public void setProductCreated(LocalDate productCreated) {
        this.productCreated = productCreated;
    }

    public String getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(String productCatalog) {
        this.productCatalog = productCatalog;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return String.format("ID: %-3d | Tên SP: %-20s | Giá: %10.2f | Tiêu đề: %-20s | Ngày tạo: %-10s | Danh mục: %-15s | Trạng thái: %s",
                productId,
                productName,
                productPrice,
                productTitle,
                productCreated,
                productCatalog,
                productStatus ? "Hoạt động" : "Ngừng bán");
    }
}

// Lớp DAO / Business xử lý JDBC, Stored Procedure và Transaction
class ProductDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/ProductManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 1. Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "{CALL sp_get_all_products()}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách sản phẩm: " + e.getMessage());
        }
        return list;
    }

    // 2. Kiểm tra sự tồn tại của danh mục (Tham số OUT)
    public boolean isCatalogExists(String catalog) {
        String sql = "{CALL sp_check_catalog_exists(?, ?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, catalog);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();

            int count = stmt.getInt(2);
            return count > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra danh mục: " + e.getMessage());
            return false;
        }
    }

    // 3. Thêm mới sản phẩm (Có quản lý Transaction)
    public boolean addProduct(Product p) {
        String sql = "{CALL sp_add_product(?, ?, ?, ?, ?, ?)}";
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setString(1, p.getProductName());
                stmt.setFloat(2, p.getProductPrice());
                stmt.setString(3, p.getProductTitle());
                stmt.setDate(4, Date.valueOf(p.getProductCreated()));
                stmt.setString(5, p.getProductCatalog());
                stmt.setBoolean(6, p.isProductStatus());

                stmt.executeUpdate();
            }

            conn.commit(); // Xác nhận Transaction thành công
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            rollbackQuietly(conn);
            System.out.println("Lỗi: Tên sản phẩm '" + p.getProductName() + "' đã tồn tại!");
            return false;
        } catch (SQLException e) {
            rollbackQuietly(conn);
            System.out.println("Lỗi khi thêm sản phẩm (Đã Rollback): " + e.getMessage());
            return false;
        } finally {
            closeQuietly(conn);
        }
    }

    // 4. Cập nhật sản phẩm theo mã (Có quản lý Transaction)
    public boolean updateProduct(Product p) {
        String sql = "{CALL sp_update_product(?, ?, ?, ?, ?, ?, ?)}";
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setInt(1, p.getProductId());
                stmt.setString(2, p.getProductName());
                stmt.setFloat(3, p.getProductPrice());
                stmt.setString(4, p.getProductTitle());
                stmt.setDate(5, Date.valueOf(p.getProductCreated()));
                stmt.setString(6, p.getProductCatalog());
                stmt.setBoolean(7, p.isProductStatus());

                stmt.executeUpdate();
            }

            conn.commit(); // Xác nhận Transaction
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            rollbackQuietly(conn);
            System.out.println("Lỗi: Tên sản phẩm mới bị trùng với sản phẩm khác!");
            return false;
        } catch (SQLException e) {
            rollbackQuietly(conn);
            System.out.println("Lỗi khi cập nhật sản phẩm (Đã Rollback): " + e.getMessage());
            return false;
        } finally {
            closeQuietly(conn);
        }
    }

    // 5. Xóa sản phẩm theo mã (Có quản lý Transaction)
    public boolean deleteProduct(int id) {
        String sql = "{CALL sp_delete_product(?)}";
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            conn.commit(); // Xác nhận Transaction
            return true;
        } catch (SQLException e) {
            rollbackQuietly(conn);
            System.out.println("Lỗi khi xóa sản phẩm (Đã Rollback): " + e.getMessage());
            return false;
        } finally {
            closeQuietly(conn);
        }
    }

    // 6. Lấy thông tin sản phẩm theo mã
    public Product getProductById(int id) {
        String sql = "{CALL sp_get_product_by_id(?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm sản phẩm theo mã: " + e.getMessage());
        }
        return null;
    }

    // 7. Tìm kiếm sản phẩm theo tên tương đối
    public List<Product> searchProductByName(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "{CALL sp_search_product_by_name(?)}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, keyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm sản phẩm: " + e.getMessage());
        }
        return list;
    }

    // 8. Thống kê số lượng sản phẩm theo danh mục
    public void statisticByCatalog() {
        String sql = "{CALL sp_statistic_by_catalog()}";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- THỐNG KÊ SỐ LƯỢNG SẢN PHẨM THEO DANH MỤC ---");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                String catalog = rs.getString("Product_catalog");
                int total = rs.getInt("total_products");
                System.out.printf("Danh mục: %-20s | Số lượng sản phẩm: %d%n", catalog, total);
            }
            if (!hasData) {
                System.out.println("Chưa có dữ liệu thống kê.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thống kê danh mục: " + e.getMessage());
        }
    }
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("Product_Id"),
                rs.getString("Product_Name"),
                rs.getFloat("Product_Price"),
                rs.getString("Product_Title"),
                rs.getDate("Product_created").toLocalDate(),
                rs.getString("Product_catalog"),
                rs.getBoolean("Product_Status")
        );
    }
    private void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
        }
    }
    private void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
// Lớp Main hiển thị Menu và Validate dữ liệu
public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductDAO productDAO = new ProductDAO();
        while (true) {
            System.out.println("\n********************PRODUCT MANAGEMENT****************");
            System.out.println("1. Danh sách sản phẩm");
            System.out.println("2. Thêm mới sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm theo tên sản phẩm");
            System.out.println("6. Sắp xếp sản phẩm theo giá tăng dần");
            System.out.println("7. Thống kê số lượng sản phẩm theo danh mục");
            System.out.println("8. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            int choice = inputInt(scanner);
            switch (choice) {
                case 1:
                    List products = productDAO.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("Danh sách sản phẩm trống.");
                    } else {
                        System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
                        products.forEach(System.out::println);
                    }
                    break;
                case 2:
                    System.out.println("\n--- THÊM MỚI SẢN PHẨM ---");
                    Product newProduct = inputProductData(scanner, productDAO, false);
                    if (productDAO.addProduct(newProduct)) {
                        System.out.println("Thêm mới sản phẩm thành công!");
                    }
                    break;
                case 3:
                    System.out.println("\n--- CẬP NHẬT SẢN PHẨM ---");
                    System.out.print("Nhập mã sản phẩm cần cập nhật: ");
                    int updateId = inputInt(scanner);
                    Product existing = productDAO.getProductById(updateId);
                    if (existing == null) {
                        System.out.println("Không tìm thấy sản phẩm có mã = " + updateId);
                    } else {
                        System.out.println("Thông tin hiện tại: " + existing);
                        Product updatedProduct = inputProductData(scanner, productDAO, true);
                        updatedProduct.setProductId(updateId);
                        if (productDAO.updateProduct(updatedProduct)) {
                            System.out.println("Cập nhật sản phẩm thành công!");
                        }
                    }
                    break;
                case 4:
                    System.out.println("\n--- XÓA SẢN PHẨM ---");
                    System.out.print("Nhập mã sản phẩm cần xóa: ");
                    int deleteId = inputInt(scanner);
                    Product toDelete = productDAO.getProductById(deleteId);
                    if (toDelete == null) {
                        System.out.println("Không tìm thấy sản phẩm có mã = " + deleteId);
                    } else {
                        if (productDAO.deleteProduct(deleteId)) {
                            System.out.println("Xóa sản phẩm thành công!");
                        }
                    }
                    break;
                case 5:
                    System.out.print("Nhập tên sản phẩm cần tìm: ");
                    String keyword = scanner.nextLine().trim();
                    List foundList = productDAO.searchProductByName(keyword);
                    if (foundList.isEmpty()) {
                        System.out.println("Không tìm thấy sản phẩm nào khớp với từ khóa '" + keyword + "'");
                    } else {
                        System.out.println("\n--- KẾT QUẢ TÌM KIẾM ---");
                        foundList.forEach(System.out::println);
                    }
                    break;
                case 6:
                    List sortedList  = productDAO.getAllProducts();
                    if (sortedList.isEmpty()) {
                        System.out.println("Danh sách sản phẩm trống.");
                    } else {
                        sortedList.sort(Comparator.comparingDouble(Product::getProductPrice));
                        System.out.println("\n--- DANH SÁCH SẢN PHẨM SẮP XẾP THEO GIÁ TĂNG DẦN ---");
                        sortedList.forEach(System.out::println);
                    }
                    break;
                case 7:
                    productDAO.statisticByCatalog();
                    break;
                case 8:
                    System.out.println("Đã thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 8!");
            }
        }
    }
    // Validate dữ liệu đầu vào ràng buộc bảng Product
    private static Product inputProductData(Scanner scanner, ProductDAO dao, boolean isUpdate) {
        String name;
        while (true) {
            System.out.print("Nhập tên sản phẩm (tối đa 100 ký tự, không rỗng): ");
            name = scanner.nextLine().trim();
            if (name.isEmpty() || name.length() > 100) {
                System.out.println("Lỗi: Tên sản phẩm không được để trống và tối đa 100 ký tự!");
                continue;
            }
            break;
        }
        float price;
        while (true) {
            System.out.print("Nhập giá sản phẩm (> 0): ");
            try {
                price = Float.parseFloat(scanner.nextLine().trim());
                if (price <= 0) {
                    System.out.println("Lỗi: Giá sản phẩm phải lớn hơn 0!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số thực hợp lệ!");
            }
        }
        String title;
        while (true) {
            System.out.print("Nhập tiêu đề sản phẩm (tối đa 200 ký tự, không rỗng): ");
            title = scanner.nextLine().trim();
            if (title.isEmpty() || title.length() > 200) {
                System.out.println("Lỗi: Tiêu đề sản phẩm không được để trống và tối đa 200 ký tự!");
                continue;
            }
            break;
        }
        LocalDate createdDate;
        while (true) {
            System.out.print("Nhập ngày tạo sản phẩm (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine().trim();
            try {
                createdDate = LocalDate.parse(dateStr, DATE_FORMATTER);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Lỗi: Ngày tạo không hợp lệ! Định dạng chuẩn: yyyy-MM-dd (ví dụ: 2026-11-18).");
            }
        }
        String catalog;
        while (true) {
            System.out.print("Nhập danh mục sản phẩm (tối đa 100 ký tự, không rỗng): ");
            catalog = scanner.nextLine().trim();
            if (catalog.isEmpty() || catalog.length() > 100) {
                System.out.println("Lỗi: Danh mục sản phẩm không được để trống và tối đa 100 ký tự!");
                continue;
            }
            if (!dao.isCatalogExists(catalog)) {
                System.out.println("Thông báo: Danh mục '" + catalog + "' là danh mục mới (chưa có sản phẩm nào trước đó).");
            } else {
                System.out.println("Thông báo: Danh mục '" + catalog + "' đã tồn tại trong hệ thống.");
            }
            break;
        }
        boolean status;
        while (true) {
            System.out.print("Nhập trạng thái sản phẩm (true = Hoạt động / false = Ngừng bán, mặc định true): ");
            String statusStr = scanner.nextLine().trim();
            if (statusStr.isEmpty()) {
                status = true;
                break;
            } else if (statusStr.equalsIgnoreCase("true") || statusStr.equalsIgnoreCase("1")) {
                status = true;
                break;
            } else if (statusStr.equalsIgnoreCase("false") || statusStr.equalsIgnoreCase("0")) {
                status = false;
                break;
            } else {
                System.out.println("Lỗi: Chỉ chấp nhận giá trị true/false hoặc 1/0!");
            }
        }
        return new Product(0, name, price, title, createdDate, catalog, status);
    }
    private static int inputInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Sai định dạng số nguyên, vui lòng nhập lại: ");
            }
        }
    }
}