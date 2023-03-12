package ru.yandex.practicum.filmorate.model;

import lombok.Data;

/** Класс жанра. Свойства model.Genre:
 *целочисленный идентификатор — id;
 *название — name;
 */
@Data
public class Genre {
    private Integer id;
    private String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}