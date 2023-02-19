package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;

/** Класс UserService, который отвечает за операции с пользователями.*/
@Service
@Slf4j
public class UserService {
    InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    /**Получение списка всех пользователей.*/
    public List<User> getUsers() {
        return inMemoryUserStorage.getAll();
    }

    /**Получение пользователя по id.*/
    public User getUserById(Integer id) {
        return inMemoryUserStorage.getById(id);
    }

    /**Добавление пользователя.*/
    public User addUser(User user) {
        return inMemoryUserStorage.add(user);
    }

    /**Обновление пользователя.*/
    public User updateUser(User user) {
        return inMemoryUserStorage.update(user);
    }

    /**Добавление друзей.*/
    public void addFriend(Integer idUser, Integer idFriend) {
        inMemoryUserStorage.getById(idUser).addFriend(idFriend);
        inMemoryUserStorage.getById(idFriend).addFriend(idUser);
    }

    /**Удаление друзей.*/
    public void deleteFriend(Integer idUser, Integer idFriend) {
        inMemoryUserStorage.getById(idUser).deleteFriend(idFriend);
        inMemoryUserStorage.getById(idFriend).deleteFriend(idUser);
    }

    /**Вывод списка друзей*/
    public List<User> getUserFriends(Integer idUser) {
        List<User> friends = new ArrayList<>();
        for (Integer friendId : inMemoryUserStorage.getById(idUser).getFriends()) {
            friends.add(inMemoryUserStorage.getById(friendId));
        }
        return friends;
    }

    /**Вывод списка общих друзей*/
    public List<User> getCommonFriends(Integer idUser, Integer idFriend) {
        List<User> commonFriend = new ArrayList<>();
        for (Integer idFriendUser : inMemoryUserStorage.getById(idUser).getFriends()) {
            if (inMemoryUserStorage.getById(idFriend).getFriends().contains(idFriendUser)) {
                commonFriend.add(inMemoryUserStorage.getById(idFriendUser));
            }
        }
        return commonFriend;
    }
}