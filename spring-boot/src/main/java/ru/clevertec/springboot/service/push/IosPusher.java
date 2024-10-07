package ru.clevertec.springboot.service.push;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.springboot.enums.Platform;
import ru.clevertec.springboot.gateway.PushGateway;
import ru.clevertec.starter.annotation.MetricHandler;

import static ru.clevertec.springboot.dto.PushRequest.*;
import static ru.clevertec.springboot.dto.PushResponse.*;
import static ru.clevertec.springboot.enums.Platform.IOS;

@Slf4j
@Service
@RequiredArgsConstructor
public class IosPusher implements Pusher {
    private final Gson gson;
    private final PushGateway pushGateway;

    @MetricHandler(
            metricPrefixName = "IOS_PUSH"
    )
    @Override
    public PushResult sendPush(PushDto push) {
        log.info("Trying to send push to ios, with push: {}", gson.toJson(push));
        PushResult pushResult = pushGateway.pushIosMessage(push);
        log.info("Response from ios: {}", gson.toJson(pushResult));
        return pushResult;
    }

    @Override
    public Platform getPlatform() {
        return IOS;
    }
}
