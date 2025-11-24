import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
public static void main(String[] args) {
// Đọc toàn bộ input vào một chuỗi (Hệ thống của bạn đưa input vào System.in)
Scanner scanner = new Scanner(System.in);

        // HashMap để đếm số lần xuất hiện của từng IP
        Map<String, Integer> ipCounts = new HashMap<>();
        int maxRequests = 0;

        // Đọc từng dòng log
        // Với 100.000 dòng, vòng lặp này sẽ chạy khá lâu
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) continue;

            // Cắt chuỗi để lấy IP (Định dạng: TIMESTAMP IP URL)
            String[] parts = line.split(" ");
            if (parts.length >= 2) {
                String ip = parts[1];
                
                // Tăng biến đếm
                int count = ipCounts.getOrDefault(ip, 0) + 1;
                ipCounts.put(ip, count);
                
                if (count > maxRequests) {
                    maxRequests = count;
                }
            }
        }
        
        // Demo chạy ngầm: Ngủ thêm 3 giây sau khi tính toán xong
        // Để giáo viên kịp nhìn thấy trạng thái PENDING
        try { Thread.sleep(3000); } catch (Exception e) {}

        System.out.print(maxRequests);
        scanner.close();
    }
}