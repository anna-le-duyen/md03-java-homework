import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Message {
    private String sender;
    private String content;
    private LocalDateTime timestamp;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return String.format("[%s] %s: %s", timestamp.format(formatter), sender, content);
    }
}

public class Exercise03 {
    public static void main(String[] args) {
        List<Message> messages = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Nhập tên người gửi (hoặc 'exit' để thoát):");
            String sender = scanner.nextLine();

            if (sender.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.println("Nhập nội dung tin nhắn:");
            String content = scanner.nextLine();

            messages.add(new Message(sender, content));

            System.out.println("Nhập 'history' để xem lịch sử, hoặc 'filter' để lọc tin nhắn theo người gửi, hoặc 'date' để lọc theo ngày:");
            String command = scanner.nextLine();

            switch (command.trim().toLowerCase()) {
                case "history":
                    System.out.println("Lịch sử chat:");
                    messages.forEach(System.out::println);
                    break;

                case "filter":
                    System.out.println("Nhập tên người gửi để lọc:");
                    String filterSender = scanner.nextLine();
                    List<Message> filteredBySender = messages.stream()
                            .filter(m -> m.getSender().equalsIgnoreCase(filterSender.trim()))
                            .collect(Collectors.toList());

                    System.out.println("Tin nhắn từ " + filterSender + ":");
                    filteredBySender.forEach(System.out::println);
                    break;

                case "date":
                    System.out.println("Nhập ngày (dd-MM-yyyy):");
                    String dateStr = scanner.nextLine();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                    try {
                        LocalDate targetDate = LocalDate.parse(dateStr.trim(), dateFormatter);
                        List<Message> filteredByDate = messages.stream()
                                .filter(m -> m.getTimestamp().toLocalDate().equals(targetDate))
                                .collect(Collectors.toList());

                        System.out.println("Tin nhắn trong ngày " + targetDate + ":");
                        filteredByDate.forEach(System.out::println);
                    } catch (DateTimeParseException e) {
                        System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng dd-MM-yyyy.");
                    }
                    break;

                default:
                    break;
            }
        }
        scanner.close();
    }
}