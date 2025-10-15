package hsf302.he187383.phudd.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String homepage() {
        return "index";
    }

    @GetMapping("/about")
    public String about() { return "about"; }

    @GetMapping("/service")
    public String service() { return "service"; }

    @GetMapping("/car")
    public String car() { return "car"; }

    @GetMapping("/detail")
    public String detail() { return "detail"; }

    @GetMapping("/booking")
    public String booking() { return "booking"; }

    @GetMapping("/team")
    public String team() { return "team"; }

    @GetMapping("/testimonial")
    public String testimonial() { return "testimonial"; }

    @GetMapping("/contact")
    public String contact() { return "contact"; }

//    @GetMapping("/login")
//    public String login() { return "login"; }
}
