package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicle_availability")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Availability {
    @Id @GeneratedValue
    @Column(name = "availability_id")
    UUID availabilityId;

    @ManyToOne @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @Column(name = "start_datetime")
    LocalDateTime startDatetime;

    @Column(name = "end_datetime")
    LocalDateTime endDatetime;

    @Column(name = "is_available")
    Boolean isAvailable;

    @Column(length = 500)
    String notes;
}
