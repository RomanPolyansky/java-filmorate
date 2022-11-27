package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public class FilmDbStorage implements FilmStorage {
    FilmDao filmDao;

    @Autowired
    public FilmDbStorage(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Override
    public List<Film> getFilms() {
        return null;
    }

    @Override
    public Film addFilm(Film film) {
        return null;
    }

    @Override
    public Film changeFilm(Film film) {
        return null;
    }

    @Override
    public Film getFilmById(int id) {
        return null;
    }
}
