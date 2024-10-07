package ru.clevertec.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.springboot.entity.Metric;

public interface MetricRepository extends JpaRepository<Metric, Long> {
}
