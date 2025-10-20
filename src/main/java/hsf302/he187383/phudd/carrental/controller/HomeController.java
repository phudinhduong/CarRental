package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.model.Vehicle;
import hsf302.he187383.phudd.carrental.model.VehicleImage;
import hsf302.he187383.phudd.carrental.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final VehicleService vehicleService;

    @GetMapping({"/", "/index"})
    public String homepage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(required = false) String q,
                           Model model) {

        // Lấy Page xe (6 chiếc/trang) – chỉ xe chưa bị xóa mềm hoặc tìm kiếm nếu có từ khoá
        Page<Vehicle> pageData = (q != null && !q.isBlank())
                ? vehicleService.searchActivePaged(q, page, 6)
                : vehicleService.findActivePaged(page, 6);

        // Gắn ảnh đầu tiên cho mỗi xe
        List<Map<String, Object>> vehiclesWithImages = pageData.getContent().stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("vehicle", v);

            List<VehicleImage> images = vehicleService.findImagesOf(v.getVehicleId());
            String imageUrl = (images != null && !images.isEmpty())
                    ? images.get(0).getUrl()
                    : "/img/car-rent-1.png"; // fallback

            // có thể chuẩn hoá để luôn có "/" đầu:
            if (imageUrl != null && !imageUrl.startsWith("/")) {
                imageUrl = "/" + imageUrl;
            }
            map.put("imageUrl", imageUrl);
            return map;
        }).toList();

        model.addAttribute("vehiclesWithImages", vehiclesWithImages);
        model.addAttribute("page", pageData); // đưa cả đối tượng Page ra view để render phân trang
        model.addAttribute("q", q);

        return "index";
    }



    @GetMapping("/about")
    public String about() { return "about"; }

    @GetMapping("/service")
    public String service() { return "service"; }

    @GetMapping("/car")
    public String carListing(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(required = false) String q,
                             Model model) {

        // Lấy Page xe (6 chiếc/trang) – chỉ xe chưa bị xóa mềm hoặc tìm kiếm nếu có từ khoá
        Page<Vehicle> pageData = (q != null && !q.isBlank())
                ? vehicleService.searchActivePaged(q, page, 6)
                : vehicleService.findActivePaged(page, 6);

        // Gắn ảnh đầu tiên cho mỗi xe
        List<Map<String, Object>> vehiclesWithImages = pageData.getContent().stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("vehicle", v);

            List<VehicleImage> images = vehicleService.findImagesOf(v.getVehicleId());
            String imageUrl = (images != null && !images.isEmpty())
                    ? images.get(0).getUrl()
                    : "/img/car-rent-1.png"; // fallback

            // có thể chuẩn hoá để luôn có "/" đầu:
            if (imageUrl != null && !imageUrl.startsWith("/")) {
                imageUrl = "/" + imageUrl;
            }
            map.put("imageUrl", imageUrl);
            return map;
        }).toList();

        model.addAttribute("vehiclesWithImages", vehiclesWithImages);
        model.addAttribute("page", pageData); // đưa cả đối tượng Page ra view để render phân trang
        model.addAttribute("q", q);

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
