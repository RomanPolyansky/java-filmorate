package ru.yandex.practicum.filmorate.dbTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class filmDbTest extends FilmorateDbTests<FilmDbStorage, UserDbStorage>{

    @Autowired
    public filmDbTest(JdbcTemplate jdbcTemplate, FilmDbStorage storage1, UserDbStorage storage2) {
        super(jdbcTemplate, storage1, storage2);
    }

    @Test
    void testFilmDb() {
        storage1.add(film);
        assertEquals(1, storage1.getAll().size());
        storage2.add(user);
        storage1.addLike(1, 1);
        assertEquals(1, storage1.getAll().get(0).getRate());
        storage1.removeLike(1, 1);
        assertEquals(0, storage1.getAll().get(0).getRate());

    }
}
