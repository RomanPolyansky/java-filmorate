package ru.yandex.practicum.filmorate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.system.Validator;

import java.time.LocalDate;

@SpringBootTest(classes = FilmorateApplication.class)
class FilmorateValidationTests {
    Film film;

    User user;

    @Test
    void emptyShouldGiveDefault() {
        film = Film.builder().build();
        assertEquals(0, film.getId());
        assertEquals("", film.getName());
        assertEquals("", film.getDescription());
        assertEquals(0, film.getDuration());
        assertEquals(LocalDate.parse("1000-12-31"), film.getReleaseDate());

        User user = User.builder().build();
        assertEquals(0, user.getId());
        assertEquals("", user.getName());
        assertEquals("", user.getEmail());
        assertEquals("", user.getLogin());
        assertEquals(LocalDate.parse("1000-12-31"), user.getBirthday());
    }

    @Test
    void filmValidationShouldThrowValidationExceptions() {
        Exception e;
        film = Film.builder().
                name("").
                releaseDate(LocalDate.parse("1894-12-28")).
                duration(-2).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.filmValidation(film));
        assertEquals("Film name is empty", e.getMessage());

        film = Film.builder().
                name("Test").
                releaseDate(LocalDate.parse("1894-12-28")).
                duration(-2).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.filmValidation(film));
        assertEquals("Film release date is not valid", e.getMessage());

        film = Film.builder().
                name("Test").
                releaseDate(LocalDate.parse("1900-12-28")).
                duration(-2).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.filmValidation(film));
        assertEquals("Film duration is not valid", e.getMessage());
    }

    @Test
    void userValidationShouldThrowValidationExceptions() {
        Exception e;
        user = User.builder().
                email("test").
                login("testing").
                birthday(LocalDate.now().plusDays(1)).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.userValidation(user));
        assertEquals("Email is not valid", e.getMessage());
        user = User.builder().
                email("test@test.test").
                login("").
                birthday(LocalDate.now().plusDays(1)).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.userValidation(user));
        assertEquals("Login is not valid", e.getMessage());

        user = User.builder().
                email("test@test.test").
                login("testing").
                birthday(LocalDate.now().plusDays(1)).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.userValidation(user));
        assertEquals("Birthday is not valid", e.getMessage());
    }
}