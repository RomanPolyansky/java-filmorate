package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films/{id}")
    public Film getFilms(@PathVariable String id) {
        log.info("Получен запрос GET /films/{id}");
        return filmService.getFilmById(Integer.parseInt(id));
    }


    @PutMapping("/films/{id}/like/{userId}")
    public Film addLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}");
        return filmService.addLike(Integer.parseInt(id), Integer.parseInt(userId));
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film removeLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Получен запрос REMOVE /films/{id}/like/{userId}");
        return filmService.removeLike(Integer.parseInt(id), Integer.parseInt(userId));
    }

    @GetMapping("/films/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "0") String count) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}");
        return filmService.getTopFilms(Integer.parseInt(count));
    }


    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос GET /films");
        return filmService.getFilms();
    }

    @PostMapping(value = "/films")
    @ResponseBody
    public Film addFilm(@RequestBody Film film) {
        filmService.addFilm(film);
        log.info(film + " is put into db");
        return film;
    }

    @PutMapping(value = "/films")
    @ResponseBody
    public Film changeFilm(@RequestBody Film film) {
        filmService.changeFilm(film);
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
