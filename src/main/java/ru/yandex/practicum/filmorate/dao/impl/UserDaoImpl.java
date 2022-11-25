package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserDaoImpl implements UserDao {

    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() throws DataAccessException {
        String sql = "select * from users";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public User addUser(User user) throws SQLException {
        final String INSERT_SQL = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"user_id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setDate(4, java.sql.Date.valueOf(user.getBirthday().toString()));
            return ps;
        }, keyHolder);

        Number newId = keyHolder.getKey();

        log.info("Новый пользователь с идентификатором {} добавлен.", keyHolder.getKey());

        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", keyHolder.getKey());

        if (userRows.next()) {
            return  User.builder().id(userRows.getInt("user_id")).email(userRows.getString("email")).login(userRows.getString("login")).name(userRows.getString("name")).birthday(userRows.getDate("birthday").toLocalDate()).build();
        }
        return null;
    }

    @Override
    public User changeUser(User user) throws SQLException {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("UPDATE users " + "SET email = ?, login = ?, name = ?, birthday = ? " + "where user_id = ?", user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return makeUser(userRows);
    }

    @Override
    public Optional<User> getUserById(int id) {
        // выполняем запрос к базе данных.
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", id);

        // обрабатываем результат выполнения запроса
        if (userRows.next()) {
            User user = User.builder().id(userRows.getInt("user_id")).email(userRows.getString("email")).login(userRows.getString("login")).name(userRows.getString("name")).birthday(userRows.getDate("birthday").toLocalDate()).build();

            log.info("Найден пользователь: {} {}", user.getId(), user.getLogin());

            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    private User makeUser(ResultSet rs) throws SQLException {
        return User.builder().id(rs.getInt("user_id")).email(rs.getString("email")).login(rs.getString("login")).name(rs.getString("name")).birthday(rs.getDate("birthday").toLocalDate()).build();
    }

    private User makeUser(SqlRowSet rowSet) throws SQLException {
        return User.builder().id(rowSet.getInt("user_id")).email(rowSet.getString("email")).login(rowSet.getString("login")).name(rowSet.getString("name")).birthday(rowSet.getDate("birthday").toLocalDate()).build();
    }
}
