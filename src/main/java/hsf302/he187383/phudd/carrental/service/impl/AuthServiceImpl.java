package hsf302.he187383.phudd.carrental.service.impl;

import hsf302.he187383.phudd.carrental.exception.AuthException;
import hsf302.he187383.phudd.carrental.model.User;
import hsf302.he187383.phudd.carrental.repository.UserRepository;
import hsf302.he187383.phudd.carrental.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final String SESSION_USER_ID   = "uid";
    public static final String SESSION_USER_ROLE = "role";
    public static final String SESSION_USER_MAIL = "uemail";

    private final UserRepository userRepository;
    private final HttpSession session;
    private final PasswordEncoder passwordEncoder;

    // BCrypt của chuỗi "dummy" – dùng để matches khi user null, chống timing attack
    private static final String DUMMY_BCRYPT = "$2a$10$7eqJtq98hPqEX7fNZaFWoO.P8G6G..iY5H1ZArYdS9x6kBev5G/Ka";

    @Override
    @Transactional
    public User login(String username, String password) {
        String email = username == null ? "" : username.trim().toLowerCase();

        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);

        String hash = (user != null && user.getPasswordHash() != null)
                ? user.getPasswordHash()
                : DUMMY_BCRYPT; // để matches cho công bằng thời gian

        boolean ok = passwordEncoder.matches(password == null ? "" : password, hash);
        if (!ok || user == null) {
            return null; // sai tài khoản hoặc mật khẩu
        }

        // Kiểm tra trạng thái tài khoản
        if (user.getStatus() != null) {
            String st = user.getStatus().toLowerCase();
            if ("banned".equals(st)) {
                throw new AuthException("Tài khoản đã bị khóa.");
            }
            if ("suspended".equals(st)) {
                throw new AuthException("Tài khoản đang tạm khóa.");
            }
        }
        if (Boolean.FALSE.equals(user.getIsVerified())) {
            throw new AuthException("Tài khoản chưa xác minh email.");
        }

        // (Tuỳ chọn) cập nhật thời điểm đăng nhập
        // user.setUpdatedAt(LocalDateTime.now());
        // userRepository.save(user);

        // Lưu thông tin nhẹ vào session (không nên nhét full entity)
        session.setAttribute(SESSION_USER_ID,   user.getUserId());
        session.setAttribute(SESSION_USER_MAIL, user.getEmail());
        session.setAttribute(SESSION_USER_ROLE, user.getRole() != null ? user.getRole().getName() : null);

        return user;
    }

    @Override
    public void logout() {
        session.invalidate();
    }

    @Override
    public User getCurrentUser() {
        UUID uid = (UUID) session.getAttribute(SESSION_USER_ID);
        if (uid == null) return null;
        return userRepository.findByUserId(uid).orElse(null);
    }

    @Override
    public boolean isLoggedIn() {
        return session.getAttribute(SESSION_USER_ID) != null;
    }
}
