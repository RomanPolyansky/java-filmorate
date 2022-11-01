package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film {
    private int id;
    private final String name;
    private String description;
    private final LocalDate releaseDate;
    private final int duration;
    private final Set<Integer> likeUserIds = new HashSet<>();
    private Set<String> genre;
    private String rating;
}
