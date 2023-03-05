package ru.yandex.practicum.filmorate.model;

import lombok.Data;

/** Класс MPA. Свойства model.Mpa:
 *целочисленный идентификатор — id;
 *название — name;
 */
@Data
public class Mpa {
    private Integer id;
    private String name;

    public Mpa(int id, String name) {
        this.id = id;
        this.name = name;
    }
}