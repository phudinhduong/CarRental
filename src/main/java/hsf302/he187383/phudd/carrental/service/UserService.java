package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.model.User;
import hsf302.he187383.phudd.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service // hoặc @Service("userServiceImpl") nếu cần giữ nguyên bean name
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
        // hoặc findByEmailIgnoreCase(email) nếu repo đã có và muốn ổn định hơn
    }
}
