package ru.yandex.practicum.filmorate.comparator;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class FilmsByLikesComparator implements Comparator<Film> {

    @Override
    public int compare(Film item1, Film item2) {
        return Integer.compare(item2.getRate(), item1.getRate());
    }
}
