package ru.clevertec.app.servlet.filter.service;

import ru.clevertec.app.servlet.filter.model.User;
import ru.clevertec.app.servlet.filter.repository.UserRepository;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
