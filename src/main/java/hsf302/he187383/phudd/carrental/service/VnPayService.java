package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.config.VnPayConfig;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnPayService {

    private final VnPayConfig vnPayConfig;

    public VnPayService(VnPayConfig vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }

    public String createPaymentUrl(String bookingId, long amount) throws Exception {
        // Tạo các tham số
        Map<String, String> vnp_Params = new TreeMap<>(); // TreeMap tự động sort

        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnPayConfig.getVnpTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", bookingId + "_" + System.currentTimeMillis());
        vnp_Params.put("vnp_OrderInfo", "Thanh toan booking " + bookingId);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getVnpReturnUrl());
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");

        // Thời gian
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));

        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));

        // Bước 1: Tạo hashData (KHÔNG encode)
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        boolean first = true;
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            if (fieldValue != null && fieldValue.length() > 0) {
                if (!first) {
                    hashData.append('&');
                    query.append('&');
                } else {
                    first = false;
                }

                // Hash data: KHÔNG encode
                hashData.append(fieldName).append('=').append(fieldValue);

                // Query string: CÓ encode
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
            }
        }

        // Bước 2: Tạo secure hash
        String vnp_SecureHash = vnPayConfig.hmacSHA512(
                vnPayConfig.getVnpSecretKey(),
                hashData.toString()
        );

        // Bước 3: Thêm hash vào query string
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        // Debug log
        System.out.println("=== VNPay Payment URL Debug ===");
        System.out.println("Hash Data: " + hashData.toString());
        System.out.println("Secure Hash: " + vnp_SecureHash);
        System.out.println("Full URL: " + vnPayConfig.getVnpPayUrl() + "?" + query.toString());
        System.out.println("===============================");

        return vnPayConfig.getVnpPayUrl() + "?" + query.toString();
    }
}