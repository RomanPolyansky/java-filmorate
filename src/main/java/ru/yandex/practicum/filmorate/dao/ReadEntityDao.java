package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ReadEntityDao <T> {
    List<T> getAll() throws EntityNotFoundException;
    Optional<T> getEntityById(int id) throws EntityNotFoundException;
}