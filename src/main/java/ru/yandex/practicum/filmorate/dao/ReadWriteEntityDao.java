package ru.yandex.practicum.filmorate.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ReadWriteEntityDao<T> extends ReadEntityDao<T> {
    List<T> getAll() throws SQLException;
    T addEntity(T entity) throws SQLException;
    T changeEntity(T entity) throws SQLException;
    Optional<T> getEntityById(int id) throws SQLException;
}
