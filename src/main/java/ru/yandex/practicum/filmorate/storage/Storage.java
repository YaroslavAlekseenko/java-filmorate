package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Storage<T> {

    List<T> getAll();

    T getById(Integer id);

    T add(T data);

    T update(T data);
}
