package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreDao {
    Collection<Genre> getGenres();
    Optional<Genre> getGenreById(String id);
}
