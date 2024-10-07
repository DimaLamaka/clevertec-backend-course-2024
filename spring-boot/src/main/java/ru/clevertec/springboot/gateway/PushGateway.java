package ru.clevertec.springboot.gateway;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.springboot.dto.PushResponse.PushResult;
import ru.clevertec.springboot.enums.Status;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static ru.clevertec.springboot.dto.PushRequest.*;

@Slf4j
@Service
public class PushGateway {

    @SneakyThrows
    public PushResult pushAndroidMessage(PushDto push) {
        int payload = ThreadLocalRandom.current().nextInt(5) + 1;
        TimeUnit.SECONDS.sleep(payload);
        return new PushResult(push.id(), Status.OK);
    }

    @SneakyThrows
    public PushResult pushIosMessage(PushDto push) {
        int payload = ThreadLocalRandom.current().nextInt(5) + 1;
        TimeUnit.SECONDS.sleep(payload);
        return new PushResult(push.id(), Status.ERROR);
    }
}
