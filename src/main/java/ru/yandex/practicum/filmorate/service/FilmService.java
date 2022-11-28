package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.comparator.FilmsByLikesComparator;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final int DEFAULT_TOP_FILMS_COUNT = 10;

    @Autowired
    public FilmService(@Qualifier("DataBase") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film getFilmById(int id) {
        return filmStorage.getById(id);
    }

    public List<Film> getFilms() {
        return filmStorage.getAll();
    }

    public Film addFilm(Film film) {
        return filmStorage.add(film);
    }

    public Film changeFilm(Film film) {
        return filmStorage.change(film);
    }

    public Film addLike(int filmId, int userId) {
        filmStorage.addLike(filmId, userId);
        return filmStorage.getById(filmId);
    }

    public Film removeLike(int filmId, int userId) {
        filmStorage.removeLike(filmId, userId);
        return filmStorage.getById(filmId);
    }

    public List<Film> getTopFilms(int count) {
        List<Film> films = filmStorage.getAll();
        films.sort(new FilmsByLikesComparator());
        if (count == 0) {
            films = films.subList(0, Integer.min(DEFAULT_TOP_FILMS_COUNT - 1, films.size()));
        } else {
            films = films.subList(0, count);
        }
        return films;
    }
}
