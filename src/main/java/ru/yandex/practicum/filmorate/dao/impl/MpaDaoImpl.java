package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static javax.swing.UIManager.getInt;
import static javax.swing.UIManager.getString;

@Component
public class MpaDaoImpl implements ReadEntityDao<Mpa> {

    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAll() throws SQLException {
        String sql = "select * from mpa";
        List<Mpa> mpaList = jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
        if (mpaList.isEmpty()) {
            return Collections.emptyList();
        }
        return mpaList;
    }

    @Override
    public Optional<Mpa> getEntityById(int id) throws SQLException {
        // выполняем запрос к базе данных.
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from mpa where id = ?", id);

        // обрабатываем результат выполнения запроса
        if (rowSet.next()) {
            Mpa mpa = makeMpa(rowSet);
            log.info("Найден mpa: {} {}", mpa.getId(), mpa.getName());

            return Optional.of(mpa);
        } else {
            log.info("Mpa с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        return Mpa.builder()
                .id(getInt("id"))
                .name(getString("name"))
                .build();
    }

    private Mpa makeMpa(SqlRowSet rs) throws SQLException {
        return Mpa.builder()
                .id(getInt("id"))
                .name(getString("name"))
                .build();
    }
}
