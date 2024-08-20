package ru.clevertec.unit.testing.repository;


import java.util.Collection;

public interface Repository<T, I> {
    T add(T t);

    Collection<T> getAll();

    T getById(I id);

    void deleteById(I id);
}
