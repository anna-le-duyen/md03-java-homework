import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class User {
    private int id;
    private String name;
    private Optional<String> email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = Optional.ofNullable(email);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Optional.ofNullable(email);
    }

    public String toDisplayString() {
        String emailStr = email.orElse("Không có email");
        return String.format("ID: %d | Tên: %s | Email: %s", id, name, emailStr);
    }
}

class Task {
    private int id;
    private String title;
    private User assignedTo;
    private LocalDate dueDate;
    private boolean completed;

    public Task(int id, String title, User assignedTo, LocalDate dueDate, boolean completed) {
        this.id = id;
        this.title = title;
        this.assignedTo = assignedTo;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String toDisplayString() {
        String statusStr = completed ? "Đã hoàn thành" : "Chưa hoàn thành";
        return String.format("ID: %d | Công việc: %s | Người thực hiện: %s | Hạn chót: %s | Trạng thái: %s",
                id, title, assignedTo.getName(), dueDate, statusStr);
    }
}

public class Exercise09 {
    public static void main(String[] args) {
        // Khởi tạo danh sách người dùng
        User u1 = new User(1, "Quý", "quy@example.com");
        User u2 = new User(2, "Lan", null);
        User u3 = new User(3, "Huyền", "huyen@example.com");

        List<User> users = List.of(u1, u2, u3);

        // Khởi tạo danh sách công việc
        List<Task> tasks = List.of(
                new Task(1, "Viết báo cáo", u1, LocalDate.of(2025, 3, 20), false),
                new Task(2, "Thiết kế slide", u1, LocalDate.of(2025, 3, 25), true),
                new Task(3, "Họp nhóm", u2, LocalDate.of(2025, 3, 18), false),
                new Task(4, "Nộp tài liệu", u3, LocalDate.of(2025, 3, 22), true),
                new Task(5, "Chuẩn bị thuyết trình", u2, LocalDate.of(2025, 3, 19), true)
        );

        // 1. Liệt kê các công việc quá hạn và chưa hoàn thành
        LocalDate today = LocalDate.now();
        System.out.println("====== CÔNG VIỆC QUÁ HẠN VÀ CHƯA HOÀN THÀNH ======");
        List<Task> overdueTasks = tasks.stream()
                .filter(task -> !task.isCompleted() && task.getDueDate().isBefore(today))
                .collect(Collectors.toList());
        overdueTasks.forEach(task -> System.out.println(task.toDisplayString()));

        // 2. Thống kê số công việc đã hoàn thành
        long completedCount = tasks.stream()
                .filter(Task::isCompleted)
                .count();
        System.out.println("\nTổng số công việc đã hoàn thành: " + completedCount);

        // 3. In danh sách người dùng kèm thông tin: Tên, Email (nếu có)
        System.out.println("\n====== DANH SÁCH NGƯỜI DÙNG ======");
        users.forEach(user -> System.out.println(user.toDisplayString()));
    }
}