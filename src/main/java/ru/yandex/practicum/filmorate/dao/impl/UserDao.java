package ru.yandex.practicum.filmorate.dao.impl;

import ru.yandex.practicum.filmorate.dao.ReadWriteEntityDao;
import ru.yandex.practicum.filmorate.model.User;

public interface UserDao extends ReadWriteEntityDao<User> {

    void removeFriend(int userId, int friendId);

    void addFriend(int userId, int friendId);
}
