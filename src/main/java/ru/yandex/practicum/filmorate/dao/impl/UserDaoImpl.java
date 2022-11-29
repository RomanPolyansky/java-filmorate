package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
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
        List<User> users = new ArrayList<>();
        String sql = "select u.id from users u";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            users.add(getEntityById(rs.getInt("id")).get());
        }
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public User addEntity(User user) {
        Long returnedId = simpleSave(user);
        log.info("Новый пользователь с идентификатором {} добавлен.", returnedId);
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where id = ?", returnedId);
        if (userRows.next()) {
            return makeUser(userRows);
        }
        return null;
    }

    @Override
    public User changeEntity(User user) throws EntityNotFoundException {
        int success = jdbcTemplate.update("UPDATE users SET email = ?, login = ?, name = ?, birthday = ? " + "where id = ?", user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        if (success > 0) return getEntityById(user.getId()).get();
        throw new EntityNotFoundException("Failed to change film with id: " + user.getId());
    }

    @Override
    public Optional<User> getEntityById(int id) throws EntityNotFoundException {
        // выполняем запрос к базе данных.
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where id = ?", id);

        // обрабатываем результат выполнения запроса
        if (userRows.next()) {
            User user = makeUser(userRows);
            log.info("Найден пользователь: {} {}", user.getId(), user.getLogin());

            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            throw new EntityNotFoundException("Пользователь с идентификатором " + id + " не найден.");
        }
    }

    private User makeUser(SqlRowSet rowSet) {
        Set<User> friends = getFriends(rowSet.getInt("id"));
        return User.builder()
                .id(rowSet.getInt("id"))
                .email(rowSet.getString("email"))
                .login(rowSet.getString("login"))
                .name(rowSet.getString("name"))
                .birthday(rowSet.getDate("birthday").toLocalDate())
                .friends(friends)
                .build();
    }

    private Set<User> getFriends(int id) {
        Set<User> friends = new HashSet<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select fr.friend_id friend_id from friendships fr join users u on u.id = fr.user_id where fr.user_id = ?", id);
        while (rs.next()) {
            friends.add(getEntityById(rs.getInt("friend_id")).get());
        }
        return friends;
    }

    public long simpleSave(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();
    }

    @Override
    public void addFriend(int userId, int friendId) {
        try {
            if (jdbcTemplate.update("INSERT into friendships (user_id, friend_id) values (?,?)", userId, friendId) == 0)
                throw new EntityNotFoundException("Nothing on user id " + userId + " and friend id " + friendId + " was found");
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Nothing on user id " + userId + " and friend id " + friendId + " was found");
        }
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        try {
            if (jdbcTemplate.update("DELETE FROM friendships WHERE user_id = ? AND friend_id = ?", userId, friendId) == 0)
                throw new EntityNotFoundException("Nothing on userId " + userId + " and friendId " + friendId + " was found");
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Nothing on userId " + userId + " and friendId " + friendId + " was found");
        }
    }
}
