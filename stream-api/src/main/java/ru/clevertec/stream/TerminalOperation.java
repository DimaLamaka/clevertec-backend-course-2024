package ru.clevertec.stream;

import lombok.RequiredArgsConstructor;
import ru.clevertec.generator.PersonGenerator;
import ru.clevertec.model.Person;
import ru.clevertec.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TerminalOperation {
    private final PersonGenerator personGenerator;

    public void showTerminalOperations() {
        List<Person> persons = personGenerator.generatePersons();

        //toList
        List<String> names = persons.stream()
                .map(Person::name)
                .toList();

        //unique tasks
        Set<Task> uniqueTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .collect(Collectors.toSet());

        //count
        long taskCount = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .count();

        //foreach
        persons.stream()
                .flatMap(person -> person.tasks().stream())
                .forEach(task -> System.out.println("Task: " + task.name()));

        //reduce
        int totalCompletedTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .map(task -> task.completed() ? 1 : 0)
                .reduce(0, Integer::sum);

        String allNames = persons.stream()
                .map(Person::name)
                .reduce("", (a, b) -> a + ", " + b);

        //findFirst
        Optional<Task> firstIncompleteTask = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .filter(task -> !task.completed())
                .findFirst();

        //findAny for parallel
        Optional<Task> any = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .filter(Task::completed)
                .findAny();

        //allMatch
        boolean allTasksCompleted = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .allMatch(Task::completed);

        //anyMatch
        boolean hasCompletedTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .anyMatch(Task::completed);

        //noneMatch
        boolean noIncompleteTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .noneMatch(task -> !task.completed());

    }
}
