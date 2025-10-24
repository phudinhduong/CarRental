package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.config.VnPayConfig;
import hsf302.he187383.phudd.carrental.service.VnPayService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/api")
public class VnPayController {

    private final VnPayService vnPayService;
    private final VnPayConfig vnPayConfig;

    public VnPayController(VnPayService vnPayService, VnPayConfig vnPayConfig) {
        this.vnPayService = vnPayService;
        this.vnPayConfig = vnPayConfig;
    }

    @GetMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createPayment(@RequestParam String bookingId,
                                           @RequestParam long amount) {
        try {
            String url = vnPayService.createPaymentUrl(bookingId, amount);
            Map<String, Object> res = new HashMap<>();
            res.put("paymentUrl", url);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> res = new HashMap<>();
            res.put("paymentUrl", null);
            res.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/payment/vnpay-return")
    public RedirectView vnpayReturn(@RequestParam Map<String, String> params) {
        try {
            System.out.println("=== VNPay Return Debug ===");
            System.out.println("All params: " + params);

            String vnp_SecureHash = params.get("vnp_SecureHash");

            // Loại bỏ các tham số không tham gia verify
            params.remove("vnp_SecureHash");
            params.remove("vnp_SecureHashType");

            // Sắp xếp các tham số theo thứ tự alphabet
            List<String> fieldNames = new ArrayList<>(params.keySet());
            Collections.sort(fieldNames);

            // Tạo chuỗi hashData từ các tham số đã sắp xếp
            StringBuilder hashData = new StringBuilder();
            boolean first = true;
            for (String key : fieldNames) {
                String value = params.get(key);
                if (value != null && value.length() > 0) {
                    if (!first) {
                        hashData.append('&');
                    } else {
                        first = false;
                    }
                    // Sử dụng giá trị GỐC (đã được decode tự động bởi Spring)
                    hashData.append(key).append('=').append(value);
                }
            }

            System.out.println("HashData for verify: " + hashData);

            // Tính chữ ký
            String signValue = vnPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());

            System.out.println("Calculated hash: " + signValue);
            System.out.println("Received hash: " + vnp_SecureHash);
            System.out.println("Hash match: " + signValue.equals(vnp_SecureHash));
            System.out.println("========================");

            String vnp_ResponseCode = params.get("vnp_ResponseCode");
            String vnp_TxnRef = params.get("vnp_TxnRef");

            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(vnp_ResponseCode)) {
                    System.out.println("Payment success: " + vnp_TxnRef);
                    return new RedirectView("/carrental/payment-success.html?bookingId=" + vnp_TxnRef);
                } else {
                    System.out.println("Payment failed: " + vnp_TxnRef);
                    return new RedirectView("/carrental/payment-failed.html?code=" + vnp_ResponseCode);
                }
            } else {
                System.out.println("Invalid signature");
                return new RedirectView("/carrental/payment-failed.html?code=97");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("/carrental/payment-failed.html?code=99");
        }
    }
}
