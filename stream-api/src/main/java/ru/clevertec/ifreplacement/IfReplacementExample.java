package ru.clevertec.ifreplacement;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class IfReplacementExample {
    private final List<Handler> handlers = List.of(new Handler1(), new Handler2(), new Handler3());

    public String handle(WeatherRequest weatherRequest) {
        return Optional.ofNullable(weatherRequest.sources())
                .stream()
                .flatMap(Collection::stream)
                .map(this::getHandlerBySource)
                .map(Handler::handle)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    private Handler getHandlerBySource(Source source) {
        return handlers.stream()
                .filter(handler -> handler.getType().equals(source))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Handler not found"));
    }
}

interface Handler {
    String handle();

    Source getType();
}

class Handler1 implements Handler {
    @Override
    public String handle() {
        return "Weather from source 1";
    }

    @Override
    public Source getType() {
        return Source.FIRST;
    }
}

class Handler2 implements Handler {
    @Override
    public String handle() {
        return "Weather from source 2";
    }

    @Override
    public Source getType() {
        return Source.SECOND;
    }
}

class Handler3 implements Handler {
    @Override
    public String handle() {
        return "Weather from source 3";
    }

    @Override
    public Source getType() {
        return Source.THIRD;
    }
}

