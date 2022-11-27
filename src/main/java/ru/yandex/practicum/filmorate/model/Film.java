package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Data
@Builder
public class Film {
    private int id;
    private final String name;
    private String description;
    private final LocalDate releaseDate;
    private final int duration;
    private final Set<Integer> likeUserIds;
    private Set<String> genre;
    private String rating;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", id);
        values.put("name", name);
        values.put("description", description);
        values.put("releaseDate", releaseDate);
        values.put("duration", duration);
        values.put("likeUserIds", likeUserIds);
        values.put("genre", genre);
        values.put("rating", rating);
        return values;
    }
}
