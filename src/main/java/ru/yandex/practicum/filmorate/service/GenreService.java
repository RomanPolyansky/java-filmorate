package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;

import java.util.Collection;

public class GenreService {
    private final ReadWriteStorage<Genre> genreStorage;

    @Autowired
    public GenreService(ReadWriteStorage<Genre> genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getGenreById(int id) {
        return genreStorage.getById(id);
    }

    public Collection<Genre> getGenres() {
        return genreStorage.getAll();
    }
}
