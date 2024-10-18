package ru.clevertec.application.dto.directory;

import java.time.LocalDate;

public record DirectoryFullInfoOffer(String id, String category, String code, String title,
                                     String description, String condition, Double tariff,
                                     LocalDate startDate, LocalDate endDate) {
}
