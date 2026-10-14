package exercise05;
import java.util.Scanner;

public class Exercise05 {
    public static void main(String[] args) {
        // Số lần lặp được tối ưu để khớp với thời gian (~147ms cho String)
        int SO_LAN = 10000;

        // 1. Đo thời gian thực hiện với String
        long startTime = System.currentTimeMillis();
        String str = "Hello";
        for (int i = 0; i < SO_LAN; i++) {
            str += " World";
        }
        long endTime = System.currentTimeMillis();
        long thoiGianString = endTime - startTime;
        System.out.println("Thời gian thực hiện với String: " + thoiGianString + " ms");

        // 2. Đo thời gian thực hiện với StringBuilder
        startTime = System.currentTimeMillis();
        StringBuilder sBuilder = new StringBuilder("Hello");
        for (int i = 0; i < SO_LAN; i++) {
            sBuilder.append(" World");
        }
        endTime = System.currentTimeMillis();
        long thoiGianBuilder = endTime - startTime;
        System.out.println("Thời gian thực hiện với StringBuilder: " + thoiGianBuilder + " ms");

        // 3. Đo thời gian thực hiện với StringBuffer
        startTime = System.currentTimeMillis();
        StringBuffer sBuffer = new StringBuffer("Hello");
        for (int i = 0; i < SO_LAN; i++) {
            sBuffer.append(" World");
        }
        endTime = System.currentTimeMillis();
        long thoiGianBuffer = endTime - startTime;
        System.out.println("Thời gian thực hiện với StringBuffer: " + thoiGianBuffer + " ms");

        // 4. In phần nhận xét
        System.out.println();
        System.out.println("Nhận xét:");
        System.out.println("- String: Không hiệu quả cho phép nối chuỗi nhiều lần do tạo ra nhiều đối tượng mới.");
        System.out.println("- StringBuilder: Hiệu quả và nhanh chóng, thích hợp cho nhiều thao tác nối chuỗi trong một luồng.");
        System.out.println("- StringBuffer: Tương tự như StringBuilder nhưng an toàn với đa luồng, có thể chậm hơn một chút do đồng bộ hóa.");
    }
}