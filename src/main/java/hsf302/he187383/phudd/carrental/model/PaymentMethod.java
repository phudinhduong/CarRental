package hsf302.he187383.phudd.carrental.model;

import hsf302.he187383.phudd.carrental.model.enums.PaymentMethodType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_methods")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod {
    @Id @GeneratedValue
    @Column(name = "method_id")
    UUID methodId;

    @ManyToOne @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    PaymentMethodType type; // CARD, MOMO, VNPAY, BANK_TRANSFER

    @Column(length = 64)
    String provider; // Visa, Mastercard, VCB,...

    @Column(length = 255)
    String token; // gateway token (không lưu PAN)

    @Column(length = 4)
    String last4;

    Integer expiryMonth;
    Integer expiryYear;

    @Column(name = "is_default")
    Boolean isDefault;

    @Column(name = "created_at")
    LocalDateTime createdAt;
}
