package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
//@Table(name = "vehicle_images")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleImage {
    @Id @GeneratedValue
    @Column(name = "image_id")
    UUID imageId;

    @ManyToOne @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @Column(length = 1024)
    String url;

    @Column(name = "is_primary")
    Boolean isPrimary;

    @Column(name = "uploaded_at")
    LocalDateTime uploadedAt;
}
