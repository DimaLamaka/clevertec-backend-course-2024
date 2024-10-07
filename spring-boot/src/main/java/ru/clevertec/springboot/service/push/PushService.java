package ru.clevertec.springboot.service.push;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.clevertec.springboot.dto.PushRequest;
import ru.clevertec.springboot.dto.PushResponse;
import ru.clevertec.springboot.dto.PushResponse.PushResult;
import ru.clevertec.springboot.enums.Platform;
import ru.clevertec.springboot.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushService {
    private final List<Pusher> pushers;
    private Map<Platform, Pusher> handlers;

    @PostConstruct
    void init() {
        handlers = pushers.stream()
                .collect(Collectors.toMap(Pusher::getPlatform, Function.identity()));
    }

    public PushResponse pushMessages(PushRequest pushRequest) {
        List<PushResult> pushResults = pushRequest.pushes()
                .stream()
                .map(push -> this.getPusherByPlatform(push.platform()).sendPush(push))
                .toList();
        return new PushResponse(pushResults);
    }

    private Pusher getPusherByPlatform(Platform platform) {
        return Optional.ofNullable(handlers.get(platform))
                .orElseThrow(() -> new ServiceException("Handler not fount for platform: " + platform, HttpStatus.BAD_REQUEST));
    }
}
