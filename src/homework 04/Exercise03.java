package exercise03;

import java.util.Scanner;

public class Exercise03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Khởi tạo mảng
        System.out.print("Nhập kích thước của mảng: ");
        int size = scanner.nextInt();
        int[] array = new int[size];

        System.out.println("Nhập các giá trị cho mảng:");
        for (int i = 0; i < size; i++) {
            System.out.print("Giá trị thứ " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }

        // Sắp xếp mảng bằng thuật toán sắp xếp nổi bọt
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] < array[j + 1]) {
                    // Hoán đổi
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            // Nếu không có sự hoán đổi nào, mảng đã sắp xếp xong
            if (!swapped) break;
        }

        // Đầu ra
        System.out.println("Mảng đã sắp xếp theo thứ tự giảm dần:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}