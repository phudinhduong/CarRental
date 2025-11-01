package hsf302.he187383.phudd.carrental.repository;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepositoryImplementation<Vehicle, UUID> {

    // lấy xe theo id của xe + chủ xe
    Optional<Vehicle> findByVehicleIdAndOwner_UserId(UUID vehicleId, UUID ownerId);

    // lấy xe để xóa
    Optional<Vehicle> findByVehicleIdAndOwner_UserIdAndDeletedAtIsNull(UUID vehicleId, UUID ownerId);

    // homepage, list car: phân trang: chỉ xe đang active và chưa bị xoá
    Page<Vehicle> findByIsActiveTrueAndDeletedAtIsNull(Pageable pageable);

    // Tìm kiếm theo từ khoá trong các trường cơ bản (chỉ xe active, chưa xoá)
    @Query("SELECT v FROM Vehicle v\n           " +
            "WHERE v.isActive = true " +
            "AND v.deletedAt IS NULL\n             " +
            "AND (LOWER(v.title) " +
            "LIKE LOWER(CONCAT('%', :q, '%'))\n                  " +
            "OR LOWER(v.brand) LIKE LOWER(CONCAT('%', :q, '%'))\n                  " +
            "OR LOWER(v.model) LIKE LOWER(CONCAT('%', :q, '%'))\n                  " +
            "OR LOWER(v.licensePlate) LIKE LOWER(CONCAT('%', :q, '%')))")
    Page<Vehicle> searchActive(@Param("q") String q, Pageable pageable);

    //manage car: hiển thị xe ko bị xóa
    List<Vehicle> findByOwner_userIdAndDeletedAtIsNull(UUID ownerId);
}

