package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.StorageException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements Storage<Film> {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer idTask = 0;

    private Integer generateId() {
        idTask++;
        return idTask;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film add(Film film) {
        int idFilm = generateId();
        film.setId(idFilm);
        films.put(idFilm, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        checkId(film.getId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getById(Integer id) {
        checkId(id);
        return films.get(id);
    }

    private void checkId(Integer id) {
        if (!films.containsKey(id)) {
            throw new StorageException("Фильм с id = " + id + " отсутвует");
        }
    }
}
