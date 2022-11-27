package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        log.info("Получен запрос GET /users/{id}");
        return userService.getUserById(Integer.parseInt(id));
    }

    /*

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable String id, @PathVariable String friendId) {
        log.info("Получен запрос PUT /users/{id}/friends/{friendId}");
        return userService.addFriend(Integer.parseInt(id), Integer.parseInt(friendId));
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable String id, @PathVariable String friendId) {
        log.info("Получен запрос DELETE /users/{id}/friends/{friendId}");
        return userService.removeFriend(Integer.parseInt(id), Integer.parseInt(friendId));
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable String id) {
        log.info("Получен запрос GET /users/{id}/friends");
        return userService.getFriends(Integer.parseInt(id));
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User>  getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        log.info("Получен запрос GET /users/{id}/friends/common/{otherId}");
        return userService.getCommonFriends(Integer.parseInt(id), Integer.parseInt(otherId));
    }

     */

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен запрос GET /users");
        return userService.getUsers();
    }

    @PostMapping(value = "/users")
    @ResponseBody
    public User addUser(@Valid @RequestBody UserRequestDto dto) {
        log.info("Получен запрос POST /users");
        User user = dto.toEntity();
        return userService.addUser(user);
    }

    @PutMapping(value = "/users")
    @ResponseBody
    public User changeUser(@RequestBody User user) {
        log.info("Получен запрос PUT /users");
        return userService.changeUser(user);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(
            ValidationException exception
    ) {
        log.info(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
