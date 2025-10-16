package hsf302.he187383.phudd.carrental.service.impl;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.model.VehicleImage;
import hsf302.he187383.phudd.carrental.repository.VehicleImageRepository;
import hsf302.he187383.phudd.carrental.repository.VehicleRepository;
import hsf302.he187383.phudd.carrental.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
