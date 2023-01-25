package ru.yandex.practicum.filmorate.Controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

/** Класс UserController. Функции UserController:
 *создание пользователя — createUser;
 *обновление пользователя — updateUser;
 *получение списка всех пользователей — getAllUsers.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer idUser = 0;

    private Integer generateId() {
        idUser++;
        return idUser;
    }

    /**Получение списка всех пользователей.*/
    @GetMapping()
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    /**Cоздание пользователя.*/
    @PostMapping()
    public User createUser(@Valid @RequestBody User user) {
            checkUserName(user);
            int idUser = generateId();
            user.setId(idUser);
            users.put(idUser, user);
            log.info("Добавлен новый пользователь, {}", user);
            return user;
    }

    /**Обновление пользователя.*/
    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())){
            log.error("Такого пользователя не существует!, {}", user);
            throw new UserValidationException("Такого пользователя не существует!");
        } else {
            checkUserName(user);
            user.setId(user.getId());
            users.put(user.getId(), user);
            log.info("Пользователь обновлен - , {}", user);
            return user;
        }
    }

    /**Проверка наличия имени пользователя.*/
    private void checkUserName(User user){
        if (user.getName() == null || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
    }
}
