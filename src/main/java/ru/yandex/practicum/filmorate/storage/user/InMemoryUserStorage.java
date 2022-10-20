package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.system.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage{
    final Map<Integer, User> userMap = new HashMap<>();
    int id = 1;

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User addUser(User user) {
        Validator.userValidation(user);
        if (userMap.containsKey(user.getId())) {
            throw new ValidationException("to update film use POST method");
        }
        user.setId(id++);
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User changeUser(User user) {
        Validator.userValidation(user);
        if (!userMap.containsKey(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(int id) {
        if (!userMap.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        } else {
            return userMap.get(id);
        }
    }
}
