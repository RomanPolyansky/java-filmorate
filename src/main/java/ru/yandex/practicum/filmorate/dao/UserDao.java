package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getUsers();
    User addUser(User user);
    User changeUser(User user);
    Optional<User> getUserById(int id);
}
