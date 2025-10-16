package hsf302.he187383.phudd.carrental.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Configuration
public class VnPayConfig {

    @Value("${vnpay.tmn-code}")
    private String vnpTmnCode;

    @Value("${vnpay.secret-key}")
    private String vnpSecretKey;

    @Value("${vnpay.pay-url}")
    private String vnpPayUrl;

    @Value("${vnpay.return-url}")
    private String vnpReturnUrl;

    @Value("${vnpay.ipn-url}")
    private String vnpIpnUrl;

    @Value("${vnpay.api-url}")
    private String vnpApiUrl;

    public String getVnpTmnCode() {
        return vnpTmnCode;
    }

    public String getVnpSecretKey() {
        return vnpSecretKey;
    }

    public String getVnpPayUrl() {
        return vnpPayUrl;
    }

    public String getVnpReturnUrl() {
        return vnpReturnUrl;
    }

    public String getVnpIpnUrl() {
        return vnpIpnUrl;
    }

    public String getVnpApiUrl() {
        return vnpApiUrl;
    }

    /**
     * Hash dữ liệu theo chuẩn HMACSHA512 của VNPay
     */
    public String hashAllFields(Map<String, String> fields) {
        StringBuilder hashData = new StringBuilder();

        fields.forEach((key, value) -> {
            if (hashData.length() > 0) {
                hashData.append('&');
            }
            hashData.append(key).append('=').append(value);
        });

        return hmacSHA512(vnpSecretKey, hashData.toString());
    }

    /**
     * Tính toán HMAC SHA512
     */
    public String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC SHA512", e);
        }
    }
}