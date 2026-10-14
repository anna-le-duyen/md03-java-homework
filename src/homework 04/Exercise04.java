package exercise04;

import java.util.Scanner;

public class Exercise04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nhập kích thước của mảng
        System.out.print("Nhập kích thước của mảng: ");
        int size = scanner.nextInt();

        // Kiểm tra kích thước mảng
        if (size <= 0) {
            System.out.println("Kích thước rỗng");
            return;
        }

        int[] array = new int[size];

        // Nhập giá trị cho từng phần tử của mảng
        System.out.println("Nhập các giá trị cho mảng:");
        for (int i = 0; i < size; i++) {
            System.out.print("Giá trị thứ " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }

        // Hiển thị mảng ban đầu
        System.out.println("Mảng ban đầu:");
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Đảo ngược mảng
        for (int i = 0; i < size / 2; i++) {
            // Hoán đổi phần tử
            int temp = array[i];
            array[i] = array[size - 1 - i];
            array[size - 1 - i] = temp;
        }

        // Hiển thị mảng sau khi đảo ngược
        System.out.println("Mảng sau khi đảo ngược:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}