package ru.clevertec.app.servlet.filter.repository;

import ru.clevertec.app.servlet.filter.model.User;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private final ConcurrentHashMap<String, User> users;

    public UserRepository() {
        users = new ConcurrentHashMap<>();
        users.put("admin", new User("admin", "admin", "ADMIN"));
        users.put("user", new User("user", "user", "USER"));
    }

    public UserRepository(ConcurrentHashMap<String, User> users) {
        this.users = users;
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }
}
