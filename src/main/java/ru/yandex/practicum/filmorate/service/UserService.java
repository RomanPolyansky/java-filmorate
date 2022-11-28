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
        User user = userStorage.getById(userId);
        userStorage.getById(friendId).getFriendsIdsSet().add(userId);
        user.getFriendsIdsSet().add(friendId);
        return user;
    }

    public User removeFriend(int userId, int friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if (!user.getFriendsIdsSet().remove(friendId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend Not Found");
        userStorage.getById(friendId).getFriendsIdsSet().remove(userId);
        return user;
    }

    public List<User> getCommonFriends(int userId, int commonId) {
        User user = userStorage.getById(userId);
        User other = userStorage.getById(commonId);

        List<User> commonUsers = new ArrayList<>();
        for (Integer id : user.getFriendsIdsSet()) {
            if (other.getFriendsIdsSet().contains(id)) commonUsers.add(userStorage.getById(id));
        }
        return commonUsers;
    }

    public List<User> getFriends(int id) {
        List<User> listOfFriends = new ArrayList<>();
        for (Integer friendId : userStorage.getById(id).getFriendsIdsSet())
            listOfFriends.add(userStorage.getById(friendId));
        return listOfFriends;
    }
}
