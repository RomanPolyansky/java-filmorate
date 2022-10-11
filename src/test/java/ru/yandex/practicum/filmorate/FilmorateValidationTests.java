package ru.yandex.practicum.filmorate;

import static org.junit.jupiter.api.Assertions.*;

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
    void filmValidationShouldThrowValidationExceptions() {
        Exception e;
        film = Film.builder().
                name("").
                releaseDate(LocalDate.parse("1894-12-28")).
                duration(-2).
                description("test").
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.filmValidation(film));
        assertEquals("Film name is empty", e.getMessage());

        film = Film.builder().
                name("Test").
                releaseDate(LocalDate.parse("1894-12-28")).
                duration(-2).
                description("test").
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.filmValidation(film));
        assertEquals("Film release date is not valid", e.getMessage());

        film = Film.builder().
                name("Test").
                releaseDate(LocalDate.parse("1894-12-28")).
                duration(-2).
                description("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890").
                build();
        e = assertThrows(ValidationException.class, () ->
                Validator.filmValidation(film));
        assertEquals("Film description is too long.", e.getMessage());

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