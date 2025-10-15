package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "booking_addons")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingAddon {
    @Id @GeneratedValue
    @Column(name = "booking_addon_id")
    UUID bookingAddonId;

    @ManyToOne @JoinColumn(name = "booking_id")
    Booking booking;

    @ManyToOne @JoinColumn(name = "addon_id")
    Addon addon;

    Integer qty;

    @Column(precision = 12, scale = 2)
    BigDecimal price; // unit price at booking time (snapshot)
}
