package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.ReadStorage;

import java.util.List;

public class MpaDbStorage implements ReadStorage<Mpa> {
    private final ReadEntityDao<Mpa> mpaDao;

    public MpaDbStorage(ReadEntityDao<Mpa> mpaDao) {
        this.mpaDao = mpaDao;
    }

    @Override
    public List<Mpa> getAll() {
        return mpaDao.getAll();
    }

    @Override
    public Mpa getById(int id) {
        return mpaDao.getEntityById(id).get();
    }
}
