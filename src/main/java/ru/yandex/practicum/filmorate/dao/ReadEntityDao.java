package ru.yandex.practicum.filmorate.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ReadEntityDao <T> {
    List<T> getAll() throws SQLException;
    Optional<T> getEntityById(int id) throws SQLException;
}