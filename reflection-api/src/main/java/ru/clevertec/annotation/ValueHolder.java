package ru.clevertec.annotation;

import lombok.Data;

@Data
public class ValueHolder {
    @RandomValue(min = 22, max = 34235424)
    private int value;
}
