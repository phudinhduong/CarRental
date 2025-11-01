package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.config.VnPayConfig;
import hsf302.he187383.phudd.carrental.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api")
public class VnPayController {

    @Autowired
    private VnPayService vnPayService;

    @Autowired
    private VnPayConfig vnPayConfig;

    // Tạo URL thanh toán
    @GetMapping("/create")
    @ResponseBody
    public String createPayment(@RequestParam("bookingId") int bookingId,
                                @RequestParam("amount") double amount,
                                HttpServletRequest request) throws UnsupportedEncodingException {
        return vnPayService.createPaymentUrl(bookingId, amount, request);
    }

    // Xử lý khi VNPAY redirect về
    @GetMapping("/payment/vnpay-return")
    public RedirectView vnpayReturn(HttpServletRequest request) {
        try {
            System.out.println("=== VNPay Return Debug ===");

            // Lấy tất cả params từ request
            Map<String, String> params = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();

            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                params.put(paramName, paramValue);
                System.out.println(paramName + " = " + paramValue);
            }

            // Lấy vnp_SecureHash từ params
            String receivedHash = params.get("vnp_SecureHash");
            if (receivedHash == null || receivedHash.isEmpty()) {
                System.out.println("❌ Không tìm thấy vnp_SecureHash");
                return new RedirectView("/carrental/payment-failed.html?code=98");
            }

            // Loại bỏ vnp_SecureHash và vnp_SecureHashType khỏi params
            params.remove("vnp_SecureHash");
            params.remove("vnp_SecureHashType");

            // Sắp xếp params theo thứ tự alphabet
            List<String> fieldNames = new ArrayList<>(params.keySet());
            Collections.sort(fieldNames);

            // Tạo hashData
            StringBuilder hashData = new StringBuilder();
            Iterator<String> it = fieldNames.iterator();

            while (it.hasNext()) {
                String fieldName = it.next();
                String fieldValue = params.get(fieldName);

                if (fieldValue != null && !fieldValue.isEmpty()) {
                    // Encode giống như khi tạo payment URL
                    hashData.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                    if (it.hasNext()) {
                        hashData.append('&');
                    }
                }
            }

            String hashDataStr = hashData.toString();
            System.out.println("HashData: " + hashDataStr);

            // Tính secure hash
            String calculatedHash = vnPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashDataStr);
            System.out.println("Calculated Hash: " + calculatedHash);
            System.out.println("Received Hash:   " + receivedHash);
            System.out.println("==========================");

            // So sánh hash
            if (!calculatedHash.equalsIgnoreCase(receivedHash)) {
                System.out.println("⚠️ Sai chữ ký!");
                return new RedirectView("/carrental/payment-failed.html?code=97");
            }

            // Kiểm tra response code
            String responseCode = params.get("vnp_ResponseCode");
            String bookingId = params.get("vnp_TxnRef");

            if ("00".equals(responseCode)) {
                System.out.println("✅ Thanh toán thành công!");
                return new RedirectView("/carrental/payment-success.html?bookingId=" + bookingId);
            } else {
                System.out.println("❌ Thanh toán thất bại với mã: " + responseCode);
                return new RedirectView("/carrental/payment-failed.html?code=" + responseCode);
            }

        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
            e.printStackTrace();
            return new RedirectView("/carrental/payment-failed.html?code=99");
        }
    }
}