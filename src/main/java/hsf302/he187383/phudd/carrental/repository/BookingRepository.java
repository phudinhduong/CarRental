package hsf302.he187383.phudd.carrental.repository;

import hsf302.he187383.phudd.carrental.model.Booking;
import hsf302.he187383.phudd.carrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByRenter(User renter);
    List<Booking> findByOwner(User owner);
}

