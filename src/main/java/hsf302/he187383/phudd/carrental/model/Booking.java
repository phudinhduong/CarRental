package hsf302.he187383.phudd.carrental.model;

import hsf302.he187383.phudd.carrental.model.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
    @Id @GeneratedValue
    @Column(name = "booking_id")
    UUID bookingId;

    @ManyToOne @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @ManyToOne @JoinColumn(name = "renter_id")
    User renter;

    // denormalized for queries
    @ManyToOne @JoinColumn(name = "owner_id")
    User owner;

    @Column(name = "start_datetime")
    LocalDateTime startDatetime;

    @Column(name = "end_datetime")
    LocalDateTime endDatetime;

    @ManyToOne @JoinColumn(name = "pickup_location_id")
    Location pickupLocation;

    @ManyToOne @JoinColumn(name = "dropoff_location_id")
    Location dropoffLocation;

    // computed -> không map DB, tính bằng code khi cần
    @Transient
    public Integer getDays() {
        if (startDatetime == null || endDatetime == null) return null;
        long hours = Duration.between(startDatetime, endDatetime).toHours();
        long days = (hours + 23) / 24; // làm tròn lên nếu lẻ giờ
        return (int) days;
    }

    @Column(name = "base_amount", precision = 12, scale = 2)
    BigDecimal baseAmount;

    @Column(name = "addons_amount", precision = 12, scale = 2)
    BigDecimal addonsAmount;

    @Column(name = "deposit_amount", precision = 12, scale = 2)
    BigDecimal depositAmount;

    @Column(name = "discount_amount", precision = 12, scale = 2)
    BigDecimal discountAmount;

    @Column(name = "tax_amount", precision = 12, scale = 2)
    BigDecimal taxAmount;

    @Column(name = "total_amount", precision = 12, scale = 2)
    BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    BookingStatus status;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    String cancellationReason;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
