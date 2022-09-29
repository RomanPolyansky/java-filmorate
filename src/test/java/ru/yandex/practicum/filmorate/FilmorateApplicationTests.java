package ru.yandex.practicum.filmorate;

import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.system.FilmorateApplication;
import ru.yandex.practicum.filmorate.system.Validator;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;

@SpringBootTest(classes = FilmorateApplication.class)
class FilmorateValidationTests {
    Film film;

    User user;

    @BeforeEach
    void setup() {

    }

    @Test
    void emptyShouldGiveDefault() {
        film = Film.builder().build();
        assertEquals(0, film.getId());
        assertEquals("", film.getName());
        assertEquals("", film.getDescription());
        assertEquals(Duration.ZERO, film.getDuration());
        assertEquals(java.util.Date.from(Instant.parse("1000-12-31T00:00:00.00Z")), film.getReleaseDate());

        User user = User.builder().build();
        assertEquals(0, user.getId());
        assertEquals("", user.getName());
        assertEquals("", user.getEmail());
        assertEquals("", user.getLogin());
        assertEquals(java.util.Date.from(Instant.parse("1000-12-31T00:00:00.00Z")), user.getBirthday());
    }

    @Test
    void filmValidationShouldThrowValidationExceptions() {
        Exception e;
        film = Film.builder().
                name("").
                releaseDate(Date.from(Instant.parse("1894-12-28T00:00:00.00Z"))).
                duration(Duration.ofHours(-2)).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.filmValidation(film));
        assertEquals("Film name is empty", e.getMessage());

        film = Film.builder().
                name("Test").
                releaseDate(Date.from(Instant.parse("1894-12-28T00:00:00.00Z"))).
                duration(Duration.ofHours(-2)).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.filmValidation(film));
        assertEquals("Film release date is not valid", e.getMessage());

        film = Film.builder().
                name("Test").
                releaseDate(Date.from(Instant.parse("1900-12-28T00:00:00.00Z"))).
                duration(Duration.ofHours(-2)).
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
                birthday(Date.from(Instant.now().plusSeconds(10))).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.userValidation(user));
        assertEquals("Email is not valid", e.getMessage());
        user = User.builder().
                email("test@test.test").
                login("").
                birthday(Date.from(Instant.now().plusSeconds(10))).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.userValidation(user));
        assertEquals("Login is not valid", e.getMessage());

        user = User.builder().
                email("test@test.test").
                login("testing").
                birthday(Date.from(Instant.now().plusSeconds(10))).
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.userValidation(user));
        assertEquals("Birthday is not valid", e.getMessage());
    }
}