package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.config.VnPayConfig;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
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
        Map<String, String> vnp_Params = new TreeMap<>();

        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnPayConfig.getVnpTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", bookingId + "_" + System.currentTimeMillis());
        vnp_Params.put("vnp_OrderInfo", "Thanhtoanbooking " + bookingId);
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

        // Tạo hashData (không encode) và query string (có encode)
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        boolean first = true;
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value != null && value.length() > 0) {
                if (!first) {
                    hashData.append('&');
                    query.append('&');
                } else {
                    first = false;
                }

                // hashData: sử dụng giá trị GỐC (không encode)
                hashData.append(key).append('=').append(value);

                // query: URL encode cả key và value
                query.append(URLEncoder.encode(key, StandardCharsets.UTF_8))
                        .append('=')
                        .append(URLEncoder.encode(value, StandardCharsets.UTF_8));
            }
        }

        // Tính chữ ký từ hashData (giá trị gốc)
        String vnp_SecureHash = vnPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());

        // Thêm chữ ký vào query string
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        System.out.println("=== VNPay Debug ===");
        System.out.println("HashData: " + hashData);
        System.out.println("SecureHash: " + vnp_SecureHash);
        System.out.println("Query: " + query);
        System.out.println("Full URL: " + vnPayConfig.getVnpPayUrl() + "?" + query);
        System.out.println("==================");

        return vnPayConfig.getVnpPayUrl() + "?" + query.toString();
    }
}