package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReadWriteEntityDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class FilmDaoImpl implements ReadWriteEntityDao<Film> {
    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getAll() {
        String sql = "select * from films";
        List<Film> films = jdbcTemplate.queryForList(sql, Film.class);
        // TODO ADD GENRE MPA
        if (films.isEmpty()) {
            return Collections.emptyList();
        }
        return films;
    }

    @Override
    public Film addEntity(Film film) throws SQLException {
        Long returnedId = simpleSave(film);
        log.info("Новый фильм с идентификатором {} добавлен.", returnedId);
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from films where id = ?", returnedId);
        putGenres(film, returnedId);
        putMpa(film, returnedId);
        if (rowSet.next()) {
            return makeFilm(rowSet);
        }
        return null;
    }

    @Override
    public Film changeEntity(Film film) throws SQLException {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("UPDATE films " + "SET name = ?, description = ?, release_date = ?, duration = ? " + "where id = ?", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId());
        return makeFilm(rowSet);
    }

    @Override
    public Optional<Film> getEntityById(int id) throws SQLException {
        // выполняем запрос к базе данных.
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from films where id = ?", id);

        // обрабатываем результат выполнения запроса
        if (rowSet.next()) {
            Film film = makeFilm(rowSet);
            log.info("Найден фимльм: {} {}", film.getId(), film.getName());

            return Optional.of(film);
        } else {
            log.info("Фильм с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    private Film makeFilm(SqlRowSet rowSet) {
        int id = rowSet.getInt("id");
        Mpa mpa = getMpa(id);

        return Film.builder()
                .id(rowSet.getInt("id"))
                .name(rowSet.getString("name"))
                .description(rowSet.getString("description"))
                .releaseDate(rowSet.getDate("release_date").toLocalDate())
                .duration(rowSet.getInt("duration"))
                .mpa(mpa)
                .build();
    }

    private Mpa getMpa(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select g.* from film_genre fg join genres g on g.id = fg.genre_id where fg.film_id = ?", id);
        if (rowSet.next()) {
            return Mpa.builder()
                    .id(rowSet.getInt("id"))
                    .name(rowSet.getString("name"))
                    .build();
        }
        return Mpa.builder()
                .id(-1)
                .name("")
                .build();
    }

    private long simpleSave(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue();
    }

    private void putMpa(Film film, Long id) {
        if (film.getMpa() != null) jdbcTemplate.update("INSERT into film_mpa (film_id, mpa_id) values (?,?)", id, film.getMpa().getId());
    }

    private void putGenres(Film film, Long id) {
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update("INSERT into film_genre (film_id, genre_id) values (?,?)", id, genre.getId());
            }
        }
    }
}