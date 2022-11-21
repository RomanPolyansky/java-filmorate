package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User addUser(User user) {
        try {
            return userDao.addUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User changeUser(User user) {
        try {
            return userDao.changeUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*

    public User addFriend(int userId, int friendId) {
        User user = userDao.getUserById(userId);
        userDao.getUserById(friendId).getFriendsIdsSet().add(userId);
        user.getFriendsIdsSet().add(friendId);
        return user;
    }

    public User removeFriend(int userId, int friendId) {
        User user = userDao.getUserById(userId);
        User friend = userDao.getUserById(friendId);
        if (!user.getFriendsIdsSet().remove(friendId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend Not Found");
        userDao.getUserById(friendId).getFriendsIdsSet().remove(userId);
        return user;
    }

    public List<User> getCommonFriends(int userId, int commonId) {
        User user = userDao.getUserById(userId);
        User other = userDao.getUserById(commonId);

        List<User> commonUsers = new ArrayList<>();
        for (Integer id : user.getFriendsIdsSet()) {
            if (other.getFriendsIdsSet().contains(id)) commonUsers.add(userDao.getUserById(id));
        }
        return commonUsers;
    }

    public List<User> getFriends(int id) {
        List<User> listOfFriends = new ArrayList<>();
        for (Integer friendId : userDao.getUserById(id).getFriendsIdsSet())
            listOfFriends.add(userDao.getUserById(friendId));
        return listOfFriends;
    }

     */
}
