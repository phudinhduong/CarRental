package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "features")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feature {
    @Id @GeneratedValue
    @Column(name = "feature_id")
    UUID featureId;

    @Column(nullable = false, unique = true)
    String name; // GPS, ChildSeat, ...

    String description;
}
