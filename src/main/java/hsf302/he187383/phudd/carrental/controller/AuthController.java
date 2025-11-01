package hsf302.he187383.phudd.carrental.controller;

import hsf302.he187383.phudd.carrental.exception.AuthException;
import hsf302.he187383.phudd.carrental.model.User;
import hsf302.he187383.phudd.carrental.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null)  model.addAttribute("errorMsg", "Sai tài khoản hoặc mật khẩu");
        if (logout != null) model.addAttribute("logoutMsg", "Bạn đã đăng xuất.");
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request,
                          RedirectAttributes ra) {
        try {
            User user = authService.login(username, password);
            if (user == null) {
                ra.addFlashAttribute("errorMsg", "Sai tài khoản hoặc mật khẩu");
                return "redirect:/login?error";
            }
            // Chống session fixation
            request.changeSessionId();
            return "redirect:/";
        } catch (AuthException ex) {
            ra.addFlashAttribute("errorMsg", ex.getMessage());
            return "redirect:/login?error";
        }
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes ra) {
        authService.logout();
        ra.addFlashAttribute("logoutMsg", "Bạn đã đăng xuất.");
        return "redirect:/login?logout";
    }
}
