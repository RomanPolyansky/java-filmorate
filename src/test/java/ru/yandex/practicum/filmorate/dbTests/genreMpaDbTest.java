package ru.yandex.practicum.filmorate.dbTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.ReadStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class genreMpaDbTest extends FilmorateDbTests<ReadEntityDao<Genre>, ReadEntityDao<Mpa>>{

    @Autowired
    public genreMpaDbTest(JdbcTemplate jdbcTemplate, ReadEntityDao<Genre> storage1, ReadEntityDao<Mpa> storage2) {
        super(jdbcTemplate, storage1, storage2);
    }

    @Test
    void testGenreDb() {
        assertEquals(6, storage1.getAll().size());
        assertEquals("Комедия", storage1.getEntityById(1).get().getName());
    }

    @Test
    void testMpaDb() {
        assertEquals(5, storage2.getAll().size());
        assertEquals("G", storage2.getEntityById(1).get().getName());
    }
}
