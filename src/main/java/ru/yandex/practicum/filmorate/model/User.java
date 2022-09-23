package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@Builder
public class User {
    @Builder.Default
    private int id = 0;
    @Builder.Default
    private String email = "";
    @Builder.Default
    private final String login = "";
    @Builder.Default
    private String name = "";
    @Builder.Default
    private final Date birthday = Date.from(Instant.MIN);
}
