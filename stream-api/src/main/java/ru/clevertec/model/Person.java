package ru.clevertec.model;

import java.util.List;

public record Person(String name,
                     int age,
                     List<Task> tasks) {
}
