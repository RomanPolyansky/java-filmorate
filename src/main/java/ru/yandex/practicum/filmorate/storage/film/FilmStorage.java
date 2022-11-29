package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;

public interface FilmStorage extends ReadWriteStorage<Film> {
    void addLike(int filmId, int userId) throws EntityNotFoundException;

    void removeLike(int filmId, int userId) throws EntityNotFoundException;
}
