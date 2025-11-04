package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.config.VNPayConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VNPayService {

    private final VNPayConfig vnPayConfig;

    // Tạo URL thanh toán
    public String createPaymentUrl(HttpServletRequest request, long amount, String orderInfo) {
        String vnp_TxnRef = getRandomNumber(8);
        String vnp_IpAddr = getClientIpAddr(request);
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = new SimpleDateFormat("yyyyMMddHHmmss").format(expireDate.getTime());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.VNP_VERSION);
        vnp_Params.put("vnp_Command", VNPayConfig.VNP_COMMAND);
        vnp_Params.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // nhân 100 theo quy định VNPAY
        vnp_Params.put("vnp_CurrCode", VNPayConfig.VNP_CURR_CODE);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", VNPayConfig.VNP_ORDER_TYPE);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        String queryUrl = VNPayConfig.hashAllFields(vnp_Params, vnPayConfig.getSecretKey());
        return vnPayConfig.getPayUrl() + "?" + queryUrl;
    }

    // Kiểm tra kết quả thanh toán trả về từ VNPAY
    public boolean validatePaymentResponse(Map<String, String> params) {
        String vnp_SecureHash = params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        String signValue = VNPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), buildHashData(params));
        return signValue.equals(vnp_SecureHash);
    }

    private String buildHashData(Map<String, String> fields) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);

        StringBuilder sb = new StringBuilder();
        try {
            for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
                String fieldName = itr.next();
                String fieldValue = fields.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    sb.append(fieldName).append('=')
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) sb.append('&');
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getRandomNumber(int len) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
