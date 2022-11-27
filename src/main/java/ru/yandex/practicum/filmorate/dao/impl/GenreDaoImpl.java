package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static javax.swing.UIManager.getInt;
import static javax.swing.UIManager.getString;

@Component
public class GenreDaoImpl implements ReadEntityDao<Genre> {

    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() throws SQLException {
        String sql = "select * from genres";
        List<Genre> genresList = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
        if (genresList.isEmpty()) {
            return Collections.emptyList();
        }
        return genresList;
    }

    @Override
    public Optional<Genre> getEntityById(int id) throws SQLException {
        // выполняем запрос к базе данных.
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from genres where id = ?", id);

        // обрабатываем результат выполнения запроса
        if (rowSet.next()) {
            Genre genre = makeGenre(rowSet);
            log.info("Найден жанр: {} {}", genre.getId(), genre.getName());

            return Optional.of(genre);
        } else {
            log.info("Жанр с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return Genre.builder()
                .id(getInt("id"))
                .name(getString("name"))
                .build();
    }

    private Genre makeGenre(SqlRowSet rs) throws SQLException {
        return Genre.builder()
                .id(getInt("id"))
                .name(getString("name"))
                .build();
    }
}
