package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.ReadStorage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MpaDbStorage implements ReadStorage<Mpa> {
    private final ReadEntityDao<Mpa> mpaDao;

    public MpaDbStorage(ReadEntityDao<Mpa> mpaDao) {
        this.mpaDao = mpaDao;
    }

    @Override
    public List<Mpa> getAll() {
        try {
            return mpaDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mpa getById(int id) {
        try {
            Optional<Mpa> mpa = mpaDao.getEntityById(id);
            if (mpa.isEmpty()) throw new EntityNotFoundException("Entity with id (" + ") not found");
            return mpa.get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
