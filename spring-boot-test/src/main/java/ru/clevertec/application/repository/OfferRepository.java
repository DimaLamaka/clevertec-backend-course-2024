package ru.clevertec.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.application.entity.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
