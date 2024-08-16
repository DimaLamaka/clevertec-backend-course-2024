package ru.clevertec.optional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalExample {

    public void showOptionalExample() {
        String example1 = Optional.ofNullable(getValue())
                .filter(value -> value.length() < 10)
                .or(this::anotherValue)
                .map(String::toUpperCase)
                .orElseThrow(() -> new RuntimeException("Invalid value"));

        Integer example2 = Optional.ofNullable(getValue())
                .flatMap(this::length)
                .orElse(0);

        Integer example3 = Optional.ofNullable(getValue())
                .flatMap(this::length)
                .orElseGet(() -> 0);

        String reduce = Optional.ofNullable(getValue())
                .or(this::anotherValue)
                .stream()
                .map(value -> value.split(""))
                .flatMap(Arrays::stream)
                .reduce((s1, s2) -> s1.concat(", ").concat(s2))
                .orElseThrow();

        Optional.ofNullable(getValue())
                .ifPresentOrElse(
                        value -> System.out.println(value + " is present"),
                        ()-> System.out.println("Value is absent")
                );

    }

    public Optional<String> anotherValue() {
        return Optional.of("value2");
    }

    public String getValue() {
        return null;
    }

    public Optional<Integer> length(String value) {
        return Optional.ofNullable(value)
                .map(String::length);
    }

    public static void main(String[] args) {
        OptionalExample optionalExample = new OptionalExample();
        optionalExample.showOptionalExample();
    }
}
