package ru.clevertec;

import java.util.StringJoiner;

public class StringExample {
    public static void main(String[] args) {
        String json = """
                {
                    "name": "John",
                    "age": 30,
                    "city": "New York"
                }
                """;
        System.out.println(json);

        String stringJoiner = new StringJoiner(", ", "[", "]")
                .add("Alice")
                .add("Bob")
                .add("Charlie")
                .toString();
        System.out.println(stringJoiner);

        String s = "  Hello  ";
        System.out.println(s.strip()); // "Hello"
        System.out.println("Java".repeat(3)); // "JavaJavaJava"
        System.out.println("   ".isBlank()); // true

        String text = "Hello\n  World";
        System.out.println(text.indent(4)); // n > 0 n spaces are inserted at the beginning of each line
        System.out.println(text.indent(-2)); // n < 0 then up to n white space characters are removed from the beginning of each line.

        boolean contentEquals = "val".contentEquals(new StringBuilder("val"));
        System.out.println(contentEquals);

        String transform = "val".transform(String::toUpperCase);
        System.out.println(transform);

        int x = 1;
        int y = 21;
        String result = STR."\{x} + \{y} = \{x + y}";
        System.out.println(result);

    }
}
