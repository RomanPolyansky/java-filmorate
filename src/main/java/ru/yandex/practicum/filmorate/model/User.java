package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    @Builder.Default
    private int id = 0;
    @Builder.Default
    private String email = "";
    @Builder.Default
    private String login = "";
    @Builder.Default
    private String name = "";
    @Builder.Default
    private final LocalDate birthday = LocalDate.parse("1000-12-31");
}
