package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FilmController {

    Map<Integer, Film> filmMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    int id = 1;


    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос GET /films");
        return new ArrayList<>(filmMap.values());
    }

    @PutMapping(value = "/films")
    @ResponseBody
    public Film addFilm(@RequestBody Film film) {
        try {
            Validator.filmValidation(film);
            if (filmMap.containsKey(film.getId())) {
                throw new ValidationException("to update film use POST method");
            }
            film.setId(id++);
            filmMap.put(film.getId(), film);
            log.info(film + " is put into db");
        } catch (ValidationException e) {
            log.info(e.getMessage());
        }
        return film;
    }

    @PostMapping(value = "/films")
    @ResponseBody
    public Film changeFilm(@RequestBody Film film) {
        try {
            Validator.filmValidation(film);
            if (!filmMap.containsKey(film.getId())) {
                throw new ValidationException("id is not valid for update");
            }
            filmMap.put(film.getId(), film);
            log.info(film + " is put into db");
        } catch (ValidationException e) {
            log.info(e.getMessage());
        }
        return film;
    }
}
