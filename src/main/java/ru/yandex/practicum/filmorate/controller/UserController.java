package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

/** Класс UserController. Функции UserController:
 *Cоздание пользователя — addUser;
 *Обновление пользователя — updateUser;
 *Получение списка всех пользователей — getAllUsers;
 *Получение пользователя по id — getUserById;
 *Добавление друзей — putUserFriend;
 *Удаление друзей — deleteUserFriend;
 *Вывод списка друзей — getUserFriends;
 *Вывод списка общих друзей — getCommonUsersFriends.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**Получение списка всех пользователей.*/
    @GetMapping()
    public List<User> getAllUsers() {
        log.info("Получены все пользователи.");
        return userService.getUsers();
    }

    /**Cоздание пользователя.*/
    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        checkUserName(user);
        log.info("Добавлен новый пользователь, {}", user);
        return userService.addUser(user);
    }

    /**Обновление пользователя.*/
    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        checkUserName(user);
        log.info("Пользователь обновлен - , {}", user);
        return userService.updateUser(user);
    }

    /**Получение пользователя по id.*/
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        log.info("Получен пользователь c id = , {}", id);
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    /**Добавление друзей.*/
    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> putUserFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Добавлен в друзья пользователь c id = , {}", friendId);
        userService.addFriend(id, friendId);
        return new ResponseEntity<>(userService.getUsers().get(id), HttpStatus.OK);
    }

    /**Удаление друзей.*/
    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> deleteUserFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Удалён из друзей пользователь c id = , {}", friendId);
        userService.deleteFriend(id, friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**Вывод списка друзей.*/
    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getUserFriends(@PathVariable Integer id) {
        log.info("Получены друзья пользователя c id = , {}", id);
        return new ResponseEntity<>(userService.getUserFriends(id), HttpStatus.OK);
    }

    /**Вывод списка общих друзей.*/
    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getCommonUsersFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Получены общие друзья с пользователем c id = , {}", otherId);
        return new ResponseEntity<>(userService.getCommonFriends(id, otherId), HttpStatus.OK);
    }

    /**Проверка наличия имени пользователя.*/
    private void checkUserName(User user){
        if (user.getName() == null || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
    }
}
