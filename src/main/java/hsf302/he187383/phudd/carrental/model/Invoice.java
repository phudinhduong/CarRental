package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice {
    @Id @GeneratedValue
    @Column(name = "invoice_id")
    UUID invoiceId;

    @ManyToOne @JoinColumn(name = "booking_id")
    Booking booking;

    @ManyToOne @JoinColumn(name = "payment_id")
    Payment payment;

    @Column(name = "file_url", length = 1024)
    String fileUrl;

    @Column(name = "issued_at")
    LocalDateTime issuedAt;
}
