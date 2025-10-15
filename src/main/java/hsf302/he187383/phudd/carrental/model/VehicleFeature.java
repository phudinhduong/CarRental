package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(VehicleFeatureId.class)
public class VehicleFeature {

    @Id
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @Id
    @ManyToOne
    @JoinColumn(name = "feature_id")
    Feature feature;
}
