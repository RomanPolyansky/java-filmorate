package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReadWriteEntityDao;
import ru.yandex.practicum.filmorate.dao.impl.UserDao;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("DataBase")
public class UserDbStorage implements UserStorage {

    private final UserDao userDao;

    public UserDbStorage(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAll() {
        try {
            return userDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User add(User user) {
        try {
            return userDao.addEntity(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User change(User user) {
        try {
            return userDao.changeEntity(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(int id) {
        try {
            Optional<User> user = userDao.getEntityById(id);
            if (user.isEmpty()) throw new EntityNotFoundException("Entity with id (" + ") not found");
            return user.get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
