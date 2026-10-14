package exercise01;

import java.util.Scanner;

public class Exercise01 {
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

        // Sắp xếp mảng bằng thuật toán sắp xếp chọn
        selectionSort(array);

        // Đầu ra
        System.out.println("Mảng đã sắp xếp theo thứ tự giảm dần:");
        for (int value : array) {
            System.out.print(value + " ");
        }

        // Phần tử lớn nhất
        System.out.println("\nPhần tử lớn nhất trong mảng: " + array[0]);
    }

    public static void selectionSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] > array[maxIndex]) {
                    maxIndex = j;
                }
            }
            // Hoán đổi phần tử lớn nhất với phần tử đầu tiên chưa được sắp xếp
            int temp = array[maxIndex];
            array[maxIndex] = array[i];
            array[i] = temp;
        }
    }

}