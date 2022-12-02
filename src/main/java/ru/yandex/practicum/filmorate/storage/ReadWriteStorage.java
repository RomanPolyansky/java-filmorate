package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface ReadWriteStorage<T> extends ReadStorage<T> {
    List<T> getAll();
    T add(T user);
    T change(T user);
    T getById(int id);
}
