package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.model.VehicleImage;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleService {

    // danh sách tất cả xe
    List<Vehicle> findAll();

    //hiển thị ảnh xe
    List<VehicleImage> findImagesOf(UUID vehicleId);

    //xem detail
    Optional<Vehicle> findById(UUID vehicleId);

    //lưu vô db
    Vehicle save(Vehicle v);

    //để mở xe edit
    Optional<Vehicle> findByIdAndOwner(UUID vehicleId, UUID ownerId);

    // xóa mềm
    void softDeleteByIdAndOwner(UUID vehicleId, UUID ownerId);

    // phân trang xe active và chưa xoá
    Page<Vehicle> findActivePaged(int page, int size);

    // tìm kiếm xe active và chưa xoá theo từ khoá
    Page<Vehicle> searchActivePaged(String q, int page, int size);

    //manage car, hiển thị xe ko bị xóa
    List<Vehicle> findByOwner_userIdAndDeletedAtIsNull(UUID ownerId);
}

