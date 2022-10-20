package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.system.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FilmController {

    final Map<Integer, Film> filmMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    int id = 1;


    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос GET /films");
        return new ArrayList<>(filmMap.values());
    }

    @PostMapping(value = "/films")
    @ResponseBody
    public Film addFilm(@RequestBody Film film) {
        Validator.filmValidation(film);
        if (filmMap.containsKey(film.getId())) {
            throw new ValidationException("to update film use POST method");
        }
        film.setId(id++);
        filmMap.put(film.getId(), film);
        log.info(film + " is put into db");
        return film;
    }

    @PutMapping(value = "/films")
    @ResponseBody
    public Film changeFilm(@RequestBody Film film) {
        Validator.filmValidation(film);
        if (!filmMap.containsKey(film.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film Not Found");
        }
        filmMap.put(film.getId(), film);
        log.info(film + " is put into db");
        return film;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(
            ValidationException exception
    ) {
        log.info(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
