package hsf302.he187383.phudd.carrental.service;


import hsf302.he187383.phudd.carrental.model.User;

public interface AuthService {
    User login(String username, String password);  // trả về user nếu đúng
    void logout();
    User getCurrentUser();                         // lấy user từ session
    boolean isLoggedIn();
}
