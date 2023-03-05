package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDb;
import ru.yandex.practicum.filmorate.exception.StorageException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    final FilmDb filmDbStorage;
    final MpaService mpaService;
    final GenreService genreService;

    @Autowired
    public FilmService(FilmDb filmDbStorage, MpaService mpaService,
                       GenreService genreService) {
        this.filmDbStorage = filmDbStorage;
        this.mpaService = mpaService;
        this.genreService = genreService;
    }

    /**Получение фильма по id.*/
    public Film getById(Integer id) {
        if (id < 9900) {
            var result = filmDbStorage.findById(id).orElseThrow(() -> new StorageException("Фильм не найден."));
            result.setGenres(genreService.getGenresId(result.getId()));
            return result;
        } else {
            throw new StorageException("Пользователь не найден.");
        }
    }

    /**Добавление фильма.*/
    public Film add(Film film) {
        film.setId(filmDbStorage.add(film));
        film.setMpa(mpaService.getById(film.getMpa().getId()));
        List<Genre> actualGenreFilm = new ArrayList<>();
        for (Genre genre : film.getGenres()) {
            actualGenreFilm.add(genreService.getById(genre.getId()));
            if (!filmDbStorage.setGenre(film.getId(), genre.getId())) {
                throw new StorageException("Не удалось установить жанр для фильма.");
            }
        }
        film.setGenres(actualGenreFilm);
        return film;
    }

    /**Обновление фильма.*/
    public void update(Film film) {
        getById(film.getId());
        filmDbStorage.update(film);
        film.setMpa(mpaService.getById(film.getMpa().getId()));
        List<Genre> actualGenreFilm = new ArrayList<>();
        for (Genre genre : film.getGenres()) {
            if (!actualGenreFilm.contains(genreService.getById(genre.getId()))) {
                actualGenreFilm.add(genreService.getById(genre.getId()));
            }
            if (!filmDbStorage.setGenre(film.getId(), genre.getId())) {
                throw new StorageException("Не удалось установить жанр для фильма.");
            }
        }
        List<Genre> currentGenreFilm = genreService.getGenresId(film.getId());
        for (Genre current : currentGenreFilm) {
            if (!actualGenreFilm.contains(current)) {
                filmDbStorage.deleteGenre(film.getId(), current.getId());
            }
        }
        film.setGenres(actualGenreFilm);
    }

    /**Получение всех фильмов.*/
    public List<Film> getAll() {
        var result = filmDbStorage.findAll();
        for (Film film : result) {
            film.setGenres(genreService.getGenresId(film.getId()));
        }

        return result;
    }

    /**Добавление лайка.*/
    public void addLike(Integer filmId, Integer userId) {
        validateIsPositive(userId);
        if (!filmDbStorage.addLike(filmId, userId)) {
            throw new StorageException("Лайк не поставлен.");
        }
    }

    /**Удаление лайка.*/
    public void deleteLike(Integer idFilm, Integer userId) {
        validateIsPositive(userId);
        if (!filmDbStorage.deleteLike(idFilm, userId)) {
            throw new StorageException("Лайк не удалён.");
        }
    }

    /**Вывод популярных фильмов.*/
    public List<Film> getPopularFilms(Integer count) {
        return filmDbStorage.mostPopulars(count);
    }

    /**Проверка id пользователя.*/
    private void validateIsPositive(Integer userId) {
        if (userId < 1) {
            throw new StorageException("id пользователя должно быть положительным числом.");
        }
    }
}
