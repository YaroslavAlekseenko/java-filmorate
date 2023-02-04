package ru.yandex.practicum.filmorate.TestController;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.Controller.FilmController;
import ru.yandex.practicum.filmorate.Exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestFilmValidation {
    Film film = new Film();
    FilmController filmController = new FilmController();

    @Test
    @DisplayName("Проверка валидации. Передаем фильм с некорректной датой релиза.")
    void testReleaseDate() {
        film.setDuration(136);
        film.setDescription("Мальчику с далекой планеты суждено изменить судьбу галактики.");
        film.setName("Звёздные войны: Эпизод 1 — Скрытая угроза");
        film.setReleaseDate(LocalDate.of(199, 5, 16));
        assertThrows(FilmValidationException.class, () -> filmController.addFilm(film));
    }
}
