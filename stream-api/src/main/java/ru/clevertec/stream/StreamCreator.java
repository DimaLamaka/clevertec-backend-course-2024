package ru.clevertec.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamCreator {

    public void create() {
        //Collection
        List<String> list = List.of("a", "b", "c");
        Stream<String> listStream = list.stream();

        Queue<String> queue = new PriorityQueue<>(list);
        Stream<String> queueStream = queue.stream();

        //Array
        String[] stringArray = {"a", "b", "c"};
        Stream<String> arrayStream1 = Arrays.stream(stringArray);
        Stream<String> arrayStream2 = Stream.of(stringArray);

        //Values
        Stream<Integer> stream = Stream.of(1, 2, 3);
        Stream<String> stream2 = Stream.of("a", "b", "c");

        //Empty
        Stream<String> emptyStream = Stream.empty();
        Stream<String> nullableStream = Stream.ofNullable("a");

        //Builder
        Stream<String> streamBuilder = Stream.<String>builder()
                .add("a")
                .add("b")
                .add("c")
                .build();

        //Infinity
        Stream<Double> randomNumbers = Stream.generate(Math::random);

        //Infinity with limit
        Stream<String> repeatedString = Stream.generate(() -> "repeat").limit(10);

        //Iterator
        Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2).limit(10);

        //File
        Path path = Paths.get("file.txt");
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //String
        IntStream charStream = "hello".chars();
        IntStream codePointStream = "hello".codePoints();

        Pattern pattern = Pattern.compile(" ");
        Stream<String> wordStream = pattern.splitAsStream("Java 21 Stream API");

        //Primitive stream
        IntStream intStream = IntStream.of(1, 2, 3);
        DoubleStream doubleStream = DoubleStream.of(1, 2, 3);
        LongStream longStream = LongStream.of(1, 2, 3);

        //Optional
        Optional<String> optional = Optional.of("hello");
        Stream<String> optionalStream = optional.stream();

        //parallel stream
        Stream<String> parallelStream = Stream.of("a", "b", "c").parallel();

        //parallel stream with optimization
        Stream<String> unorderedParallel = Stream.of("a", "b", "c")
                .parallel()
                .unordered();

    }
}
