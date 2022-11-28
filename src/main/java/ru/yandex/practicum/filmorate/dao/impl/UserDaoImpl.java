package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReadWriteEntityDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.util.*;

@Component
public class UserDaoImpl implements UserDao {

    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        String sql = "select * from users";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public User addEntity(User user) throws SQLException {

        Long returnedId = simpleSave(user);

        log.info("Новый пользователь с идентификатором {} добавлен.", returnedId);

        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where id = ?", returnedId);

        if (userRows.next()) {
            return makeUser(userRows);
        }
        return null;
    }

    @Override
    public User changeEntity(User user) throws SQLException {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("UPDATE users " + "SET email = ?, login = ?, name = ?, birthday = ? " + "where id = ?", user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return makeUser(userRows);
    }

    @Override
    public Optional<User> getEntityById(int id) throws SQLException {
        // выполняем запрос к базе данных.
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where id = ?", id);

        // обрабатываем результат выполнения запроса
        if (userRows.next()) {
            User user = makeUser(userRows);
            log.info("Найден пользователь: {} {}", user.getId(), user.getLogin());

            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    private User makeUser(ResultSet rs) throws SQLException {
        return User.builder().id(rs.getInt("id")).email(rs.getString("email")).login(rs.getString("login")).name(rs.getString("name")).birthday(rs.getDate("birthday").toLocalDate()).build();
    }

    private User makeUser(SqlRowSet rowSet) throws SQLException {
        return User.builder().id(rowSet.getInt("id")).email(rowSet.getString("email")).login(rowSet.getString("login")).name(rowSet.getString("name")).birthday(rowSet.getDate("birthday").toLocalDate()).build();
    }

    public long simpleSave(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();
    }
}
