package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.model.VehicleImage;
import hsf302.he187383.phudd.carrental.repository.VehicleImageRepository;
import hsf302.he187383.phudd.carrental.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // hoặc @Service("vehicleServiceImpl") nếu cần giữ nguyên bean name cũ
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleImageRepository vehicleImageRepository;

    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<VehicleImage> findImagesOf(UUID vehicleId) {
        return vehicleImageRepository
                .findByVehicle_VehicleIdOrderByIsPrimaryDescUploadedAtDesc(vehicleId);
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> findById(UUID id) {
        return vehicleRepository.findById(id);
    }

    @Transactional
    public Vehicle save(Vehicle v) {
        return vehicleRepository.save(v);
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> findByIdAndOwner(UUID vehicleId, UUID ownerId) {
        return vehicleRepository.findByVehicleIdAndOwner_UserId(vehicleId, ownerId);
    }

    @Transactional
    public void softDeleteByIdAndOwner(UUID vehicleId, UUID ownerId) {
        Optional<Vehicle> opt = vehicleRepository
                .findByVehicleIdAndOwner_UserIdAndDeletedAtIsNull(vehicleId, ownerId);
        if (opt.isPresent()) {
            Vehicle v = opt.get();
            v.setDeletedAt(LocalDateTime.now()); // đánh dấu xoá mềm
            vehicleRepository.save(v);
        }
    }

    @Transactional(readOnly = true)
    public Page<Vehicle> findActivePaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return vehicleRepository.findByIsActiveTrueAndDeletedAtIsNull(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Vehicle> searchActivePaged(String q, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return vehicleRepository.searchActive(q.trim(), pageable);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findByOwner_userIdAndDeletedAtIsNull(UUID ownerId) {
        return vehicleRepository.findByOwner_userIdAndDeletedAtIsNull(ownerId);
    }
}
