package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(int userId, int friendId) {
        User user = userStorage.getUserById(userId);
        userStorage.getUserById(friendId).getFriendsIdsSet().add(userId);
        user.getFriendsIdsSet().add(friendId);
        return user;
    }

    public User removeFriend(int userId, int friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        if (!user.getFriendsIdsSet().remove(friendId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend Not Found");
        userStorage.getUserById(friendId).getFriendsIdsSet().remove(userId);
        return user;
    }

    public List<User> getCommonFriends(int userId, int commonId) {
        User user = userStorage.getUserById(userId);
        User other = userStorage.getUserById(commonId);

        List<User> commonUsers = new ArrayList<>();
        for (Integer id : user.getFriendsIdsSet()) {
            if (other.getFriendsIdsSet().contains(id)) commonUsers.add(userStorage.getUserById(id));
        }
        return commonUsers;
    }

    public List<User> getFriends(int id) {
        List<User> listOfFriends = new ArrayList<>();
        for (Integer friendId : userStorage.getUserById(id).getFriendsIdsSet())
            listOfFriends.add(userStorage.getUserById(friendId));
        return listOfFriends;
    }
}
