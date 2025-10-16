package hsf302.he187383.phudd.carrental.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@Entity
//@Table(name = "vehicle_features")
@Data @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleFeature {

    @EmbeddedId
    VehicleFeatureId id;

    @ManyToOne @MapsId("vehicleId") @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @ManyToOne @MapsId("featureId") @JoinColumn(name = "feature_id")
    Feature feature;

    @Embeddable
    @Data @NoArgsConstructor @AllArgsConstructor
    public static class VehicleFeatureId implements Serializable {
        @Column(name = "vehicle_id") UUID vehicleId;
        @Column(name = "feature_id") UUID featureId;
    }
}
