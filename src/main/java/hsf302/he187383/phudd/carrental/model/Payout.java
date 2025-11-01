package hsf302.he187383.phudd.carrental.model;

import hsf302.he187383.phudd.carrental.model.enums.PayoutMethod;
import hsf302.he187383.phudd.carrental.model.enums.PayoutStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payouts")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payout {
    @Id @GeneratedValue
    @Column(name = "payout_id")
    UUID payoutId;

    @ManyToOne @JoinColumn(name = "owner_id")
    User owner;

    @Column(precision = 12, scale = 2)
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    PayoutMethod method; // BANK_TRANSFER, MOMO

    @Column(name = "account_info", columnDefinition = "NVARCHAR(MAX)")
    String accountInfoJsonMasked; // JSON masked

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    PayoutStatus status;

    @Column(name = "requested_at")
    LocalDateTime requestedAt;

    @Column(name = "paid_at")
    LocalDateTime paidAt;
}
