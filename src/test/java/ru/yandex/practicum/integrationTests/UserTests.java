package ru.yandex.practicum.integrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.dao.impl.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserTests {

    final UserDbStorage userStorage;

    @BeforeEach
    void createdUserForDB() {
        if (userStorage.findAll().size() != 2) {
            User firstTestUser = new User("test1@yandex.ru", "User1", "Tester1", LocalDate.parse("1999-01-01"));
            userStorage.add(firstTestUser);
            User SecondTestUser = new User("test2@yandex.ru", "User2", "Tester2", LocalDate.parse("2000-01-01"));
            userStorage.add(SecondTestUser);
        }
        userStorage.deleteFriends(1, 2);
    }

    @Test
    void testCreatedUser() {
        checkFindUserById(1);
        checkFindUserById(2);
    }

    @Test
    void testFindAll() {
        List<User> currentList = userStorage.findAll();
        assertEquals(2, currentList.size(), "Некорректное количество пользователей");
    }

    @Test
    void testUpgradeUser() {
        User updateUser = new User("updateUser@yandex.ru",
                "updateUser",
                "UpdateName",
                LocalDate.parse("2000-10-10"));
        updateUser.setId(1);
        userStorage.update(updateUser);
        Optional<User> userStorageUser = userStorage.findById(1);
        Map<String, Object> mapForCheck = new HashMap<>();
        mapForCheck.put("id", updateUser.getId());
        mapForCheck.put("email", updateUser.getEmail());
        mapForCheck.put("login", updateUser.getLogin());
        mapForCheck.put("name", updateUser.getName());
        mapForCheck.put("birthday", updateUser.getBirthday());
        for (Map.Entry<String, Object> entry : mapForCheck.entrySet()) {
            assertThat(userStorageUser)
                    .isPresent()
                    .hasValueSatisfying(user ->
                            assertThat(user).hasFieldOrPropertyWithValue(entry.getKey(), entry.getValue())
                    );
        }
    }

    @Test
    void testFindUserById() {
        checkFindUserById(1);
    }

    @Test
    void testAddRequestsFriendship() {
        assertTrue(userStorage.addRequestsFriendship(1, 2), "Запрос на дружбу не отправлен");
        assertFalse(userStorage.addRequestsFriendship(1, 2), "Запрос на дружбу не должен быть отправлен");
    }

    @Test
    void testDeleteFriends() {
        userStorage.addRequestsFriendship(1, 2);
        assertTrue(userStorage.deleteFriends(1, 2), "Запрос на дружбу не удален");
        assertFalse(userStorage.deleteFriends(1, 2), "Запрос на дружбу не должен быть удален");
    }

    @Test
    void testFindAllFriends() {
        userStorage.addRequestsFriendship(1, 2);
        List<Integer> listFriendIdOne = userStorage.findAllFriends(1);
        assertEquals(1, listFriendIdOne.size(), "В списке друзей должен быть 1 друг");
        assertEquals(2, (int) listFriendIdOne.get(0), "Значение ID друга должно равнятся 2");
        List<Integer> listFriendIdTwo = userStorage.findAllFriends(2);
        assertEquals(0, listFriendIdTwo.size(), "Список друзей должен быть пуст");
    }

    void checkFindUserById(Integer idUser) {
        Optional<User> userStorageById = userStorage.findById(idUser);
        assertThat(userStorageById)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", idUser)
                );
    }
}