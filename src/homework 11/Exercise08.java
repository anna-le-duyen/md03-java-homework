import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Order {
    private int id;
    private String customerName;
    private LocalDate createdDate;
    private Optional<LocalDate> deliveryDate;

    public Order() {
        this.deliveryDate = Optional.empty();
    }

    public Order(int id, String customerName, LocalDate createdDate, LocalDate deliveryDate) {
        this.id = id;
        this.customerName = customerName;
        this.createdDate = createdDate;
        this.deliveryDate = Optional.ofNullable(deliveryDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Optional<LocalDate> getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = Optional.ofNullable(deliveryDate);
    }

    public String toDisplayString() {
        String deliveryStr = deliveryDate
                .map(LocalDate::toString)
                .orElse("Chưa giao");
        return String.format("ID: %d | Tên KH: %s | Ngày đặt: %s | Ngày giao: %s",
                id, customerName, createdDate, deliveryStr);
    }
}

public class Exercise08 {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order(1, "Quý", LocalDate.of(2025, 3, 20), LocalDate.of(2025, 3, 25)),
                new Order(2, "Lan", LocalDate.of(2025, 3, 21), null),
                new Order(3, "Minh", LocalDate.of(2025, 3, 22), LocalDate.of(2025, 3, 23)),
                new Order(4, "Huyền", LocalDate.of(2025, 3, 23), null),
                new Order(5, "Việt", LocalDate.of(2025, 3, 23), LocalDate.of(2025, 3, 30))
        );

        // 1. Liệt kê các đơn hàng đã được giao
        System.out.println("====== DANH SÁCH ĐƠN HÀNG ĐÃ GIAO ======");
        List<Order> deliveredOrders = orders.stream()
                .filter(order -> order.getDeliveryDate().isPresent())
                .collect(Collectors.toList());
        deliveredOrders.forEach(order -> System.out.println(order.toDisplayString()));

        // 2. Liệt kê các đơn hàng chưa được giao
        System.out.println("\n======= DANH SÁCH ĐƠN HÀNG CHƯA GIAO ======");
        List<Order> undeliveredOrders = orders.stream()
                .filter(order -> order.getDeliveryDate().isEmpty())
                .collect(Collectors.toList());
        undeliveredOrders.forEach(order -> System.out.println(order.toDisplayString()));

        // 3. Đếm số đơn hàng đã giao trong khoảng từ ngày 2025-03-17 đến 2025-03-23
        LocalDate startDate = LocalDate.of(2025, 3, 17);
        LocalDate endDate = LocalDate.of(2025, 3, 23);

        long deliveredCount = orders.stream()
                .filter(order -> order.getDeliveryDate()
                        .map(date -> !date.isBefore(startDate) && !date.isAfter(endDate))
                        .orElse(false))
                .count();
        System.out.println("\nSố đơn hàng đã giao từ 2025-03-17 đến 2025-03-23: " + deliveredCount);

        // 4. In thông tin đơn hàng
        System.out.println("\n=== TẤT CẢ ĐƠN HÀNG ===");
        orders.forEach(order -> System.out.println(order.toDisplayString()));
    }
}