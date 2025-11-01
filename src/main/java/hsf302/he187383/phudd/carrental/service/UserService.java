package hsf302.he187383.phudd.carrental.service;

import hsf302.he187383.phudd.carrental.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}