package hsf302.he187383.phudd.carrental.service.impl;

import hsf302.he187383.phudd.carrental.model.User;
import hsf302.he187383.phudd.carrental.repository.UserRepository;
import hsf302.he187383.phudd.carrental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
