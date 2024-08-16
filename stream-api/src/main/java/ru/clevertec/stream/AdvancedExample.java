package ru.clevertec.stream;

import lombok.RequiredArgsConstructor;
import ru.clevertec.generator.PersonGenerator;
import ru.clevertec.model.Person;
import ru.clevertec.model.Task;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AdvancedExample {
    private final PersonGenerator personGenerator;

    public void showAdvancedExamples() {
        List<Person> persons = personGenerator.generatePersons();

        Map<String, Double> groupingPersonWithAverageAge = persons.stream()
                .collect(//anna -> 10 , anna-> 20
                        Collectors.groupingBy(Person::name, //key-> anna , {anna 10, anna 20}
                                Collectors.averagingInt(Person::age) //key-> anna, 15
                        )
                );
        System.out.println("groupingPersonWithAverageAge: %s".formatted(groupingPersonWithAverageAge));

        Map<String, Set<Task>> groupingTasks = persons.stream()
                .collect(
                        Collectors.groupingBy(
                                Person::name,
                                Collectors.filtering(person -> person.tasks().stream().anyMatch(Task::completed),
                                        Collectors.mapping(Person::tasks,
                                                Collectors.flatMapping(Collection::stream, Collectors.toSet()))
                                )
                        )
                );
        System.out.println("groupingTasks: %s".formatted(groupingTasks));

        Map<String, String> personWithCountCompletedAndNotCompletedTasks = persons.stream()
                .collect(
                        Collectors.groupingBy(
                                Person::name,
                                Collectors.mapping(
                                        Person::tasks,
                                        Collectors.flatMapping(
                                                Collection::stream,
                                                Collectors.collectingAndThen(
                                                        Collectors.partitioningBy(Task::completed),
                                                        map -> "completed: %s, not completed: %s".formatted(map.get(Boolean.TRUE), map.get(Boolean.FALSE)))
                                        )
                                )
                        )
                );
        System.out.println("personWithCountCompletedAndNotCompletedTasks: %s".formatted(personWithCountCompletedAndNotCompletedTasks));

    }

    public void showCurryingMethods() {
        Function<String, Function<Integer, Person>> function = name -> age -> new Person(name, age, Collections.emptyList());
        Person person = function.apply("name").apply(18);

        record PersonInfo(String name, String surname, String address) {
        }

        Stream.of("name")
                .map(name -> {
                    String surname = getSurname(name);
                    String address = getAddress(surname);
                    return new PersonInfo(name, surname, address);
                });

        Function<String, Function<String, PersonInfo>> functionFunction = name -> surname -> new PersonInfo(name, surname, getAddress(surname));

        Stream.of("name")
                .map(name -> functionFunction.apply(name).apply(getSurname(name)));
    }

    private String getSurname(String name) {
        return "surname";
    }

    private String getAddress(String surname) {
        return "address";
    }
}
