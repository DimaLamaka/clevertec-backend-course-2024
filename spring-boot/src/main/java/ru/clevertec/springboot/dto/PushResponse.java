package ru.clevertec.springboot.dto;

import lombok.experimental.Accessors;
import ru.clevertec.springboot.enums.Status;

import java.util.List;

@Accessors(chain = true)
public record PushResponse(List<PushResult> pushesResult) {
    @Accessors(chain = true)
    public record PushResult(String id, Status status) {

    }
}
