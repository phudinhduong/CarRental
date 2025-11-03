package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.model.*;
import hsf302.he187383.phudd.carrental.model.enums.BookingStatus;
import hsf302.he187383.phudd.carrental.repository.BookingRepository;
import hsf302.he187383.phudd.carrental.repository.LocationRepository;
import hsf302.he187383.phudd.carrental.repository.UserRepository;
import hsf302.he187383.phudd.carrental.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public Booking createBooking(UUID vehicleId,
                                 UUID pickupLocationId, UUID dropoffLocationId,
                                 LocalDateTime start, LocalDateTime end) {

        Vehicle vehicle = vehicleRepository.findByVehicleId(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        User renter = userRepository.findByEmail("customer@gmail.com")
                .orElseThrow(() -> new RuntimeException("Renter not found"));

        Location pickup = locationRepository.findById(pickupLocationId)
                .orElseThrow(() -> new RuntimeException("Pickup location not found"));

        Location dropoff = locationRepository.findById(dropoffLocationId)
                .orElseThrow(() -> new RuntimeException("Dropoff location not found"));

        // Tính số ngày thuê
        long hours = Duration.between(start, end).toHours();
        long days = (hours + 23) / 24;
        if (days <= 0) {
            throw new IllegalArgumentException("Invalid booking period");
        }

        BigDecimal baseAmount = vehicle.getDailyPrice()
                .multiply(BigDecimal.valueOf(days));

        BigDecimal totalAmount = baseAmount;

        Booking booking = Booking.builder()
                .vehicle(vehicle)
                .renter(renter)
                .owner(vehicle.getOwner())
                .pickupLocation(pickup)
                .dropoffLocation(dropoff)
                .startDatetime(start)
                .endDatetime(end)
                .baseAmount(baseAmount)
                .addonsAmount(BigDecimal.ZERO)
                .depositAmount(BigDecimal.ZERO)
                .discountAmount(BigDecimal.ZERO)
                .taxAmount(BigDecimal.ZERO)
                .totalAmount(totalAmount)
                .status(BookingStatus.REQUESTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return bookingRepository.save(booking);
    }
}
