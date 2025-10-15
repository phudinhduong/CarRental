package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vehicle {

    @Id
    @GeneratedValue
    UUID vehicleId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;

    String title;
    String brand;
    String model;
    String type; // car, motorbike, truck
    @Column(unique = true)
    String licensePlate;

    Integer year;
    String color;
    Integer seats;

    BigDecimal dailyPrice;
    BigDecimal depositAmount;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    String status; // AVAILABLE, BOOKED, etc.
    Boolean isActive;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;
}
