package hsf302.he187383.phudd.carrental.model;

import lombok.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Khóa tổng hợp cho bảng VehicleFeature (M:N)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleFeatureId implements Serializable {

    UUID vehicleId;
    UUID featureId;
}
