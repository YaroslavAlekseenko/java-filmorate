package ru.yandex.practicum.filmorate.TestController;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Controller.UserController;
import ru.yandex.practicum.filmorate.Exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TestUserValidation {
    User user = new User();
    UserController userController = new UserController();

    @Test
    @DisplayName("Проверка валидации. Передаем пользователя в соответствии с правилами валидации.")
    void testUser() throws UserValidationException {
        user.setName("Иван");
        user.setBirthday(LocalDate.of(1999, 5, 16));
        user.setLogin("kinoman123");
        user.setEmail("ivan.ivanov@yandex.ru");
        userController.createUser(user);
        assertThrows(UserValidationException.class, () -> userController.createUser(user));
    }

    @Test
    @DisplayName("Проверка валидации. Передаем пользователя без почтового ящика.")
    void testEmail() {
        user.setName("Иван");
        user.setBirthday(LocalDate.of(1999, 5, 16));
        user.setLogin("kinoman123");
        user.setEmail("");
        assertThrows(UserValidationException.class, () -> userController.createUser(user));
    }

    @Test
    @DisplayName("Проверка валидации. Передаем пользователя без логина.")
    void testLogin() {
        user.setName("Иван");
        user.setBirthday(LocalDate.of(1999, 5, 16));
        user.setLogin(" ");
        user.setEmail("ivan.ivanov@yandex.ru");
        assertThrows(UserValidationException.class, () -> userController.createUser(user));
    }

    @Test
    @DisplayName("Проверка валидации. Передаем пользователя с некорректной датой рождения.")
    void testBirthday() {
        user.setName("Иван");
        user.setBirthday(LocalDate.of(19999, 5, 16));
        user.setLogin("kinoman123");
        user.setEmail("ivan.ivanov@yandex.ru");
        assertThrows(UserValidationException.class, () -> userController.createUser(user));
    }
}

