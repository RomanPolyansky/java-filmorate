package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.yandex.practicum.filmorate.dao.impl.FilmDao;
import ru.yandex.practicum.filmorate.dao.impl.FilmDaoImpl;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTests {

    @AfterEach
    public void wipeOut() {
        jdbcTemplate.update("DELETE FROM film_genre");
        jdbcTemplate.update("DELETE FROM film_likes");
        jdbcTemplate.update("DELETE FROM film_mpa");
        jdbcTemplate.update("DELETE FROM films");
        jdbcTemplate.update("DELETE FROM friendships");
        jdbcTemplate.update("DELETE FROM genres");
        jdbcTemplate.update("DELETE FROM mpa");
        jdbcTemplate.update("DELETE FROM users");
    }

    @BeforeEach
    public void init() {
        jdbcTemplate.update("INSERT into genres (name) values ('Комедия');");
        jdbcTemplate.update("INSERT into genres (name) values ('Драма');");
        jdbcTemplate.update("INSERT into genres (name) values ('Мультфильм');");
        jdbcTemplate.update("INSERT into genres (name) values ('Триллер');");
        jdbcTemplate.update("INSERT into genres (name) values ('Документальный');");
        jdbcTemplate.update("INSERT into genres (name) values ('Боевик');");
        jdbcTemplate.update("INSERT into mpa (name) values ('G');");
        jdbcTemplate.update("INSERT into mpa (name) values ('PG');");
    }

    private final UserDbStorage userStorage;
    private final FilmStorage filmStorage;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void whenInjectInMemoryDataSource_thenReturnCorrectEmployeeCount() {

    }
}