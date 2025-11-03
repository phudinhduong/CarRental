package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.config.VNPayConfig;
import hsf302.he187383.phudd.carrental.service.VNPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class VNPayController {

    @Autowired
    private VNPayConfig vnPayConfig;

    @GetMapping("/create-payment")
    @ResponseBody
    public String createPayment(@RequestParam("amount") long amount, // Sử dụng long cho an toàn
                                HttpServletRequest request) throws Exception {

        String vnp_TmnCode = vnPayConfig.getTmnCode();
        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
        String vnp_IpAddr = request.getRemoteAddr(); // Cân nhắc dùng hàm lấy IP chính xác hơn

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang #" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String createDate = LocalDateTime.now().format(formatter);
        vnp_Params.put("vnp_CreateDate", createDate);

        String expireDate = LocalDateTime.now().plusMinutes(15).format(formatter);
        vnp_Params.put("vnp_ExpireDate", expireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                // Build hash data
                if (hashData.length() > 0) {
                    hashData.append('&');
                }
                hashData.append(fieldName).append('=').append(fieldValue);

                // Build query string
                if (query.length() > 0) {
                    query.append('&');
                }
                // SỬA LỖI 2: Dùng UTF-8
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
            }
        }

        String vnp_SecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash); // Hash không cần encode vì là hex

        String paymentUrl = vnPayConfig.getPayUrl() + "?" + query;

        System.out.println("=== VNPAY CREATE PAYMENT ===");
        System.out.println("HashData (chuỗi ký): " + hashData);
        System.out.println("SecureHash gửi đi: " + vnp_SecureHash);
        System.out.println("Payment URL: " + paymentUrl);

        return paymentUrl;
    }

    /**
     * SỬA LỖI 1 & 3:
     * Dùng @RequestParam Map<String, String> để Spring tự động
     * phân tích và URL-decode tất cả tham số.
     */
    @GetMapping("/api/payment/vnpay-return")
    @ResponseBody
    public String vnpayReturn(@RequestParam Map<String, String> vnp_Params, HttpServletRequest request) {

        // Lấy vnp_SecureHash ra khỏi map
        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");
        vnp_Params.remove("vnp_SecureHashType"); // Xóa luôn nếu có

        // Lấy danh sách tên field và sắp xếp
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        // Tạo chuỗi hashData
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);

            // SỬA LỖI 3: Chỉ append nếu fieldValue không rỗng
            if (fieldValue != null && !fieldValue.isEmpty()) {
                if (hashData.length() > 0) {
                    hashData.append('&');
                }
                // Các giá trị trong vnp_Params đã được Spring decode
                hashData.append(fieldName).append('=').append(fieldValue);
            }
        }

        String computedHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());

        System.out.println("=== VNPAY RETURN ===");
        System.out.println("Raw query (để debug): " + request.getQueryString());
        System.out.println("HashData (chuỗi ký): " + hashData);
        System.out.println("SecureHash nhận về: " + vnp_SecureHash);
        System.out.println("SecureHash tính lại: " + computedHash);

        if (computedHash.equalsIgnoreCase(vnp_SecureHash)) {
            String responseCode = vnp_Params.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                // TODO: Xử lý logic nghiệp vụ khi thanh toán thành công
                // (ví dụ: cập nhật trạng thái đơn hàng, lưu giao dịch)
                return "✅ Thanh toán thành công! Mã giao dịch: " + vnp_Params.get("vnp_TransactionNo");
            } else {
                // TODO: Xử lý logic nghiệp vụ khi thanh toán thất bại
                return "❌ Thanh toán thất bại! Mã lỗi: " + responseCode;
            }
        } else {
            return "⚠️ Sai chữ ký! (Invalid signature)";
        }
    }
}