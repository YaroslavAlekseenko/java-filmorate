package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

/** Класс фильма. Свойства model.Film:
 *целочисленный идентификатор — id;
 *название — name;
 *описание — description;
 *дата релиза — releaseDate;
 *продолжительность фильма — duration.
 */
@Data
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}
