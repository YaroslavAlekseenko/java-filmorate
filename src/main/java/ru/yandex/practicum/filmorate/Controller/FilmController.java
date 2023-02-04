package ru.yandex.practicum.filmorate.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

/**Класс FilmController. Функции FilmController:
 *добавление фильма — addFilm;
 *обновление фильма — updateFilm;
 *получение всех фильмов — getFilms.
 */
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer idFilm = 0;
    private static final LocalDate validDate = LocalDate.of(1895, 12, 28);

    private Integer generateId() {
        idFilm++;
        return idFilm;
    }

    /**Получение всех фильмов.*/
    @GetMapping()
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    /**Добавление фильма.*/
    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
            checkFilmDate(film);
            int idFilm = generateId();
            film.setId(idFilm);
            films.put(idFilm, film);
            log.info("Добавлен новый фильм, {}", film);
            return film;
    }

    /**Обновление фильма.*/
    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())){
            log.error("Такого фильма не существует!, {}", film);
            throw new FilmValidationException("Такого фильма не существует!");
        } else {
            checkFilmDate(film);
            films.put(film.getId(), film);
            log.info("Фильм обновлен - , {}", film);
            return film;
        }
    }

    /**Проверка даты релиза фильма.*/
    private void checkFilmDate(Film film){
        if  (film.getReleaseDate().isBefore(validDate)){
            log.error("Дата релиза не должна быть раньше 28 декабря 1895!, {}", film);
            throw new FilmValidationException("Дата релиза не должна быть раньше 28 декабря 1895!");
        }
    }
}
