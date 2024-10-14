package ru.clevertec.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OfferDto(@NotBlank String code, @NotBlank String category, @NotBlank String title,
                       String description, @NotBlank String condition, @Positive Double tariff) {
}
