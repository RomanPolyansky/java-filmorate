package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReadEntityDao;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static javax.swing.UIManager.getInt;
import static javax.swing.UIManager.getString;

@Component
public class MpaDaoImpl implements ReadEntityDao<Mpa> {

    private final Logger log = LoggerFactory.getLogger(MpaDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAll() {
        List<Mpa> mpaList = new ArrayList<>();
        String sql = "select m.id from mpa m";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            mpaList.add(getEntityById(rs.getInt("id")).get());
        }
        if (mpaList.isEmpty()) {
            return Collections.emptyList();
        }
        return mpaList;
    }

    @Override
    public Optional<Mpa> getEntityById(int id)  {
        // выполняем запрос к базе данных.
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from mpa where id = ?", id);

        // обрабатываем результат выполнения запроса
        if (rowSet.next()) {
            Mpa mpa = makeMpa(rowSet);
            log.info("Найден mpa: {} {}", mpa.getId(), mpa.getName());

            return Optional.of(mpa);
        } else {
            log.info("Mpa с идентификатором {} не найден.", id);
            throw new EntityNotFoundException("Mpa с идентификатором " + id + " не найден.");
        }
    }

    private Mpa makeMpa(SqlRowSet rs) {
        return Mpa.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
