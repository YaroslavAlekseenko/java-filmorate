package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Component
public interface UserDb {
    Integer add(User user);

    void update(User user);

    Optional<User> findById(Integer id);

    List<User> findAll();

    boolean addRequestsFriendship(Integer idUser, Integer idFriend);

    boolean deleteFriends(Integer idUser, Integer idFriend);

    List<Integer> findAllFriends(Integer idUser);
}