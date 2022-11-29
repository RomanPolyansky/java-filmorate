package ru.yandex.practicum.filmorate;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ru.yandex.practicum.filmorate.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.system.Validator;
import ru.yandex.practicum.filmorate.validator.ValidBirthDate;
import ru.yandex.practicum.filmorate.validator.ValidDate;

import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FilmorateApplication.class)
class FilmorateValidationTests {
    Film film;
    User user;

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    void filmDtoThrowsValidationExceptions() {
        FilmRequestDto filmRequestDto;
        Set<ConstraintViolation<FilmRequestDto>> cv;

        filmRequestDto = new FilmRequestDto(
                0 ,
                null,
                "123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899100101102103104105106107108109110111112113114115116117118119120121122123124125126127128129130131132133134135136137138139140141142143144145146147148149150151152153154155156157158159160161162163164165166167168169170171172173174175176177178179180181182183184185186187188189190191192193194195196197198199200",
                LocalDate.of(1000, 1, 1),
                -5,
                null,
                null,
                0
                );
        cv = validator.validate(filmRequestDto);
        cv.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
        Assert.assertTrue(cv.size() == 5);

        filmRequestDto = new FilmRequestDto(
                0 ,
                "null",
                "null",
                LocalDate.of(2000, 1, 1),
                50,
                List.of(Genre.builder().id(1).name("test").build()),
                Mpa.builder().id(1).name("test").build(),
                0
        );
        cv = validator.validate(filmRequestDto);
        Assert.assertTrue(cv.size() == 0);
    }

    @Test
    void userDtoThrowsValidationExceptions() {
        UserRequestDto userRequestDto;
        Set<ConstraintViolation<UserRequestDto>> cv;

        userRequestDto = new UserRequestDto(
                0 ,
                "notemail",
                null,
                "",
                LocalDate.of(3000, 1, 1)
        );

        cv = validator.validate(userRequestDto);
        cv.stream().map(v -> v.getMessage())
                .forEach(System.out::println);
        Assert.assertTrue(cv.size() == 3);

        userRequestDto = new UserRequestDto(
                0 ,
                "is@email.com",
                "null",
                "",
                LocalDate.of(2000, 1, 1)
        );
        cv = validator.validate(userRequestDto);
        Assert.assertTrue(cv.size() == 0);
    }


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