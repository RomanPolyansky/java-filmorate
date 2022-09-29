package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

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
    private final Date releaseDate = Date.from(Instant.parse("1000-12-31T00:00:00.00Z"));
    @Builder.Default
    private final Duration duration = Duration.ZERO;
}
