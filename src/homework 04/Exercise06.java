package exercise06;
import java.util.Arrays;
import java.util.Scanner;

public class Exercise06 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Nhập dữ liệu đầu vào
        System.out.print("Nhập kích thước mảng: ");
        int n = scanner.nextInt();

        System.out.println("Nhập các phần tử cho mảng:");
        int[] mang = new int[n];
        for (int i = 0; i < n; i++) {
            mang[i] = scanner.nextInt();
        }

        System.out.print("Nhập số cần tìm: ");
        int x = scanner.nextInt();

        // 2. Sắp xếp mảng giảm dần bằng thuật toán Selection Sort
        for (int i = 0; i < n - 1; i++) {
            int indexMax = i;
            for (int j = i + 1; j < n; j++) {
                if (mang[j] > mang[indexMax]) {
                    indexMax = j;
                }
            }
            int temp = mang[i];
            mang[i] = mang[indexMax];
            mang[indexMax] = temp;
        }

        // 3. Tìm kiếm tuyến tính
        int viTriTuyenTinh = timKiemTuyenTinh(mang, x);

        // 4. Tìm kiếm nhị phân
        int viTriNhiPhan = timKiemNhiPhanGiamDan(mang, x);

        // 5. Hiển thị Output
        System.out.println(); // Xuống dòng phân tách vùng kết quả
        System.out.println("Mảng sau khi sắp xếp theo thứ tự giảm dần: " + Arrays.toString(mang));

        // Kết quả tìm kiếm tuyến tính
        if (viTriTuyenTinh != -1) {
            System.out.println("Tìm kiếm tuyến tính: Phần tử " + x + " tìm thấy tại chỉ số: " + viTriTuyenTinh);
        } else {
            System.out.println("Tìm kiếm tuyến tính: Không tìm thấy phần tử " + x);
        }

        // Kết quả tìm kiếm nhị phân
        if (viTriNhiPhan != -1) {
            System.out.println("Tìm kiếm nhị phân: Phần tử " + x + " tìm thấy tại chỉ số: " + viTriNhiPhan);
        } else {
            System.out.println("Tìm kiếm nhị phân: Không tìm thấy phần tử " + x);
        }

        scanner.close();
    }

    // Hàm Tìm kiếm Tuyến tính
    public static int timKiemTuyenTinh(int[] mang, int x) {
        for (int i = 0; i < mang.length; i++) {
            if (mang[i] == x) {
                return i;
            }
        }
        return -1;
    }

    // Hàm Tìm kiếm Nhị phân trên mảng giảm dần
    public static int timKiemNhiPhanGiamDan(int[] mang, int x) {
        int left = 0;
        int right = mang.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (mang[mid] == x) {
                return mid;
            }
            if (mang[mid] < x) {
                right = mid - 1; // Số lớn nằm bên trái do mảng giảm dần
            } else {
                left = mid + 1;  // Số nhỏ nằm bên phải
            }
        }
        return -1;
    }
}