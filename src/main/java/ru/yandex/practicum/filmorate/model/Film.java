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
 *Motion Picture Association, сокращённо МРА фильма — mpa;
 *жанр фильма — genres;
 *рейтинг фильма и лайки пользователей — rateAndLikes.
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
    private Long duration;
    private Integer rating;
    private Mpa mpa;
    private List<Genre> genres;
    private Integer rateAndLikes;

    public Film(String name, String description, LocalDate releaseDate, Long duration, Integer rate, Mpa mpa,
                List<Genre> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rating = Objects.requireNonNullElse(rate, 0);
        this.mpa = mpa;
        this.genres = Objects.requireNonNullElseGet(genres, ArrayList::new);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("FILM_NAME", name);
        values.put("FILM_DESCRIPTION", description);
        values.put("FILM_RELEASE_DATE", releaseDate);
        values.put("FILM_DURATION", duration);
        values.put("FILM_RATE", rating);
        values.put("MPA_ID", mpa.getId());
        values.put("FILM_RATE_AND_LIKES", rateAndLikes);
        return values;
    }
}
