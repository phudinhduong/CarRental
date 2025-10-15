package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    UUID notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false, length = 50)
    String type;   // Ví dụ: BOOKING_UPDATE, PAYMENT_SUCCESS, SYSTEM_ALERT

    @Column(nullable = false, length = 255)
    String title;

    @Column(columnDefinition = "TEXT")
    String body;

    @Column(name = "is_read")
    Boolean isRead;

    @Column(columnDefinition = "TEXT")
    String meta;   // JSON chứa dữ liệu phụ (vd: {"bookingId": "...", "action": "confirmed"})

    @Column(name = "created_at")
    LocalDateTime createdAt;
}
