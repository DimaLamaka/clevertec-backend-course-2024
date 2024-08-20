package ru.clevertec.unit.testing.service;


import lombok.AllArgsConstructor;
import ru.clevertec.unit.testing.model.User;
import ru.clevertec.unit.testing.repository.UserRepository;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public User add(User user) {
        return userRepository.add(user);
    }

    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    public User getById(UUID id) {
        return userRepository.getById(id);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

}
