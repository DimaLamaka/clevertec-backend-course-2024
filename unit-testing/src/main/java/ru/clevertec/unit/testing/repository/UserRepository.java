package ru.clevertec.unit.testing.repository;

import ru.clevertec.unit.testing.model.User;
import ru.clevertec.unit.testing.util.UuidUtil;

import java.util.*;

public class UserRepository implements Repository<User, UUID> {
    private static final Map<UUID, User> users = new HashMap<>();

    @Override
    public User add(User user) {
        return Optional.ofNullable(user)
                .filter(x -> Objects.isNull(x.getUuid()))
                .map(x -> x.setUuid(UuidUtil.generateUuid()))
                .map(x -> {
                    users.put(x.getUuid(), x);
                    return x;
                })
                .orElseThrow(() -> new RuntimeException("Invalid user"));
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User getById(UUID id) {
        return Optional.ofNullable(id)
                .map(users::get)
                .orElseThrow(() -> new RuntimeException("Id is required"));
    }

    @Override
    public void deleteById(UUID id) {
        users.remove(id);
    }
}
