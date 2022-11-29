package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.impl.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
@Qualifier("DataBase")
public class FilmDbStorage implements FilmStorage {
    FilmDao filmDao;

    @Autowired
    public FilmDbStorage(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Override
    public List<Film> getAll() {
        return filmDao.getAll();
    }

    @Override
    public Film add(Film film) {
        return filmDao.addEntity(film);
    }

    @Override
    public Film change(Film film) {
        return filmDao.changeEntity(film);
    }

    @Override
    public Film getById(int id) {
        return filmDao.getEntityById(id).get();
    }

    @Override
    public void addLike(int filmId, int userId) {
        filmDao.addLike(filmId, userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        filmDao.removeLike(filmId, userId);
    }
}
