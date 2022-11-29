package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("DataBase") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User getUserById(int id) {
        return userStorage.getById(id);
    }

    public List<User> getUsers() {
        return userStorage.getAll();
    }

    public User addUser(User user) {
        return userStorage.add(user);
    }

    public User changeUser(User user) {
        return userStorage.change(user);
    }

    public User addFriend(int userId, int friendId) {
        userStorage.addFriend(userId, friendId);
        return getUserById(userId);
    }

    public User removeFriend(int userId, int friendId) {
        userStorage.removeFriend(userId, friendId);
        return getUserById(userId);
    }

    public List<User> getCommonFriends(int userId, int commonId) {
        User user = getUserById(userId);
        User other = getUserById(commonId);

        List<User> commonUsers = new ArrayList<>();
        for (User user1 : user.getFriends()) {
            for (User user2 : other.getFriends()) {
                if (user1.equals(user2)) commonUsers.add(user1);
            }
        }
        return commonUsers;
    }

    public List<User> getFriends(int id) {
        return new ArrayList<>(getUserById(id).getFriends());
    }
}
