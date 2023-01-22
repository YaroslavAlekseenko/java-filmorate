package ru.yandex.practicum.filmorate.TestController;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Controller.FilmController;
import ru.yandex.practicum.filmorate.Exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TestFilmValidation {
    Film film = new Film();
    FilmController filmController = new FilmController();

    @Test
    @DisplayName("Проверка валидации. Передаем фильм в соответствии с правилами валидации.")
    void testFilm() throws FilmValidationException {
        film.setDuration(136);
        film.setDescription("Мальчику с далекой планеты суждено изменить судьбу галактики.");
        film.setName("Звёздные войны: Эпизод 1 — Скрытая угроза");
        film.setReleaseDate(LocalDate.of(1999, 5, 16));
        filmController.addFilm(film);
        assertThrows(FilmValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    @DisplayName("Проверка валидации. Передаем фильм с пустым именем.")
    void testName() {
        film.setDuration(136);
        film.setDescription("Мальчику с далекой планеты суждено изменить судьбу галактики.");
        film.setName("");
        film.setReleaseDate(LocalDate.of(1999, 5, 16));
        assertThrows(FilmValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    @DisplayName("Проверка валидации. Передаем фильм с описанием превышающим 200 символов.")
    void testDescription() {
        film.setDuration(136);
        film.setDescription("Мальчику с далекой планеты суждено изменить судьбу галактики. " +
                "Мальчику с далекой планеты суждено изменить судьбу галактики." +
                "Мальчику с далекой планеты суждено изменить судьбу галактики." +
                "Мальчику с далекой планеты суждено изменить судьбу галактики." +
                "Мальчику с далекой планеты суждено изменить судьбу галактики." +
                "Мальчику с далекой планеты суждено изменить судьбу галактики." +
                "Мальчику с далекой планеты суждено изменить судьбу галактики.");
        film.setName("Звёздные войны: Эпизод 1 — Скрытая угроза");
        film.setReleaseDate(LocalDate.of(1999, 5, 16));
        assertThrows(FilmValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    @DisplayName("Проверка валидации. Передаем фильм с некорректной датой релиза.")
    void testReleaseDate() {
        film.setDuration(136);
        film.setDescription("Мальчику с далекой планеты суждено изменить судьбу галактики.");
        film.setName("Звёздные войны: Эпизод 1 — Скрытая угроза");
        film.setReleaseDate(LocalDate.of(199, 5, 16));
        assertThrows(FilmValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    @DisplayName("Проверка валидации. Передаем фильм с отрицательной продолжительностью.")
    void testDuration() {
        film.setDuration(-136);
        film.setDescription("Мальчику с далекой планеты суждено изменить судьбу галактики.");
        film.setName("Звёздные войны: Эпизод 1 — Скрытая угроза");
        film.setReleaseDate(LocalDate.of(1999, 5, 16));
        assertThrows(FilmValidationException.class, () -> filmController.addFilm(film));
    }
}
