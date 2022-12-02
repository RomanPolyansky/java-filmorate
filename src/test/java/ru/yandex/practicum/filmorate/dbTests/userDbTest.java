package ru.yandex.practicum.filmorate.dbTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class userDbTest extends FilmorateDbTests<FilmDbStorage, UserDbStorage>{

    @Autowired
    public userDbTest(JdbcTemplate jdbcTemplate, FilmDbStorage storage1, UserDbStorage storage2) {
        super(jdbcTemplate, storage1, storage2);
    }

    @Test
    void testUserDb() {
        storage2.add(user);
        assertEquals(1, storage2.getAll().size());
        assertEquals("test", storage2.getAll().get(0).getLogin());
        storage2.add(user);
        assertEquals(2, storage2.getAll().size());
        storage2.addFriend(1,2);
        assertEquals(1, storage2.getAll().get(0).getFriends().size());
        storage2.removeFriend(1,2);
        assertEquals(0, storage2.getAll().get(0).getFriends().size());
    }
}
