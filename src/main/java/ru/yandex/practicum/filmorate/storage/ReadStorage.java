package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface ReadStorage<T> {
    List<T> getAll();
    T getById(int id);
}
