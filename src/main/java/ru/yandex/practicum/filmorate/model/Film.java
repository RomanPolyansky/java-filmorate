package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Film {
    @Builder.Default
    private int id = 0;
    @Builder.Default
    private final String name = "";
    @Builder.Default
    private final String description = "";
    @Builder.Default
    private final LocalDate releaseDate = LocalDate.parse("1000-12-31");
    @Builder.Default
    private final int duration = 0;
}
