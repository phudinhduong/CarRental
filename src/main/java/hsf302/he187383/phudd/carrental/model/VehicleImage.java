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
public class VehicleImage {

    @Id
    @GeneratedValue
    UUID imageId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    String url;
    Boolean isPrimary;
    LocalDateTime uploadedAt;
}