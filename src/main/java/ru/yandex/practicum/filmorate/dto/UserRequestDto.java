package ru.yandex.practicum.filmorate.dto;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserRequestDto {

    private int id;
    @Email
    private String email;
    @NotNull
    private String login;
    private String name;
    @Min(0)
    private Long daysAfterBirthday;
    @NotNull
    private final LocalDate birthday;

    public UserRequestDto(int id, String email, String login, String name, int daysAfterBirthday, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.daysAfterBirthday = LocalDate.now().toEpochDay() - birthday.toEpochDay();
    }

    public User toEntity() {
        if (name.isEmpty()) name = login;
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .birthday(birthday)
                .build();
    }
}
