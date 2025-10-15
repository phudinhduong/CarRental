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
public class UserDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID documentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String type; // ID_CARD, DRIVER_LICENSE, VEHICLE_REG

    String fileUrl;

    String status; // pending, approved, rejected

    LocalDateTime uploadedAt;
}
