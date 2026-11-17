package exercise01;

import java.util.Arrays;
import java.util.List;

public class Exercise01 {
    public static void main(String[] args) {
        // Tạo danh sách số nguyên
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Sử dụng Lambda Expression để in ra tất cả các số chẵn
        System.out.println("Các số chẵn trong danh sách:");
        numbers.forEach(number -> {
            if (number % 2 == 0) {
                System.out.println(number);
            }
        });

        // Sử dụng phuương thức tham chiêu để tính tổng của tất cả các số trong danh sách
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Tổng của tất cả các số trong danh sách: " + sum);
    }
}