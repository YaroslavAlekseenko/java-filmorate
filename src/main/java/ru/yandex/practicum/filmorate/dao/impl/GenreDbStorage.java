package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDb;
import ru.yandex.practicum.filmorate.exception.StorageException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GenreDbStorage implements GenreDb {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String findById(Integer id) {
        log.info("GenreDbStorage. findById.");
        String sqlQuery = String.format("SELECT GENRE_NAME " +
                "FROM GENRE WHERE GENRE_ID = %d", id);
        List<String> names = jdbcTemplate.queryForList(sqlQuery, String.class);
        if (names.size() != 1) {
            throw new StorageException("Некорректный id жанра.");
        }
        return names.get(0);
    }

    @Override
    public List<Genre> getGenres(Integer idFilm) {
        log.info("FilmDbStorage. getGenres");
        String sqlQuery = String.format("SELECT GENRE_ID\n" +
                "FROM FILM_TO_GENRE\n" +
                "WHERE FILM_ID = %d", idFilm);
        List<Integer> idGenres = jdbcTemplate.queryForList(sqlQuery, Integer.class);
        List<Genre> genres = new ArrayList<>();
        for (Integer id : idGenres) {
            genres.add(new Genre(id,findById(id)));
        }
        return genres;
    }

    @Override
    public List<Genre> findAll() {
        log.info("GenreDbStorage. findAll.");
        String sqlQuery = "SELECT GENRE_ID, GENRE_NAME FROM GENRE";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        log.info("GenreDbStorage. mapRowToGenre.");
        return new Genre(resultSet.getInt("GENRE_ID")
                , resultSet.getString("GENRE_NAME"));
    }
}