package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.dao.ReadWriteEntityDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FilmDaoImpl implements ReadWriteEntityDao<Film> {
    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getAll() {
        String sql = "select * from films";
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        if (films.isEmpty()) {
            return Collections.emptyList();
        }
        return films;
    }

    @Override
    public Film addEntity(Film film) throws SQLException {

        Long returnedId = simpleSave(film);

        log.info("Новый фильм с идентификатором {} добавлен.", returnedId);

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from films where film_id = ?", returnedId);

        if (rowSet.next()) {
            return makeFilm(rowSet);
        }

        return null;
    }

    @Override
    public Film changeEntity(Film film) throws SQLException {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("UPDATE films " + "SET name = ?, description = ?, release_date = ?, duration = ? " + "where film_id = ?", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId());
        return makeFilm(rowSet);
    }

    @Override
    public Optional<Film> getEntityById(int id) throws SQLException {
        // выполняем запрос к базе данных.
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from films where film_id = ?", id);

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

    private Film makeFilm(ResultSet rs) throws SQLException {
        return Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("releaseDate").toLocalDate())
                .duration(rs.getInt("duration"))
                .build();
    }

    private Film makeFilm(SqlRowSet rowSet) {
        return Film.builder()
                .id(rowSet.getInt("film_id"))
                .name(rowSet.getString("name"))
                .description(rowSet.getString("description"))
                .releaseDate(rowSet.getDate("releaseDate").toLocalDate())
                .duration(rowSet.getInt("duration"))
                .build();
    }

    public long simpleSave(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue();
    }
}