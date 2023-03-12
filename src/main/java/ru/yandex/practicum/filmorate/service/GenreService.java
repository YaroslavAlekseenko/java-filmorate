package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDb;
import ru.yandex.practicum.filmorate.dao.impl.GenreDbStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Service
public class GenreService {
    final GenreDb genreDbStorage;

    @Autowired
    public GenreService(GenreDbStorage genreDbStorage){
        this.genreDbStorage = genreDbStorage;
    }

    /**Получение всех жанров.*/
    public List<Genre> getAll(){
        return genreDbStorage.findAll();
    }

    /**Получение жанров по id фильма.*/
    public List<Genre> getGenresId(Integer id){
        return genreDbStorage.getGenres(id);
    }

    /**Получение жанра по id.*/
    public Genre getById(Integer id){
        return new Genre(id, genreDbStorage.findById(id));
    }
}