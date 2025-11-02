package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.model.Location;
import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.service.LocationService;
import hsf302.he187383.phudd.carrental.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


@Controller
@RequiredArgsConstructor
public class BookingController {

    private final VehicleService vehicleService;
    private final LocationService locationService;

    @GetMapping("/booking/{id}")
    public String carDetail(@PathVariable("id") UUID vehicleId, Model model) {
        Vehicle vehicle = vehicleService.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        var images = vehicleService.findImagesOf(vehicleId);
        Location location = locationService.findById(vehicle.getLocation().getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("location", location);
        model.addAttribute("images", images);

        return "booking";
    }
}

