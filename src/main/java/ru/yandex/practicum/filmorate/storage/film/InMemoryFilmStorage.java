package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.system.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    final Map<Integer, Film> filmMap = new HashMap<>();
    int id = 1;

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(filmMap.values());
    }

    @Override
    public Film addFilm(Film film) {
        Validator.filmValidation(film);
        if (filmMap.containsKey(film.getId())) {
            throw new ValidationException("to update film use POST method");
        }
        film.setId(id++);
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film changeFilm(Film film) {
        Validator.filmValidation(film);
        if (!filmMap.containsKey(film.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film Not Found");
        }
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getFilmById(int id) {
        if (!filmMap.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film Not Found");
        } else {
            return filmMap.get(id);
        }
    }
}
