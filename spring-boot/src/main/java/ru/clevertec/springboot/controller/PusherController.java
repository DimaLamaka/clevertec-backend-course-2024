package ru.clevertec.springboot.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.springboot.dto.PushRequest;
import ru.clevertec.springboot.dto.PushResponse;
import ru.clevertec.springboot.service.push.PushService;

@Slf4j
@RestController
@RequestMapping("api/v1/push")
@RequiredArgsConstructor
public class PusherController {
    private final Gson gson;
    private final PushService pushService;

    @PostMapping
    public ResponseEntity<PushResponse> push(@RequestBody PushRequest pushRequest) {
        log.info("POST /v1/push with request: {}", gson.toJson(pushRequest));
        PushResponse pushResponse = pushService.pushMessages(pushRequest);
        log.info("RESPONSE POST /v1/push: {}", gson.toJson(pushResponse));
        return ResponseEntity.ok(pushResponse);
    }
}
