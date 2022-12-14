package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.*;

@Component
public class FilmDaoImpl implements FilmDao {
    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getAll() {
        List<Film> films = new ArrayList<>();
        String sql = "select f.id from films f";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            films.add(getEntityById(rs.getInt("id")).get());
        }
        if (films.isEmpty()) {
            log.info("no films found");
            return Collections.emptyList();
        }
        log.info("returning {} films", films.size());
        return films;
    }

    @Override
    public Film addEntity(Film film) {
        int returnedId = (int) simpleSave(film);
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
    public Film changeEntity(Film film) throws EntityNotFoundException {
        int success = jdbcTemplate.update("UPDATE films SET name = ?, description = ?, release_date = ?, duration = ? " + "where id = ?", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId());
        if (success == 0) throw new EntityNotFoundException("Failed to change film with id: " + film.getId());
        resetMpaAndRating(film.getId());
        putGenres(film, film.getId());
        putMpa(film, film.getId());

        return getEntityById(film.getId()).get();
    }

    @Override
    public Optional<Film> getEntityById(int id) throws EntityNotFoundException {
        // выполняем запрос к базе данных.
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from films where id = ?", id);

        // обрабатываем результат выполнения запроса
        if (rowSet.next()) {
            Film film = makeFilm(rowSet);
            log.info("Найден фимльм: {} {}", film.getId(), film.getName());

            return Optional.of(film);
        } else {
            log.info("Фильм с идентификатором {} не найден.", id);
            throw new EntityNotFoundException("Фильм с идентификатором " + id + " не найден.");
        }
    }

    private Film makeFilm(SqlRowSet rowSet) {
        int id = rowSet.getInt("id");
        Mpa mpa = getMpa(id);
        List<Genre> genreList = getListOfGenre(id);
        int rate = getRate(id);

        return Film.builder()
                .id(rowSet.getInt("id"))
                .name(rowSet.getString("name"))
                .description(rowSet.getString("description"))
                .releaseDate(rowSet.getDate("release_date").toLocalDate())
                .duration(rowSet.getInt("duration"))
                .mpa(mpa)
                .genres(genreList)
                .rate(rate)
                .build();
    }

    private Mpa getMpa(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select m.* from film_mpa fm join mpa m on m.id = fm.mpa_id where fm.film_id = ?", id);
        if (rowSet.next()) {
            return Mpa.builder().id(rowSet.getInt("id")).name(rowSet.getString("name")).build();
        }
        return null;
    }

    private int getRate(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select count(fl.id) count from film_likes fl join films f on f.id = fl.film_id where fl.film_id = ?", id);
        if (rowSet.next()) {
            return rowSet.getInt("count");
        }
        return 0;
    }

    private List<Genre> getListOfGenre(int id) {
        List<Genre> genreList = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select DISTINCT  g.* from film_genre fg join genres g on g.id = fg.genre_id where fg.film_id = ?", id);

        while (rowSet.next()) {
            genreList.add(Genre.builder()
                    .id(rowSet.getInt("id"))
                    .name(rowSet.getString("name"))
                    .build());
        }
        return genreList;
    }

    private long simpleSave(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("films").usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(film.toMap()).longValue();
    }

    private void putMpa(Film film, int id) {
        if (film.getMpa() != null)
            jdbcTemplate.update("INSERT into film_mpa (film_id, mpa_id) values (?,?)", id, film.getMpa().getId());
    }

    private void putGenres(Film film, int id) {
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update("INSERT into film_genre (film_id, genre_id) values (?,?)", id, genre.getId());
            }
        }
    }

    private void resetMpaAndRating(int id) {
        jdbcTemplate.update("DELETE FROM film_genre WHERE film_id = ? ", id);
        jdbcTemplate.update("DELETE FROM film_mpa WHERE film_id = ? ", id);
    }

    @Override
    public void addLike(int filmId, int userId) {
        try {
            if (jdbcTemplate.update("INSERT into film_likes (film_id, like_user_id) values (?,?)", filmId, userId) == 0)
                throw new EntityNotFoundException("Nothing on id " + filmId + " and user id " + userId + " was found");
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Nothing on id " + filmId + " and user id " + userId + " was found");
        }
    }

    @Override
    public void removeLike(int filmId, int userId) {
        try {
            if (jdbcTemplate.update("DELETE FROM film_likes WHERE film_id = ? AND like_user_id = ?", filmId, userId) == 0)
                throw new EntityNotFoundException("Nothing on id " + filmId + " and user id " + userId + " was found");
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Nothing on id " + filmId + " and user id " + userId + " was found");
        }
    }
}