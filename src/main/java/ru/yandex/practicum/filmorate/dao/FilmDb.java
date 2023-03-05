package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Component
public interface FilmDb {
    Integer add(Film film);

    void update(Film film);

    Optional<Film> findById(Integer id);

    List<Film> findAll();

    boolean setGenre(Integer idFilm, Integer idGenre);

    boolean deleteGenre(Integer idFilm, Integer idGenre);

    boolean addLike(Integer idFilm, Integer idUser);

    List<Film> mostPopulars(Integer limit);

    boolean deleteLike(Integer idFilm, Integer idUser);
}