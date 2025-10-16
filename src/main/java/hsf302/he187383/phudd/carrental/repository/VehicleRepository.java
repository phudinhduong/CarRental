package hsf302.he187383.phudd.carrental.repository;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.UUID;

public interface VehicleRepository extends JpaRepositoryImplementation<Vehicle, UUID> {
}
