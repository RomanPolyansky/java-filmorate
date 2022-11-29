package ru.yandex.practicum.filmorate.dto;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.validator.ValidDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class FilmRequestDto {

    private int id;
    @NotBlank
    private final String name;
    private String description;
    @Max(200)
    private int descriptionLength;
    @ValidDate
    private final LocalDate releaseDate;
    @Min(0)
    private final int duration;
    private List<Genre> genres;
    @NotNull
    private Mpa mpa;
    private final int rate;

    public FilmRequestDto(int id, String name, String description, LocalDate releaseDate, int duration, List<Genre> genres, Mpa mpa, int rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        if (description == null) this.description = "";
        this.descriptionLength = this.description.length();
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.mpa = mpa;
        this.rate = rate;
    }

    public Film toEntity() {
        return Film.builder()
                .id(id)
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .genres(genres)
                .mpa(mpa)
                .rate(rate)
                .build();
    }
}
