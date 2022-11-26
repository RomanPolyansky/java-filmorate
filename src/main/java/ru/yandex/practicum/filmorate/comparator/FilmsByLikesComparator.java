package ru.yandex.practicum.filmorate.comparator;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class FilmsByLikesComparator implements Comparator<Film> {

    @Override
    public int compare(Film item1, Film item2) {
        return 1; // Integer.compare(item2.getLikeUserIds().size(), item1.getLikeUserIds().size());
    }
}
