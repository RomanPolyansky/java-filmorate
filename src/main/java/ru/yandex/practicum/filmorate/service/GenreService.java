package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;

import java.util.Collection;

@Service
public class GenreService {
    private final ReadEntityDao<Genre> genreStorage;

    @Autowired
    public GenreService(ReadEntityDao<Genre> genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getGenreById(int id) {
        return genreStorage.getEntityById(id).get();
    }

    public Collection<Genre> getGenres() {
        return genreStorage.getAll();
    }
}
