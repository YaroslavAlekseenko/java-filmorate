package ru.yandex.practicum.filmorate.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**Класс FilmController. Функции FilmController:
 *добавление фильма — addFilm;
 *обновление фильма — updateFilm;
 *получение всех фильмов — getFilms.
 */
@RestController
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private Integer idFilm = 0;

    private Integer generateId() {
        idFilm++;
        return idFilm;
    }

    /**Получение всех фильмов.*/
    @GetMapping()
    public ArrayList<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    /**Добавление фильма.*/
    @PostMapping()
    public Film addFilm(@RequestBody Film film) throws FilmValidationException {
        if (films.containsKey(film.getId())){
            log.error("Фильм уже был добавлен!, {}", film);
            throw new FilmValidationException("Фильм уже был добавлен!");
        } else if (film.getName().isEmpty()){
            log.error("Название не может быть пустым!, {}", film);
            throw new FilmValidationException("Название не может быть пустым!");
        } else if (film.getDescription().length() > 200){
            log.error("Максимальная длина описания — 200 символов!, {}", film);
            throw new FilmValidationException("Максимальная длина описания — 200 символов!");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))){
            log.error("Дата релиза не должна быть раньше 28 декабря 1895!, {}", film);
            throw new FilmValidationException("Дата релиза не должна быть раньше 28 декабря 1895!");
        } else if (film.getDuration() <= 0){
            log.error("Продолжительность фильма должна быть положительна!, {}", film);
            throw new FilmValidationException("Продолжительность фильма должна быть положительна!");
        } else {
            int idFilm = generateId();
            film.setId(idFilm);
            films.put(idFilm, film);
            log.info("Добавлен новый фильм, {}", film);
            return film;
        }
    }

    /**Обновление фильма.*/
    @PutMapping()
    public Film updateFilm(@RequestBody Film film) throws FilmValidationException {
        if (!films.containsKey(film.getId())){
            log.error("Такого фильма не существует!, {}", film);
            throw new FilmValidationException("Такого фильма не существует!");
        } else {
            films.put(film.getId(), film);
            log.info("Фильм обновлен - , {}", film);
            return film;
        }
    }
}
