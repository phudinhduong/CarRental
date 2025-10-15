package hsf302.he187383.phudd.carrental.model;

import hsf302.he187383.phudd.carrental.model.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicles")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vehicle {
    @Id @GeneratedValue
    @Column(name = "vehicle_id")
    UUID vehicleId;

    @ManyToOne @JoinColumn(name = "owner_id")
    User owner;

    String title;
    String brand;
    String model;

    @Column(length = 32)
    String type; // car, motorbike, truck

    @Column(name = "license_plate", unique = true, length = 64)
    String licensePlate;

    Integer year;
    String color;
    Integer seats;

    @Column(name = "daily_price", precision = 12, scale = 2)
    BigDecimal dailyPrice;

    @Column(name = "deposit_amount", precision = 12, scale = 2)
    BigDecimal depositAmount;

    @ManyToOne @JoinColumn(name = "location_id")
    Location location;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    VehicleStatus status;

    @Column(name = "is_active")
    Boolean isActive;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    LocalDateTime deletedAt;
}
