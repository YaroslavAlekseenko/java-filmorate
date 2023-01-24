package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
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
    @NotBlank(message = "электронная почта не может быть пустой и должна содержать символ @")
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;
    private String name;
    @NotBlank(message = "логин не может быть пустым и содержать пробелы")
    @Pattern(regexp = "^\\S*", message = "логин не может быть пустым и содержать пробелы")
    private String login;
    @Past(message = "дата рождения не может быть в будущем")
    private LocalDate birthday;
}
