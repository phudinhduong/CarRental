package hsf302.he187383.phudd.carrental.model;

import hsf302.he187383.phudd.carrental.model.enums.PaymentStatus;
import hsf302.he187383.phudd.carrental.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id @GeneratedValue
    @Column(name = "payment_id")
    UUID paymentId;

    @ManyToOne @JoinColumn(name = "booking_id")
    Booking booking;

    @ManyToOne @JoinColumn(name = "payer_id")
    User payer;

    @ManyToOne @JoinColumn(name = "method_id")
    PaymentMethod method; // có thể null nếu 3rd-party

    @Column(precision = 12, scale = 2)
    BigDecimal amount;

    @Column(length = 16)
    String currency;

    @Column(name = "paid_at")
    LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    PaymentStatus status;

    @Column(name = "gateway_transaction_id", length = 255)
    String gatewayTransactionId;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    PaymentType type; // FULL, DEPOSIT, REFUND, PENALTY
}
