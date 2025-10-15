package hsf302.he187383.phudd.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "about";
    }

    @GetMapping("/service")
    public String showService() {
        return "service";
    }

    @GetMapping("/car")
    public String showCar() {
        return "car";
    }

    @GetMapping("/detail")
    public String showDetail() {
        return "detail";
    }

    @GetMapping("/booking")
    public String showBooking() {
        return "booking";
    }

    @GetMapping("/team")
    public String showTeam() {
        return "team";
    }

    @GetMapping("/testimonial")
    public String showTestimonial() {
        return "testimonial";
    }

    @GetMapping("/contact")
    public String showContact() {
        return "contact";
    }


}
