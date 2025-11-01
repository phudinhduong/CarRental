package hsf302.he187383.phudd.carrental.service.impl;

import hsf302.he187383.phudd.carrental.model.Location;
import hsf302.he187383.phudd.carrental.repository.LocationRepository;
import hsf302.he187383.phudd.carrental.repository.VehicleRepository;
import hsf302.he187383.phudd.carrental.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final VehicleRepository vehicleRepository;


    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(UUID id) {
        return locationRepository.findById(id);
    }
}
