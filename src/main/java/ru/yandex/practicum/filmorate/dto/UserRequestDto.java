package ru.yandex.practicum.filmorate.dto;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.ValidBirthDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class UserRequestDto {

    private int id;
    @Email
    private String email;
    @NotNull
    private String login;
    private String name;
    @ValidBirthDate
    private final LocalDate birthday;

    public UserRequestDto(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User toEntity() {
        if (name.isEmpty()) name = login;
        return User.builder()
                .id(id)
                .login(login)
                .email(email)
                .name(name)
                .birthday(birthday)
                .build();
    }
}
