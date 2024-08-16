package ru.clevertec.stream;

import lombok.RequiredArgsConstructor;
import ru.clevertec.generator.PersonGenerator;
import ru.clevertec.model.Person;
import ru.clevertec.model.Task;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class IntermediateOperation {
    private final PersonGenerator personGenerator;

    public void showIntermediateOperationExamples() {
        List<Person> persons = personGenerator.generatePersons();

        //filter
        List<Person> filteredPersons1 = persons.stream()
                .filter(person -> "Alice".equals(person.name()))
                .toList();
        List<Person> filteredPersons2 = persons.stream()
                .filter(person -> person.tasks().stream().anyMatch(Task::completed))
                .toList();

        //map
        List<String> names = persons.stream()
                .map(Person::name)
                .toList();

        //flatmap
        List<Task> allTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .toList();

        List<Task> allTasks2 = persons.stream()
                .map(Person::tasks)
                .flatMap(Collection::stream)
                .toList();

        //distinct
        List<Task> uniqueTasks = persons.stream()
                .map(person -> person.tasks())
                .flatMap(Collection::stream)
                .distinct()
                .toList();
        


        //sorted
        List<Task> sortedTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .sorted(Comparator.comparing(Task::name))
                .toList();

        //limit
        List<Person> firstTwoPeople = persons.stream()
                .limit(2)
                .toList();

        //skip
        List<Task> remainingTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .skip(2)
                .toList();

        //peek
        List<Task> loggedTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .peek(task -> System.out.println("Processing task: " + task.name()))
                .toList();

        //takeWhile
        List<Task> takenTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .takeWhile(Task::completed)
                .toList();

        //dropWhile
        List<Task> droppedTasks = persons.stream()
                .flatMap(person -> person.tasks().stream())
                .dropWhile(Task::completed)
                .toList();

        //IntStream
        double average = persons.stream()
                .mapToInt(Person::age)
                .average()
                .orElse(0.0);

        long count = persons.stream()
                .mapToInt(Person::age)
                .count();

        int min = persons.stream()
                .mapToInt(Person::age)
                .min()
                .orElseThrow(RuntimeException::new);

        int max = persons.stream()
                .mapToInt(Person::age)
                .max()
                .orElseThrow(RuntimeException::new);
    }
}
