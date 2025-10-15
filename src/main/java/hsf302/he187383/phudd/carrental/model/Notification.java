package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {

    @Id
    @GeneratedValue
    UUID notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String type;   // ví dụ: BOOKING_UPDATE, PAYMENT_SUCCESS, SYSTEM_ALERT

    String title;

    @Column(columnDefinition = "TEXT")
    String body;

    Boolean isRead;

    @Column(columnDefinition = "TEXT")
    String meta;   // JSON string chứa dữ liệu phụ (vd: {"bookingId": "...", "action": "confirmed"})

    LocalDateTime createdAt;
}
