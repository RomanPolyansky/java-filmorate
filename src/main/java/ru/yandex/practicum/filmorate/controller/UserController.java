package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.system.LoginService;
import ru.yandex.practicum.filmorate.system.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    Map<Integer, User> userMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    int id = 1;

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен запрос GET /users");
        return new ArrayList<>(userMap.values());
    }

    @PostMapping(value = "/users")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        try {
            Validator.userValidation(user);
            LoginService.registerUser(user);
            if (userMap.containsKey(user.getId())) {
                throw new ValidationException("to update film use POST method");
            }
            user.setId(id++);
            userMap.put(user.getId(), user);
            log.info(user + " is put into db");
        } catch (ValidationException e) {
            log.info(e.getMessage());
        }
        return user;
    }

    @PutMapping(value = "/users")
    @ResponseBody
    public User changeUser(@RequestBody User user) {
        try {
            Validator.userValidation(user);
            LoginService.checkUser(user);
            if (!userMap.containsKey(user.getId())) {
                throw new ValidationException("id is not valid for update");
            }
            userMap.put(user.getId(), user);
            log.info(user + " is put into db");
        } catch (ValidationException e) {
            log.info(e.getMessage());
        }
        return user;
    }
}
