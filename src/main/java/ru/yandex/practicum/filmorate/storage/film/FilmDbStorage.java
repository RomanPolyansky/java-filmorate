package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReadWriteEntityDao;
import ru.yandex.practicum.filmorate.dao.impl.FilmDao;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
        try {
            return filmDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Film add(Film film) {
        try {
            return filmDao.addEntity(film);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Film change(Film film) {
        try {
            return filmDao.changeEntity(film);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Film getById(int id) {
        try {
            Optional<Film> film = filmDao.getEntityById(id);
            if (film.isEmpty()) throw new EntityNotFoundException("Entity with id (" + id + ") not found");
            return film.get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addLike(int filmId, int userId) {
        try {
            filmDao.addLike(filmId, userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeLike(int filmId, int userId) {
        try {
            filmDao.removeLike(filmId, userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
