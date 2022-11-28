package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;

import java.sql.SQLException;
import java.util.List;

public interface FilmStorage extends ReadWriteStorage<Film> {
    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);
}
