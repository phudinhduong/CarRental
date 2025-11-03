package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.model.Booking;
import hsf302.he187383.phudd.carrental.model.Location;
import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.service.BookingService;
import hsf302.he187383.phudd.carrental.service.LocationService;
import hsf302.he187383.phudd.carrental.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final VehicleService vehicleService;
    private final LocationService locationService;
    private final BookingService bookingService;

    /**
     * Hiển thị trang chi tiết xe và form đặt xe
     */
    @GetMapping("/{id}")
    public String carDetail(@PathVariable("id") UUID vehicleId, Model model) {
        Vehicle vehicle = vehicleService.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        var images = vehicleService.findImagesOf(vehicleId);
        var locations = locationService.findAll();

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("images", images);
        model.addAttribute("locations", locations);
        return "booking";
    }

    /**
     * Xử lý form xác nhận đặt xe
     */
    @PostMapping("/confirm")
    public String confirmBooking(
            @RequestParam UUID vehicleId,
            @RequestParam UUID pickupLocationId,
            @RequestParam UUID dropoffLocationId,
            @RequestParam LocalDateTime startDatetime,
            @RequestParam LocalDateTime endDatetime,
            Model model) {

        Booking booking = bookingService.createBooking(
                vehicleId, pickupLocationId, dropoffLocationId,
                startDatetime, endDatetime
        );

        model.addAttribute("booking", booking);
        model.addAttribute("message", "Your booking has been created successfully!");
        return "booking-confirm";
    }
}
