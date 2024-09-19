package ru.clevertec.app.servlet.filter.model;

public record User(String username,
                   String password,
                   String role) {
}
