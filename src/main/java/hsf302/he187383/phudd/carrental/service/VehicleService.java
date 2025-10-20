package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.model.VehicleImage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleService {

    List<Vehicle> findAll();

    List<VehicleImage> findImagesOf(UUID vehicleId);

    Optional<Vehicle> findById(UUID vehicleId);

    Vehicle save(Vehicle v);

    void deleteById(java.util.UUID id);

    List<Vehicle> findByOwner(UUID ownerId);

    List<Vehicle> findActiveByOwner(UUID ownerId);

    Optional<Vehicle> findByIdAndOwner(UUID vehicleId, UUID ownerId);

    void softDeleteByIdAndOwner(UUID vehicleId, UUID ownerId);

}

