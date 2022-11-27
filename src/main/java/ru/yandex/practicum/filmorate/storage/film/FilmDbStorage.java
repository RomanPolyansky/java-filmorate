package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReadWriteEntityDao;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("DataBase")
public class FilmDbStorage implements ReadWriteStorage<Film> {
    ReadWriteEntityDao<Film> filmDao;

    @Autowired
    public FilmDbStorage(ReadWriteEntityDao<Film> filmDao) {
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
            if (film.isEmpty()) throw new EntityNotFoundException("Entity with id (" + ") not found");
            return film.get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
