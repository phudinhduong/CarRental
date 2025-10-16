package hsf302.he187383.phudd.carrental.repository;

import hsf302.he187383.phudd.carrental.model.VehicleImage;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleImageRepository extends JpaRepositoryImplementation<VehicleImage, UUID> {
    List<VehicleImage> findByVehicle_VehicleIdOrderByIsPrimaryDescUploadedAtDesc(UUID vehicleId);

    // Ảnh chính (nếu cần lấy 1 tấm)
    Optional<VehicleImage> findFirstByVehicle_VehicleIdOrderByIsPrimaryDescUploadedAtDesc(UUID vehicleId);
}

