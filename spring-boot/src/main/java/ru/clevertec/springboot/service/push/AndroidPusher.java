package ru.clevertec.springboot.service.push;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.springboot.enums.Platform;
import ru.clevertec.springboot.gateway.PushGateway;
import ru.clevertec.springboot.service.metric.DatabaseMetricSender;
import ru.clevertec.starter.annotation.MetricHandler;
import ru.clevertec.starter.enums.Metric;

import static ru.clevertec.springboot.dto.PushRequest.*;
import static ru.clevertec.springboot.dto.PushResponse.*;
import static ru.clevertec.springboot.enums.Platform.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AndroidPusher implements Pusher {
    private final Gson gson;
    private final PushGateway pushGateway;

    @MetricHandler(
            metricPrefixName = "ANDROID_PUSH",
            metrics = {Metric.EXECUTION_TIME},
            includeDefaultMetrics = false,
            metricSenders = DatabaseMetricSender.class
    )
    @Override
    public PushResult sendPush(PushDto push) {
        log.info("Trying to send push to android, with push: {}", gson.toJson(push));
        PushResult pushResult = pushGateway.pushAndroidMessage(push);
        log.info("Response from android: {}", gson.toJson(pushResult));
        return pushResult;
    }

    @Override
    public Platform getPlatform() {
        return ANDROID;
    }
}
