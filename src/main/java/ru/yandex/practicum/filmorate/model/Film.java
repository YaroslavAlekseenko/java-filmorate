package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "название не может быть пустым")
    private String name;
    @NotNull
    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive(message = "продолжительность фильма должна быть положительной")
    private int duration;
}
