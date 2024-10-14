package ru.clevertec.application.dto;

import jakarta.validation.constraints.NotNull;

public record DirectoryOfferRequest(@NotNull String directoryName) {
}
