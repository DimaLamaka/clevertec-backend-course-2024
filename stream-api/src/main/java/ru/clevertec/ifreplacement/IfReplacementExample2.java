package ru.clevertec.ifreplacement;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class IfReplacementExample2 {
    private final Map<Predicate<Integer>, UnaryOperator<Integer>> handlers = new HashMap<>();

    {
        handlers.put(number -> number >= 1 && number <= 10, number -> number * 2);
        handlers.put(number -> number >= 11 && number <= 20, number -> number * 3);
        handlers.put(number -> number >= 21 && number <= 30, number -> number * 4);
    }

    //if 1-10 -> x2
    //if 11-20 -> x3
    //if 21-30 -> x4
    //another cases-> x 100
    public int multiplyByRange(int number) {
        if (number >= 1 && number <= 10) {
            return number * 2;
        }
        if (number >= 11 && number <= 20) {
            return number * 3;
        }
        if (number >= 21 && number <= 30) {
            return number * 4;
        }
        return number * 100;
    }

    public int improveMultiplyByRange(int number) {
        return handlers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().test(number))
                .findFirst()
                .map(Map.Entry::getValue)
                .map(operator -> operator.apply(number))
                .orElseGet(() -> number * 100);
    }
}
