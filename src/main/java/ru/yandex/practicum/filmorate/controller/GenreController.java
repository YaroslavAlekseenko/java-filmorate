package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

/**Класс GenreController. Функции GenreController:
 *Получение списка жанров — getGenres;
 *Получение жанра по id — getGenreById.
 */
@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {
    final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**Получение списка жанров.*/
    @GetMapping()
    public List<Genre> getGenres() {
        log.info("Получены все жанры");
        return genreService.getAll();
    }

    /**Получение жанра по id.*/
    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Integer id) {
        log.info("Получен жанр c id = , {}", id);
        return new ResponseEntity<>(genreService.getById(id), HttpStatus.OK);
    }
}