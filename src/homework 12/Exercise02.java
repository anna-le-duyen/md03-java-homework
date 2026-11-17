import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

class Appointment {
    private String appointmentId;
    private String patientName;
    private String phoneNumber;
    private LocalDate appointmentDate;
    private String doctor;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public Appointment() {
    }

    public Appointment(String appointmentId, String patientName, String phoneNumber, LocalDate appointmentDate, String doctor) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.phoneNumber = phoneNumber;
        this.appointmentDate = appointmentDate;
        this.doctor = doctor;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void inputData(Scanner scanner) {
        inputData(scanner, new ArrayList<>());
    }

    public void inputData(Scanner scanner, List<Appointment> existingAppointments) {
        this.appointmentId = inputAppointmentId(scanner, existingAppointments);
        this.patientName = inputPatientName(scanner);
        this.phoneNumber = inputPhoneNumber(scanner);
        this.appointmentDate = inputAppointmentDate(scanner);
        this.doctor = inputDoctor(scanner);
    }

    public static String inputAppointmentId(Scanner scanner, List<Appointment> existingAppointments) {
        while (true) {
            System.out.print("Nhập mã lịch hẹn (6 ký tự): ");
            String id = scanner.nextLine().trim();
            if (id.length() != 6) {
                System.out.println("Lỗi: Mã lịch hẹn phải gồm đúng 6 ký tự!");
                continue;
            }
            boolean isDuplicate = existingAppointments.stream()
                    .anyMatch(a -> a.getAppointmentId().equalsIgnoreCase(id));
            if (isDuplicate) {
                System.out.println("Lỗi: Mã lịch hẹn đã tồn tại!");
                continue;
            }
            return id;
        }
    }

    public static String inputPatientName(Scanner scanner) {
        while (true) {
            System.out.print("Nhập tên bệnh nhân (10-50 ký tự): ");
            String name = scanner.nextLine().trim();
            if (name.length() < 10 || name.length() > 50) {
                System.out.println("Lỗi: Tên bệnh nhân phải có độ dài từ 10 đến 50 ký tự!");
                continue;
            }
            return name;
        }
    }

    public static String inputPhoneNumber(Scanner scanner) {
        String vnPhoneRegex = "^(0[3|5|7|8|9])[0-9]{8}$";
        while (true) {
            System.out.print("Nhập số điện thoại liên hệ (10 số di động VN): ");
            String phone = scanner.nextLine().trim();
            if (!phone.matches(vnPhoneRegex)) {
                System.out.println("Lỗi: Số điện thoại không đúng định dạng di động Việt Nam (ví dụ: 0912345678)!");
                continue;
            }
            return phone;
        }
    }

    public static LocalDate inputAppointmentDate(Scanner scanner) {
        while (true) {
            System.out.print("Nhập ngày hẹn khám (dd/MM/yyyy): ");
            String dateStr = scanner.nextLine().trim();
            try {
                return LocalDate.parse(dateStr, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Lỗi: Ngày hẹn khám không hợp lệ hoặc sai định dạng dd/MM/yyyy!");
            }
        }
    }

    public static String inputDoctor(Scanner scanner) {
        while (true) {
            System.out.print("Nhập tên bác sĩ phụ trách (tối đa 200 ký tự): ");
            String doctor = scanner.nextLine().trim();
            if (doctor.isEmpty() || doctor.length() > 200) {
                System.out.println("Lỗi: Tên bác sĩ không được để trống và tối đa 200 ký tự!");
                continue;
            }
            return doctor;
        }
    }

    @Override
    public String toString() {
        return String.format("Mã LH: %-6s | Bệnh nhân: %-25s | SĐT: %-10s | Ngày hẹn: %-10s | Bác sĩ: %s",
                appointmentId,
                patientName,
                phoneNumber,
                appointmentDate.format(DATE_FORMATTER),
                doctor);
    }
}

class AppointmentBusiness {
    private List<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Scanner scanner) {
        Appointment appointment = new Appointment();
        appointment.inputData(scanner, appointments);
        appointments.add(appointment);
        System.out.println("Thêm lịch hẹn mới thành công!");
    }

    public void displayAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("Danh sách lịch hẹn trống.");
            return;
        }
        System.out.println("--- DANH SÁCH LỊCH HẸN KHÁM (SẮP XẾP THEO NGÀY HẸN TĂNG DẦN) ---");
        appointments.stream()
                .sorted(Comparator.comparing(Appointment::getAppointmentDate))
                .forEach(System.out::println);
    }

    public void searchByPatientName(Scanner scanner) {
        System.out.print("Nhập tên bệnh nhân cần tìm kiếm: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        List<Appointment> result = appointments.stream()
                .filter(a -> a.getPatientName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("Không tìm thấy lịch hẹn nào cho bệnh nhân có tên chứa: " + keyword);
        } else {
            System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
            result.forEach(System.out::println);
        }
    }

    public void updateAppointment(Scanner scanner) {
        System.out.print("Nhập mã lịch hẹn cần cập nhật: ");
        String id = scanner.nextLine().trim();

        findById(id).ifPresentOrElse(
                appointment -> {
                    System.out.println("Thông tin hiện tại: " + appointment);
                    appointment.setPatientName(Appointment.inputPatientName(scanner));
                    appointment.setPhoneNumber(Appointment.inputPhoneNumber(scanner));
                    appointment.setAppointmentDate(Appointment.inputAppointmentDate(scanner));
                    appointment.setDoctor(Appointment.inputDoctor(scanner));
                    System.out.println("Cập nhật lịch hẹn thành công!");
                },
                () -> System.out.println("Không tìm thấy lịch hẹn có mã: " + id)
        );
    }

    public void deleteAppointment(Scanner scanner) {
        System.out.print("Nhập mã lịch hẹn cần xóa: ");
        String id = scanner.nextLine().trim();

        findById(id).ifPresentOrElse(
                appointment -> {
                    System.out.println("Tìm thấy lịch hẹn: " + appointment);
                    System.out.print("Bạn có chắc chắn muốn xóa lịch hẹn này không? (Y/N): ");
                    String confirm = scanner.nextLine().trim();
                    if (confirm.equalsIgnoreCase("Y")) {
                        appointments.remove(appointment);
                        System.out.println("Đã xóa lịch hẹn thành công!");
                    } else {
                        System.out.println("Đã hủy thao tác xóa.");
                    }
                },
                () -> System.out.println("Không tìm thấy lịch hẹn có mã: " + id)
        );
    }

    public void statisticAppointments() {
        System.out.println("--- THỐNG KÊ LỊCH HẸN KHÁM BỆNH ---");
        System.out.println("Tổng số lịch hẹn: " + appointments.size());

        if (appointments.isEmpty()) {
            return;
        }

        Map<String, Long> countByDoctor = appointments.stream()
                .collect(Collectors.groupingBy(Appointment::getDoctor, Collectors.counting()));

        System.out.println("Số lịch hẹn theo từng bác sĩ:");
        countByDoctor.forEach((doctor, count) ->
                System.out.printf(" - Bác sĩ %s: %d lịch hẹn%n", doctor, count)
        );
    }

    private Optional<Appointment> findById(String id) {
        return appointments.stream()
                .filter(a -> a.getAppointmentId().equalsIgnoreCase(id))
                .findFirst();
    }
}

public class Exercise02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AppointmentBusiness appointmentBusiness = new AppointmentBusiness();

        while (true) {
            System.out.println("\n*********************QUẢN LÝ LỊCH HẸN********************");
            System.out.println("1. Thêm lịch hẹn");
            System.out.println("2. Hiển thị danh sách lịch hẹn");
            System.out.println("3. Tìm kiếm lịch hẹn theo tên bệnh nhân");
            System.out.println("4. Cập nhật lịch hẹn theo mã lịch hẹn");
            System.out.println("5. Xóa lịch hẹn theo mã lịch hẹn");
            System.out.println("6. Thống kê số lượng lịch hẹn theo bác sĩ");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng (0-6): ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    appointmentBusiness.addAppointment(scanner);
                    break;
                case "2":
                    appointmentBusiness.displayAppointments();
                    break;
                case "3":
                    appointmentBusiness.searchByPatientName(scanner);
                    break;
                case "4":
                    appointmentBusiness.updateAppointment(scanner);
                    break;
                case "5":
                    appointmentBusiness.deleteAppointment(scanner);
                    break;
                case "6":
                    appointmentBusiness.statisticAppointments();
                    break;
                case "0":
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            };
        }
    }
}