package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.config.VnPayConfig;
import hsf302.he187383.phudd.carrental.service.VnPayService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
            String vnp_SecureHash = params.get("vnp_SecureHash");
            params.remove("vnp_SecureHash");
            params.remove("vnp_SecureHashType");

            // Tạo hash để verify
            List<String> fieldNames = new ArrayList<>(params.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            for (String fieldName : fieldNames) {
                String fieldValue = params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    if (hashData.length() > 0) {
                        hashData.append('&');
                    }
                    hashData.append(fieldName).append('=').append(fieldValue);
                }
            }

            String signValue = vnPayConfig.hmacSHA512(vnPayConfig.getVnpSecretKey(), hashData.toString());

            String vnp_ResponseCode = params.get("vnp_ResponseCode");
            String vnp_TxnRef = params.get("vnp_TxnRef");

            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(vnp_ResponseCode)) {
                    // Thanh toán thành công
                    System.out.println("Payment success for booking: " + vnp_TxnRef);
                    return new RedirectView("/carrental/payment-success.html?bookingId=" + vnp_TxnRef);
                } else {
                    // Thanh toán thất bại
                    System.out.println("Payment failed for booking: " + vnp_TxnRef);
                    return new RedirectView("/carrental/payment-failed.html?code=" + vnp_ResponseCode);
                }
            } else {
                // Chữ ký không hợp lệ
                System.out.println("Invalid signature");
                return new RedirectView("/carrental/payment-failed.html?code=97");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("/carrental/payment-failed.html?code=99");
        }
    }
}