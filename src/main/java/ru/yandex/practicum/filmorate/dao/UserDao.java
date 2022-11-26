package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getUsers();
    User addUser(User user) throws SQLException;
    User changeUser(User user) throws SQLException;
    Optional<User> getUserById(int id);
}
