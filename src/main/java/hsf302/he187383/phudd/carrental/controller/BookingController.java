package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.model.Vehicle;
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

    @GetMapping("/booking")
    public String Default(Model model) {
        var vehicles = vehicleService.findAll();
        if (vehicles.isEmpty()) {
            model.addAttribute("errorMsg", "No vehicles available.");
            return "booking"; // or "error" page
        }

        Vehicle vehicle = vehicles.get(0);
        model.addAttribute("vehicle", vehicle);

        var images = vehicleService.findImagesOf(vehicle.getVehicleId());
        model.addAttribute("images", images); // <-- use "images" to match the template

        return "booking";
    }

    @GetMapping("/booking/{id}")
    public String carDetail(@PathVariable("id") UUID vehicleId, Model model) {
        Vehicle vehicle = vehicleService.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        model.addAttribute("vehicle", vehicle);

        var images = vehicleService.findImagesOf(vehicleId);
        model.addAttribute("images", images);

        return "booking";
    }
}

