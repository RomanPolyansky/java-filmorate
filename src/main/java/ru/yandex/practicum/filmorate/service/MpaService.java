package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Service
public class MpaService {
    private final ReadEntityDao<Mpa> mpaStorage;

    @Autowired
    public MpaService(ReadEntityDao<Mpa> mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Mpa getMpaById(int id) {
        return mpaStorage.getEntityById(id).get();
    }

    public Collection<Mpa> getMpas() {
        return mpaStorage.getAll();
    }
}
