// 1. Lớp trừu tượng PaymentMethod
abstract class PaymentMethod {
    protected double amount;

    public PaymentMethod(double amount) {
        this.amount = amount;
    }

    public abstract double processPayment();
    public abstract double calculateFee();
    public abstract String processPayment(String currency);
}

// 2. Lớp con CreditCard (Phí 2%)
class CreditCard extends PaymentMethod {

    public CreditCard(double amount) {
        super(amount);
    }

    @Override
    public double calculateFee() {
        return amount * 0.02;
    }

    @Override
    public double processPayment() {
        return amount + calculateFee();
    }

    @Override
    public String processPayment(String currency) {
        return processPayment() + " " + currency;
    }
}

// 3. Lớp con DebitCard (Phí 1%)
class DebitCard extends PaymentMethod {

    public DebitCard(double amount) {
        super(amount);
    }

    @Override
    public double calculateFee() {
        return amount * 0.01;
    }

    @Override
    public double processPayment() {
        return amount + calculateFee();
    }

    @Override
    public String processPayment(String currency) {
        return processPayment() + " " + currency;
    }
}

// 4. Lớp con Cash (Miễn phí - 0%)
class Cash extends PaymentMethod {

    public Cash(double amount) {
        super(amount);
    }

    @Override
    public double calculateFee() {
        return 0.0;
    }

    @Override
    public double processPayment() {
        return amount + calculateFee();
    }

    @Override
    public String processPayment(String currency) {
        return processPayment() + " " + currency;
    }
}

// 5. Lớp Main chạy giao dịch
public class Exercise10 {
    public static void main(String[] args) {
        // Khởi tạo các phương thức thanh toán bằng tính đa hình
        PaymentMethod credit = new CreditCard(1000.0);
        PaymentMethod debit = new DebitCard(1000.0);
        PaymentMethod cash = new Cash(1000.0);

        System.out.println("=== THANH TOÁN BẰNG CREDIT CARD ===");
        System.out.println("Phí giao dịch: " + credit.calculateFee());
        System.out.println("Tổng tiền phải trả: " + credit.processPayment());
        System.out.println("Tổng tiền (kèm đơn vị): " + credit.processPayment("USD"));

        System.out.println("\n=== THANH TOÁN BẰNG DEBIT CARD ===");
        System.out.println("Phí giao dịch: " + debit.calculateFee());
        System.out.println("Tổng tiền phải trả: " + debit.processPayment());
        System.out.println("Tổng tiền (kèm đơn vị): " + debit.processPayment("USD"));

        System.out.println("\n=== THANH TOÁN BẰNG TIỀN MẶT (CASH) ===");
        System.out.println("Phí giao dịch: " + cash.calculateFee());
        System.out.println("Tổng tiền phải trả: " + cash.processPayment());
        System.out.println("Tổng tiền (kèm đơn vị): " + cash.processPayment("VND"));
    }
}