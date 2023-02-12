package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.StorageException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer idTask = 0;

    private Integer generateId() {
        idTask++;
        return idTask;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getById(Integer id) {
        checkId(id);
        return users.get(id);
    }

    @Override
    public User add(User user) {
        int idUser = generateId();
        user.setId(idUser);
        users.put(idUser, user);
        return user;
    }

    @Override
    public User update(User user) {
        checkId(user.getId());
        users.put(user.getId(), user);
        return user;
    }

    private void checkId(Integer id){
        if (!users.containsKey(id)){
            throw new StorageException("Пользователь с id = " + id + " отсутвует");
        }
    }
}