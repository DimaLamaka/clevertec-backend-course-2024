package ru.clevertec.springboot.service.push;


import ru.clevertec.springboot.enums.Platform;

import static ru.clevertec.springboot.dto.PushRequest.*;
import static ru.clevertec.springboot.dto.PushResponse.*;

public interface Pusher {
    PushResult sendPush(PushDto push);

    Platform getPlatform();
}
