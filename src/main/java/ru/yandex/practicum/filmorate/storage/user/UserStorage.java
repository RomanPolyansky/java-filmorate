package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;

public interface UserStorage extends ReadWriteStorage<User> {

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);
}
