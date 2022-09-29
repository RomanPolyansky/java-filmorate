package ru.yandex.practicum.filmorate.system;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginService {
    static List<String> loginsList = new ArrayList<>();
    public static void registerUser(User user) throws ValidationException {
        if (loginsList.contains(user.getLogin())) throw new ValidationException("Login already registered");
        loginsList.add(user.getLogin());
    }

    public static void checkUser(User user) throws ValidationException {
        if (!loginsList.contains(user.getLogin())) throw new ValidationException("Login is not found");
    }
}
