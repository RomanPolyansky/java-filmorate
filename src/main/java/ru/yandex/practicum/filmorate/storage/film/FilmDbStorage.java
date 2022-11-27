package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;

import java.util.List;

public class FilmDbStorage implements ReadWriteStorage<Film> {
    FilmDao filmDao;

    @Autowired
    public FilmDbStorage(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Override
    public List<Film> getAll() {
        return null;
    }

    @Override
    public Film add(Film film) {
        return null;
    }

    @Override
    public Film change(Film film) {
        return null;
    }

    @Override
    public Film getById(int id) {
        return null;
    }
}
