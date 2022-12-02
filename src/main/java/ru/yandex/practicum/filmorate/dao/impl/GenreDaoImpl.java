package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class GenreDaoImpl implements ReadEntityDao<Genre> {

    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() throws EntityNotFoundException {
        List<Genre> genres = new ArrayList<>();
        String sql = "select g.id from genres g";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            genres.add(getEntityById(rs.getInt("id")).get());
        }
        if (genres.isEmpty()) {
            return Collections.emptyList();
        }
        log.info("returning {} genres", genres.size());
        return genres;
    }

    @Override
    public Optional<Genre> getEntityById(int id) throws EntityNotFoundException {
        // выполняем запрос к базе данных.
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from genres where id = ?", id);

        // обрабатываем результат выполнения запроса
        if (rowSet.next()) {
            Genre genre = makeGenre(rowSet);
            log.info("Найден жанр: {} {}", genre.getId(), genre.getName());

            return Optional.of(genre);
        } else {
            log.info("Жанр с идентификатором {} не найден.", id);
            throw new EntityNotFoundException("Жанр с идентификатором " + id + " не найден.");
        }
    }


    private Genre makeGenre(SqlRowSet rs) {
        return Genre.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
