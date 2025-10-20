package hsf302.he187383.phudd.carrental.repository;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepositoryImplementation<Vehicle, UUID> {
    // List theo owner
    List<Vehicle> findByOwner_UserId(UUID ownerId);

    // Tải 1 xe thuộc về owner (để check quyền)
    Optional<Vehicle> findByVehicleIdAndOwner_UserId(UUID vehicleId, UUID ownerId);

    // Xoá theo id + owner (an toàn, 1 câu lệnh)
    void deleteByVehicleIdAndOwner_UserId(UUID vehicleId, UUID ownerId);

    List<Vehicle> findByOwner_UserIdAndDeletedAtIsNull(UUID ownerId);

    Optional<Vehicle> findByVehicleIdAndOwner_UserIdAndDeletedAtIsNull(UUID vehicleId, UUID ownerId);
}

