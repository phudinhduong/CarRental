package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.model.Location;

import java.util.*;

public interface LocationService {
    List<Location> findAll();
    Optional<Location> findById(UUID id);
}
