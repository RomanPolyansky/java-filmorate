package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.ReadStorage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GenreDbStorage implements ReadStorage<Genre> {
    private final ReadEntityDao<Genre> genreDao;

    public GenreDbStorage(ReadEntityDao<Genre> genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        try {
            return genreDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Genre getById(int id) {
        try {
            Optional<Genre> genre = genreDao.getEntityById(id);
            if (genre.isEmpty()) throw new EntityNotFoundException("Entity with id (" + ") not found");
            return genre.get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
