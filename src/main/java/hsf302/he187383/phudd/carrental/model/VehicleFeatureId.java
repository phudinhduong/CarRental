package hsf302.he187383.phudd.carrental.model;

import lombok.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleFeatureId implements Serializable {
    UUID vehicle;
    UUID feature;
}
