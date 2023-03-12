package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

/**Класс FilmController. Функции FilmController:
 *Добавление фильма — addFilm;
 *Обновление фильма — updateFilm;
 *Получение всех фильмов — getFilms;
 *Получение фильма по id — getFilmById;
 *Вывод популярных фильмов — getPopularFilms;
 *Добавление лайка — addLikeFilm;
 *Удаление лайка — deleteLikeFilm.
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    final FilmService filmService;
    private static final LocalDate VALID_DATE = LocalDate.of(1895, 12, 28);
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**Получение всех фильмов.*/
    @GetMapping()
    public List<Film> getFilms() {
        log.info("Получены все фильмы.");
        return filmService.getAll();
    }

    /**Добавление фильма.*/
    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        checkFilmDate(film);
        log.info("Добавлен новый фильм, {}", film);
        return filmService.add(film);
    }

    /**Обновление фильма.*/
    @PutMapping()
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        checkFilmDate(film);
        log.info("Обновлён фильм, {}", film);
        filmService.update(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    /**Получение фильма по id.*/
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        log.info("Получен фильм c id = , {}", id);
        return new ResponseEntity<>(filmService.getById(id), HttpStatus.OK);
    }

    /**Вывод популярных фильмов.*/
    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        log.info("Получены популярные фильмы.");
        return new ResponseEntity<>(filmService.getPopularFilms(count), HttpStatus.OK);
    }

    /**Добавление лайка.*/
    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> addLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Поставлен лайк фильму.");
        filmService.addLike(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**Удаление лайка.*/
    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> deleteLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Удалён лайк у фильма.");
        filmService.deleteLike(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**Проверка даты релиза фильма.*/
    private void checkFilmDate(Film film){
        if  (film.getReleaseDate().isBefore(VALID_DATE)){
            log.error("Дата релиза не должна быть раньше 28 декабря 1895!, {}", film);
            throw new FilmValidationException("Дата релиза не должна быть раньше 28 декабря 1895!");
        }
    }
}
