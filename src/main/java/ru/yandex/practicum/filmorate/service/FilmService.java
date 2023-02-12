package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.StorageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/** Класс FilmService, который отвечает за операции с фильмами.*/
@Service
public class FilmService {
    InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    /**Получение всех фильмов.*/
    public List<Film> getFilms() {
        return inMemoryFilmStorage.getAll();
    }

    /**Получение фильма по id.*/
    public Film getFilmById(Integer id) {
        return inMemoryFilmStorage.getById(id);
    }

    /**Добавление фильма.*/
    public Film addFilm(Film film) {
        return inMemoryFilmStorage.add(film);
    }

    /**Обновление фильма.*/
    public void updateFilm(Film film) {
        inMemoryFilmStorage.update(film);
    }

    /**Добавление лайка.*/
    public void addLikeFilm(Integer filmId, Integer userId) {
        if (!inMemoryFilmStorage.getById(filmId).getUsersLikedFilm().contains(userId)) {
            inMemoryFilmStorage.getById(filmId).addLike(userId);
        }
    }

    /**Удаление лайка.*/
    public void deleteLikeFilm(Integer filmId, Integer userId) {
        positiveUser(userId);
        inMemoryFilmStorage.getById(filmId).deleteLike(userId);
    }

    /**Вывод популярных фильмов.*/
    public List<Film> getPopularFilms(Integer count) {
        Comparator<Film> filmComparator = (film1, film2) -> {
            if (film2.getRating().compareTo(film1.getRating()) == 0) {
                return film1.getName().compareTo(film2.getName());
            }
            return film2.getRating().compareTo(film1.getRating());
        };
        return inMemoryFilmStorage.getAll().stream()
                .sorted(filmComparator)
                .limit(count)
                .collect(Collectors.toList());
    }

    /**Проверка id пользователя.*/
    private void positiveUser(Integer userId) {
        if (userId < 1) {
            throw new StorageException("id пользователя должно быть положительным числом.");
        }
    }
}