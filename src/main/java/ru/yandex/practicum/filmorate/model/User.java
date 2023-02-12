package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.StorageException;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/** Класс пользователя. Свойства model.User:
 *целочисленный идентификатор — id;
 *электронная почта — email;
 *логин пользователя — login;
 *имя для отображения — name;
 *дата рождения — birthday;
 *список друзей — friends.
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
    @NotNull
    @PastOrPresent(message = "дата рождения не может быть в будущем")
    private LocalDate birthday;
    Set<Integer> friends = new HashSet<>();

    /**Добавление в список друзей.*/
    public void addFriend(Integer idFriend) {
        if (idFriend > 0) {
            friends.add(idFriend);
        } else {
            throw new StorageException("id пользователя должно быть положительным числом.");
        }
    }

    /**Удаление из списка друзей.*/
    public void deleteFriend(Integer idFriend) {
        if (idFriend > 0) {
            friends.remove(idFriend);
        } else {
            throw new StorageException("id пользователя должно быть положительным числом.");
        }
     }
}
