package ru.yandex.practicum.integrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.dao.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.impl.GenreDbStorage;
import ru.yandex.practicum.filmorate.dao.impl.UserDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmTests {

    final FilmDbStorage filmDbStorage;
    final GenreDbStorage genreDbStorage;
    final UserDbStorage userStorage;

    @BeforeEach
    void createdFilmForDB() {
        if (filmDbStorage.findAll().size() != 2) {
            List<Genre> genres = new ArrayList<>();
            genres.add(new Genre(2, genreDbStorage.findById(2)));
            Film film = new Film("Film1", "Description film1", LocalDate.parse("1999-01-01"),
                    87L, 4, new Mpa(1, "G"), genres);
            filmDbStorage.add(film);
            filmDbStorage.setGenre(1, 2);
            Film filmNext = new Film("Film2", "Description film2", LocalDate.parse("2020-01-01"),
                    75L, 0, new Mpa(2, "PG"), genres);
            filmDbStorage.add(filmNext);
            filmDbStorage.setGenre(2, 2);
        }
        if (userStorage.findAll().size() != 2) {
            User firstTestUser = new User("test1@yandex.ru", "User1", "Tester1", LocalDate.parse("1999-01-01"));
            userStorage.add(firstTestUser);
            User SecondTestUser = new User("test2@yandex.ru", "User2", "Tester2", LocalDate.parse("2000-01-01"));
            userStorage.add(SecondTestUser);
        }
    }

    @Test
    void testAddFilm() {
        checkFindFilmById(1);
        checkFindFilmById(2);
    }

    @Test
    void testUpgradeFilm() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(2, genreDbStorage.findById(2)));
        Film updateFilm = new Film("Film1", "updateTest", LocalDate.parse("1999-01-01"), 87L, 4, new Mpa(1, "G"), genres);
        updateFilm.setId(1);
        filmDbStorage.update(updateFilm);
        Optional<Film> filmDbStorageFilm = filmDbStorage.findById(1);
        assertThat(filmDbStorageFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("description", "updateTest")
                );
    }

    @Test
    void testFindFilm() {
        checkFindFilmById(1);
    }

    @Test
    void testFindAll() {
        List<Film> current = filmDbStorage.findAll();
        Assertions.assertEquals(2, current.size(), "Некорректное количество фильмов");
    }

    @Test
    void testDeleteGenreFilm() {
        assertTrue(filmDbStorage.deleteGenre(2, 2), "Жанр фильма не изменился");
        List<Genre> genres = new ArrayList<>();
        Optional<Film> filmDbStorageFilm = filmDbStorage.findById(2);
        assertThat(filmDbStorageFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("genres", genres)
                );
        filmDbStorage.setGenre(2, 2);
    }

    @Test
    void testAddLikeFilm() {
        assertTrue(filmDbStorage.addLike(1, 1), "Лайк не добавлен");
        filmDbStorage.deleteLike(1, 1);
    }

    @Test
    void testDeleteLike() {
        filmDbStorage.addLike(1, 1);
        assertTrue(filmDbStorage.deleteLike(1, 1), "Лайк не удален");
    }

    @Test
    void testListMostPopularFilms() {
        filmDbStorage.addLike(1, 1);
        List<Film> films = filmDbStorage.mostPopulars(1);
        Assertions.assertEquals(1, films.size(), "Размер списка фильмов не соответсвует");
        Optional<Film> filmDbStorageFilm = filmDbStorage.findById(1);
        assertThat(filmDbStorageFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("rateAndLikes", film.getRating() + 1)
                );
        films = filmDbStorage.mostPopulars(2);
        Assertions.assertEquals(2, films.size(), "Размер списка фильмов не соответсвует");
        filmDbStorageFilm = filmDbStorage.findById(2);
        assertThat(filmDbStorageFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("rateAndLikes", film.getRating())
                );
    }

    void checkFindFilmById(Integer idFilm) {
        Optional<Film> filmOptional = filmDbStorage.findById(idFilm);
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", idFilm)
                );
    }
}