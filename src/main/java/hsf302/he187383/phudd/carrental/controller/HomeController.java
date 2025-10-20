package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.model.VehicleImage;
import hsf302.he187383.phudd.carrental.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final VehicleService vehicleService;

    @GetMapping({"/", "/index"})
    public String homepage(Model model) {

        // Lấy 6 xe đầu tiên
        List<Vehicle> vehicles = vehicleService.findAll()
                .stream()
                .limit(6)
                .toList();

        // Gắn ảnh đầu tiên cho mỗi xe
        List<Map<String, Object>> vehiclesWithImages = vehicles.stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("vehicle", v);

            List<VehicleImage> images = vehicleService.findImagesOf(v.getVehicleId());
            String imageUrl = (images != null && !images.isEmpty())
                    ? images.get(0).getUrl()
                    : "/img/car-rent-1.png";

            map.put("imageUrl", imageUrl);
            return map;
        }).toList();

        model.addAttribute("vehiclesWithImages", vehiclesWithImages);

        return "index";
    }


    @GetMapping("/about")
    public String about() { return "about"; }

    @GetMapping("/service")
    public String service() { return "service"; }

    @GetMapping("/car")
    public String car(Model model) {
        // Lấy 6 xe đầu tiên
        List<Vehicle> vehicles = vehicleService.findAll()
                .stream()
                .limit(6)
                .toList();

        // Gắn ảnh đầu tiên cho mỗi xe
        List<Map<String, Object>> vehiclesWithImages = vehicles.stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("vehicle", v);

            List<VehicleImage> images = vehicleService.findImagesOf(v.getVehicleId());
            String imageUrl = (images != null && !images.isEmpty())
                    ? images.get(0).getUrl()
                    : "/img/car-rent-1.png";

            map.put("imageUrl", imageUrl);
            return map;
        }).toList();

        model.addAttribute("vehiclesWithImages", vehiclesWithImages);

        return "car";
    }

    @GetMapping("/detail")
    public String detail() { return "detail"; }

//    @GetMapping("/booking")
//    public String booking() { return "booking"; }

    @GetMapping("/team")
    public String team() { return "team"; }

    @GetMapping("/testimonial")
    public String testimonial() { return "testimonial"; }

    @GetMapping("/contact")
    public String contact() { return "contact"; }

//    @GetMapping("/login")
//    public String login() { return "login"; }

//    @GetMapping("/manageCar")
//    public String showManageCar() { return "manageCar"; }

}
