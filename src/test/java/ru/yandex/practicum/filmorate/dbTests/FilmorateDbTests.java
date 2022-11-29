package ru.yandex.practicum.filmorate.dbTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
abstract class FilmorateDbTests <T, K> {
    public Film film = Film.builder()
            .name("test")
            .description("desc")
            .duration(50)
            .releaseDate(LocalDate.of(2000, 1, 1))
            .mpa(Mpa.builder().id(1).name("test").build())
            .genres(List.of(Genre.builder().id(1).name("test").build()))
            .build();

    public User user = User.builder()
            .email("is@email.com")
            .login("test")
            .birthday(LocalDate.of(2000, 1, 1))
            .build();

    public final JdbcTemplate jdbcTemplate;
    public final T storage1;
    public final K storage2;

    public FilmorateDbTests(JdbcTemplate jdbcTemplate, T storage1, K storage2) {
        this.jdbcTemplate = jdbcTemplate;
        this.storage1 = storage1;
        this.storage2 = storage2;
    }
}