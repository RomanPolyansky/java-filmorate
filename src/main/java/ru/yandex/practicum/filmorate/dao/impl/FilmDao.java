package ru.yandex.practicum.filmorate.dao.impl;

import ru.yandex.practicum.filmorate.dao.ReadWriteEntityDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLException;

public interface FilmDao extends ReadWriteEntityDao<Film> {
    void addLike(int filmId, int userId) throws SQLException;

    void removeLike(int filmId, int userId) throws SQLException;
}
