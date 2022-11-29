package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ReadWriteEntityDao<T> extends ReadEntityDao<T> {
    T addEntity(T entity) throws EntityNotFoundException;
    T changeEntity(T entity) throws EntityNotFoundException;
}
