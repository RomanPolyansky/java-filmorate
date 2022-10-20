package ru.yandex.practicum.filmorate.system;

import org.apache.commons.lang3.StringUtils;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {

    public static void filmValidation(Film film) throws ValidationException {
        final int MAX_FILM_DESCRIPTION_LENGTH = 200;
        final LocalDate MIN_DATE = LocalDate.parse("1895-12-28");

        if (film.getDescription() == null) film.setDescription("");

        if (StringUtils.isBlank(film.getName())) throw new ValidationException("Film name is empty");
        if (film.getDescription().length() > MAX_FILM_DESCRIPTION_LENGTH)
            throw new ValidationException("Film description is too long.");
        if (film.getReleaseDate().isBefore(MIN_DATE))
            throw new ValidationException("Film release date is not valid");
        if (film.getDuration()<= 0) throw new ValidationException("Film duration is not valid");
    }

    public static void userValidation(User user) throws ValidationException {
        if (StringUtils.isBlank(user.getEmail()) || !user.getEmail().contains("@")) throw new ValidationException("Email is not valid");
        if (StringUtils.isBlank(user.getLogin()) || user.getLogin().contains(" ")) throw new ValidationException("Login is not valid");
        if (user.getName() == null || StringUtils.isBlank(user.getName())) user.setName(user.getLogin());
        if (user.getBirthday().isAfter(LocalDate.now())) throw new ValidationException("Birthday is not valid");
    }
}
