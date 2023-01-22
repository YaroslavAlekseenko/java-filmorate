package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

/** Класс пользователя. Свойства model.User:
 *целочисленный идентификатор — id;
 *электронная почта — email;
 *логин пользователя — login;
 *имя для отображения — name;
 *дата рождения — birthday.
 */
@Data
public class User {
    private int id;
    private String email;
    private String name;
    private String login;
    private LocalDate birthday;
}
