package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.ReadStorage;

import java.util.List;

public class GenreDbStorage implements ReadStorage<Genre> {
    private final ReadEntityDao<Genre> genreDao;

    public GenreDbStorage(ReadEntityDao<Genre> genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Genre getById(int id) {
        return genreDao.getEntityById(id).get();
    }
}
