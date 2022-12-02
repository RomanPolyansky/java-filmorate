package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.ReadWriteStorage;
import ru.yandex.practicum.filmorate.system.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("InMemory")
public class InMemoryUserStorage implements ReadWriteStorage<User> {
    final Map<Integer, User> userMap = new HashMap<>();
    int id = 1;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User add(User user) {
        Validator.userValidation(user);
        if (userMap.containsKey(user.getId())) {
            throw new ValidationException("to update film use POST method");
        }
        user.setId(id++);
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User change(User user) {
        Validator.userValidation(user);
        if (!userMap.containsKey(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(int id) {
        if (!userMap.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        } else {
            return userMap.get(id);
        }
    }
}
