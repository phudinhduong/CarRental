package hsf302.he187383.phudd.carrental.service.impl;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.model.VehicleImage;
import hsf302.he187383.phudd.carrental.repository.VehicleImageRepository;
import hsf302.he187383.phudd.carrental.repository.VehicleRepository;
import hsf302.he187383.phudd.carrental.service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleImageRepository vehicleImageRepository;

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public List<VehicleImage> findImagesOf(UUID vehicleId) {
        return vehicleImageRepository
                .findByVehicle_VehicleIdOrderByIsPrimaryDescUploadedAtDesc(vehicleId);
    }

    @Override
    public Optional<Vehicle> findById(UUID id) {
        return vehicleRepository.findById(id);
    }

    @Override
    @Transactional
    public Vehicle save(Vehicle v) { return vehicleRepository.save(v); }

    @Override
    @Transactional
    public void deleteById(UUID id) { vehicleRepository.deleteById(id); }

    @Override
    public List<Vehicle> findByOwner(UUID ownerId) {
        return vehicleRepository.findByOwner_UserId(ownerId);
    }

    @Override
    public List<Vehicle> findActiveByOwner(UUID ownerId) {
        return vehicleRepository.findByOwner_UserIdAndDeletedAtIsNull(ownerId);
    }

    @Override
    public Optional<Vehicle> findByIdAndOwner(UUID vehicleId, UUID ownerId) {
        return vehicleRepository.findByVehicleIdAndOwner_UserId(vehicleId, ownerId);
    }

    @Override
    @Transactional
    public void softDeleteByIdAndOwner(UUID vehicleId, UUID ownerId) {
        Optional<Vehicle> opt = vehicleRepository.findByVehicleIdAndOwner_UserIdAndDeletedAtIsNull(vehicleId, ownerId);
        if (opt.isPresent()) {
            Vehicle v = opt.get();
            v.setDeletedAt(LocalDateTime.now());  // 👈 đánh dấu đã xoá
            vehicleRepository.save(v);
        }
    }
}
