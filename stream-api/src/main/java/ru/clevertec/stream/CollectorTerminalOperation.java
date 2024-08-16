package ru.clevertec.stream;

import lombok.RequiredArgsConstructor;
import ru.clevertec.generator.PersonGenerator;
import ru.clevertec.model.Person;
import ru.clevertec.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CollectorTerminalOperation {
    private final PersonGenerator personGenerator;

    public void showCollectorTerminalOperations() {
        List<Person> persons = personGenerator.generatePersons();

        //Set
        Set<Task> uniqueTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .collect(Collectors.toSet());

        //Map
        Map<String, Person> nameToAgeMap = persons.stream()
                .collect(Collectors.toMap(Person::name, Function.identity(), (p1, p2) -> p2));

        //grouping
        Map<Integer, List<Person>> peopleByAge = persons.stream()
                .collect(Collectors.groupingBy(Person::age));

        Map<Boolean, List<Task>> tasksByStatus = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .collect(Collectors.groupingBy(Task::completed));

        //partitioning
        Map<Boolean, List<Task>> partitionedTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .collect(Collectors.partitioningBy(Task::completed));

        //mapping
        List<Integer> nameLengths = persons.stream()
                .collect(
                        Collectors.mapping(
                                person -> person.name().length(),
                                Collectors.toList())
                );

        //counting
        long numberOfPeople = persons.stream()
                .collect(Collectors.counting());

        //averaging
        double averageAge = persons.stream()
                .collect(Collectors.averagingInt(Person::age));

        //String
        String names = persons.stream()
                .map(Person::name)
                .collect(Collectors.joining(", ", "[", "]"));


    }
}
