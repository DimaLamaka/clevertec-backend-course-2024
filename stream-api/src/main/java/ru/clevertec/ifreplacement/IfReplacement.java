package ru.clevertec.ifreplacement;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IfReplacement {
    private final Handler1 handler1;
    private final Handler2 handler2;
    private final Handler3 handler3;

    public String handle(WeatherRequest weatherRequest) {
        StringBuilder result = new StringBuilder();
        List<Source> sources = weatherRequest.sources();
        for (Source source : sources) {
            if (Source.FIRST.equals(source)) {
                String handle = handler1.handle();
                result.append(handle);
            }
            if (Source.SECOND.equals(source)) {
                String handle = handler2.handle();
                result.append(handle);
            }
            if (Source.THIRD.equals(source)) {
                String handle = handler3.handle();
                result.append(handle);
            }
        }
        return result.toString();
    }
}
