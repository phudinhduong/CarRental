package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.model.Location;
import hsf302.he187383.phudd.carrental.repository.LocationRepository;
import hsf302.he187383.phudd.carrental.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // hoặc @Service("locationServiceImpl") nếu muốn giữ nguyên bean name cũ
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final VehicleRepository vehicleRepository; // Nếu chưa dùng, có thể xoá để sạch warning

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Optional<Location> findById(UUID id) {
        return locationRepository.findById(id);
    }
}
