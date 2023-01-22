package ru.yandex.practicum.filmorate.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;

/** Класс UserController. Функции UserController:
 *создание пользователя — createUser;
 *обновление пользователя — updateUser;
 *получение списка всех пользователей — getAllUsers.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private static final HashMap<Integer, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private Integer idUser = 0;

    private Integer generateId() {
        idUser++;
        return idUser;
    }

    /**Получение списка всех пользователей.*/
    @GetMapping()
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    /**Cоздание пользователя.*/
    @PostMapping()
    public User createUser(@RequestBody User user) throws UserValidationException {
        if (users.containsKey(user.getId())){
            log.error("Такой пользователь уже существует!, {}", user);
            throw new UserValidationException("Такой пользователь уже существует!");
        } else if (user.getEmail().trim().equals("") || !user.getEmail().contains("@")){
            log.error("Электронная почта не может быть пустой и должна содержать символ - @, {}", user);
            throw new UserValidationException("Электронная почта не может быть пустой и должна содержать символ - @");
        } else if (user.getLogin().equals("") || user.getLogin().contains(" ")){
            log.error("Логин не может быть пустым и содержать пробелы!, {}", user);
            throw new UserValidationException("Логин не может быть пустым и содержать пробелы!");
        } else if (user.getBirthday().isAfter(LocalDate.now())){
            log.error("Дата рождения не может быть в будущем!, {}", user);
            throw new UserValidationException("Дата рождения не может быть в будущем!");
        } else if (user.getName() == null){
            user.setName(user.getLogin());
            int idUser = generateId();
            user.setId(idUser);
            users.put(idUser, user);
            log.info("Добавлен новый пользователь, {}", user);
            return user;
        } else {
            int idUser = generateId();
            user.setId(idUser);
            users.put(idUser, user);
            log.info("Добавлен новый пользователь, {}", user);
            return user;
        }
    }

    /**Обновление пользователя.*/
    @PutMapping()
    public User updateUser(@RequestBody User user) throws UserValidationException {
        if (!users.containsKey(user.getId())){
            log.error("Такого пользователя не существует!, {}", user);
            throw new UserValidationException("Такого пользователя не существует!");
        } else {
            if (user.getName().trim().equals("")){
                user.setName(user.getLogin());
            }
            user.setId(user.getId());
            users.put(user.getId(), user);
            log.info("Пользователь обновлен - , {}", user);
            return user;
        }
    }
}
