package ru.clevertec.springboot.dto;

import lombok.experimental.Accessors;
import ru.clevertec.springboot.enums.Platform;

import java.util.List;

@Accessors(chain = true)
public record PushRequest(List<PushDto> pushes) {

    @Accessors(chain = true)
    public record PushDto(String id, Platform platform, String pushMessage) {

    }
}
