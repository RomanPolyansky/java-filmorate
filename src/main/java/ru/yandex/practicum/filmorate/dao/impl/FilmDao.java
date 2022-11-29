package ru.yandex.practicum.filmorate.dao.impl;

import ru.yandex.practicum.filmorate.dao.ReadWriteEntityDao;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmDao extends ReadWriteEntityDao<Film> {
    void addLike(int filmId, int userId) throws EntityNotFoundException;

    void removeLike(int filmId, int userId) throws EntityNotFoundException;
}
