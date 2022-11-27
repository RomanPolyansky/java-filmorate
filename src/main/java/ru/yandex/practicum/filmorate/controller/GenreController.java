package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

public class GenreController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public Collection<Genre> getGenres() {
        log.info("Получен запрос GET /genres");
        return genreService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable String id) {
        log.info("Получен запрос GET /genres/{id}");
        return genreService.getGenreById(Integer.parseInt(id));
    }
}
