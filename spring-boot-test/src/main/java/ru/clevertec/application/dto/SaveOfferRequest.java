package ru.clevertec.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SaveOfferRequest(@Valid @NotNull OfferDto offer) {
}
