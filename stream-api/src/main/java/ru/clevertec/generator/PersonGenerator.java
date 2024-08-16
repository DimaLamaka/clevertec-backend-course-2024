package ru.clevertec.generator;

import ru.clevertec.model.Person;
import ru.clevertec.model.Task;

import java.util.List;

public class PersonGenerator {

    public List<Person> generatePersons() {
        return List.of(
                new Person("Alice", 22, List.of(
                        new Task("Task 1", true),
                        new Task("Task 2", false),
                        new Task("Task 3", true)
                )),
                new Person("Bob", 19, List.of(
                        new Task("Task 4", false),
                        new Task("Task 5", true),
                        new Task("Task 6", false)
                )),
                new Person("Charlie", 16, List.of(
                        new Task("Task 7", true),
                        new Task("Task 8", false)
                )),
                new Person("Jack", 33, List.of(
                        new Task("Task 1", true),
                        new Task("Task 2", false),
                        new Task("Task 3", true)
                )),
                new Person("Charlie", 21, List.of(
                        new Task("Task 99", true),
                        new Task("Task 100", false)
                ))
        );
    }
}
