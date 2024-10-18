package ru.clevertec.application.dto.directory;

import java.time.LocalDate;

public record DirectoryShortInfoOffer(String id, String code, LocalDate startDate, LocalDate endDate) {
}
