package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

/** Класс фильма. Свойства model.Film:
 *целочисленный идентификатор — id;
 *название — name;
 *описание — description;
 *дата релиза — releaseDate;
 *продолжительность фильма — duration;
 *рейтинг фильма — rating;
 *список пользователей поставивших лайк — usersLikedFilm.
 */
@Data
public class Film {
    private int id;
    @NotBlank(message = "название не может быть пустым")
    private String name;
    @NotNull
    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive(message = "продолжительность фильма должна быть положительной")
    private int duration;
    Integer rating;
    Set<Integer> usersLikedFilm = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, int duration, Integer rating) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        if (rating == null || rating < 0) {
            this.rating = 0;
        } else {
            this.rating = rating;
        }
    }

    /**Добавление лайка.*/
    public void addLike(Integer idUser) {
        usersLikedFilm.add(idUser);
        rating = rating + usersLikedFilm.size();
    }

    /**Удаление лайка.*/
    public void deleteLike(Integer idUser) {
        rating = rating - usersLikedFilm.size();
        usersLikedFilm.remove(idUser);
    }
}
