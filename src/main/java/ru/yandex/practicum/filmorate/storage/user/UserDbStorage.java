package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.impl.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
@Qualifier("DataBase")
public class UserDbStorage implements UserStorage {

    private final UserDao userDao;

    public UserDbStorage(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User add(User user) {
        return userDao.addEntity(user);
    }

    @Override
    public User change(User user) {
        return userDao.changeEntity(user);
    }

    @Override
    public User getById(int id) {
        return userDao.getEntityById(id).get();
    }

    @Override
    public void addFriend(int userId, int friendId) {
        userDao.addFriend(userId, friendId);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        userDao.removeFriend(userId, friendId);
    }
}
