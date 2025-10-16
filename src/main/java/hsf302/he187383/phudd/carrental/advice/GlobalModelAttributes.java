package hsf302.he187383.phudd.carrental.advice;

import hsf302.he187383.phudd.carrental.model.User;
import hsf302.he187383.phudd.carrental.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final AuthService authService;

    @ModelAttribute("currentUser")
    public User currentUser() {
        return authService.getCurrentUser(); // null nếu chưa login
    }
}
