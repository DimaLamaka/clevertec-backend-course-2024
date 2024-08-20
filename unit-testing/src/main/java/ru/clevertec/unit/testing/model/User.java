package ru.clevertec.unit.testing.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class User {
    private UUID uuid;
    private String name;
    private Role role;

    public enum Role {
        ADMIN,
        USER,
        GUEST
    }
}
