package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.model.Location;
import hsf302.he187383.phudd.carrental.model.User;
import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.model.enums.VehicleStatus;
import hsf302.he187383.phudd.carrental.service.LocationService;
import hsf302.he187383.phudd.carrental.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carowner")
public class CarOwnerController {
    private final VehicleService vehicleService;
    private final LocationService locationService;

    // ========== LIST ==========
    @GetMapping("/manageCar")
    public String list(@ModelAttribute("currentUser") User currentUser, Model model) {
        if (!isOwner(currentUser)) return "redirect:/login?error=login_required";
        model.addAttribute("vehicles", vehicleService.findByOwner_userIdAndDeletedAtIsNull(currentUser.getUserId()));
        return "manageCar";
    }

    // ========== CREATE FORM ==========
    @GetMapping("/vehicles/new")
    public String createForm(@ModelAttribute("currentUser") User currentUser, Model model) {
        if (!isOwner(currentUser)) return "redirect:/login?error=login_required";
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("actionUrl", "/carowner/vehicles");
        model.addAttribute("locations", locationService.findAll());
        return "vehicle-form";
    }

    // ========== CREATE SUBMIT ==========
    @PostMapping("/vehicles")
    public String create(@ModelAttribute("currentUser") User currentUser,
                         @Valid @ModelAttribute("vehicle") Vehicle form,
                         BindingResult br,
                         @RequestParam(value = "locationId", required = false) UUID locationId,
                         Model model) {
        if (!isOwner(currentUser)) return "redirect:/login?error=login_required";

        if (br.hasErrors()) {
            model.addAttribute("locations", locationService.findAll());
            return "carowner/vehicle-form";
        }

        // set owner hiện tại
        form.setOwner(currentUser);
        if (locationId != null) {
            locationService.findById(locationId).ifPresent(form::setLocation);
        }
        form.setCreatedAt(java.time.LocalDateTime.now());
        form.setIsActive(true);

        vehicleService.save(form);
        return "redirect:/carowner/manageCar";
    }

    // ========== EDIT FORM ==========
    @GetMapping("/vehicles/{id}/edit")
    public String editForm(@ModelAttribute("currentUser") User currentUser,
                           @PathVariable UUID id,
                           Model model) {
        if (!isOwner(currentUser)) return "redirect:/login?error=login_required";

        Vehicle v = vehicleService.findByIdAndOwner(id, currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found or not yours"));
        model.addAttribute("vehicle", v);
        model.addAttribute("actionUrl", "/carowner/vehicles/" + v.getVehicleId());
        model.addAttribute("locations", locationService.findAll());
        return "vehicle-form";
    }

    // ========== EDIT SUBMIT ==========
    @PostMapping("/vehicles/{id}")
    public String update(@ModelAttribute("currentUser") User currentUser,
                         @PathVariable UUID id,
                         @Valid @ModelAttribute("vehicle") Vehicle form,
                         BindingResult br,
                         @RequestParam(value = "locationId", required = false) UUID locationId,
                         Model model) {
        if (!isOwner(currentUser)) return "redirect:/login?error=login_required";

        Vehicle v = vehicleService.findByIdAndOwner(id, currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found or not yours"));

        if (br.hasErrors()) {
            model.addAttribute("locations", locationService.findAll());
            return "vehicle-form";
        }

        // copy field cho phép sửa
        v.setTitle(form.getTitle());
        v.setBrand(form.getBrand());
        v.setModel(form.getModel());
        v.setType(form.getType());
        v.setLicensePlate(form.getLicensePlate());
        v.setYear(form.getYear());
        v.setColor(form.getColor());
        v.setSeats(form.getSeats());
        v.setDailyPrice(form.getDailyPrice());
        v.setDepositAmount(form.getDepositAmount());
        v.setStatus(form.getStatus());
        v.setIsActive(form.getIsActive());
        v.setUpdatedAt(java.time.LocalDateTime.now());
        if (locationId != null) {
            locationService.findById(locationId).ifPresent(v::setLocation);
        }

        vehicleService.save(v);
        return "redirect:/carowner/manageCar";
    }

    // ========== DELETE ==========
    @PostMapping("/vehicles/{id}/delete")
    public String softDelete(@ModelAttribute("currentUser") User currentUser,
                             @PathVariable UUID id) {
        if (currentUser == null) {
            return "redirect:/login?error=login_required";
        }
        vehicleService.softDeleteByIdAndOwner(id, currentUser.getUserId());
        return "redirect:/carowner/manageCar";
    }



    // helper
    private boolean isOwner(User u) {
        return u != null && u.getRole() != null
                && "Owner".equalsIgnoreCase(u.getRole().getName());
    }
}
