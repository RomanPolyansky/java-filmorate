package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

@Service
public class FilmService {
    public final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }


    public Film addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        film.getLikeUserIds().add(userId);
        return film;
    }

    public Film removeLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (!film.getLikeUserIds().remove(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User's like Not Found");
        return film;
    }

    public List<Film> getTopFilms(int count) {
        List<Film> films = filmStorage.getFilms();
        films.sort(new FilmsByLikesComparator());
        if (count == 0) {
            int TOP_COUNT = 10;
            films = films.subList(0, Integer.min(TOP_COUNT - 1, films.size()));
        } else {
            films = films.subList(0, count);
        }
        return films;
    }

    static class FilmsByLikesComparator implements Comparator<Film> {

        @Override
        public int compare(Film item1, Film item2) {
            return Integer.compare(item2.getLikeUserIds().size(), item1.getLikeUserIds().size());
        }
    }
}
