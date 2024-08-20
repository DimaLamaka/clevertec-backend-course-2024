package ru.clevertec.unit.testing.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UuidUtil {
    public UUID generateUuid() {
        return UUID.randomUUID();
    }
}
