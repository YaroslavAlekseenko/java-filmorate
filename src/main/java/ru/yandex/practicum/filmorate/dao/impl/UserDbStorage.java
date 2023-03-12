package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDb;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class UserDbStorage implements UserDb {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer add(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");
        return simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue();
    }

    @Override
    public void update(User user) {
        String sqlQuery = "UPDATE USERS SET " +
                "USER_EMAIL = ?, USER_LOGIN = ?, USER_NAME = ? , USER_BIRTHDAY = ?" +
                "WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getEmail()
                , user.getLogin()
                , user.getName()
                , user.getBirthday()
                , user.getId());
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sqlQuery = "SELECT USER_ID, USER_EMAIL, USER_LOGIN, USER_NAME, USER_BIRTHDAY " +
                "FROM USERS WHERE USER_ID = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id));
    }

    @Override
    public List<User> findAll() {
        String sqlQuery = "SELECT USER_ID, USER_EMAIL, USER_LOGIN, USER_NAME, USER_BIRTHDAY FROM USERS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public boolean addRequestsFriendship(Integer sender, Integer recipient) {
        if (!findRequestsFriendship(sender, recipient)) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("FIRST_USER_ID", sender);
            map.put("SECOND_USER_ID", recipient);
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("FRIENDS")
                    .usingColumns("FIRST_USER_ID", "SECOND_USER_ID");
            return simpleJdbcInsert.execute(map) == 1;
        }
        return false;
    }

    @Override
    public List<Integer> findAllFriends(Integer idUser) {
        String sqlQuery = String.format("SELECT SECOND_USER_ID AS friends\n" +
                "FROM FRIENDS\n" +
                "WHERE FIRST_USER_ID = %d", idUser, idUser);
        return jdbcTemplate.queryForList(sqlQuery, Integer.class);
    }

    @Override
    public boolean deleteFriends(Integer idUser, Integer idFriend) {
        String sqlQuery = String.format("DELETE\n" +
                "FROM FRIENDS\n" +
                "WHERE FIRST_USER_ID = %d AND SECOND_USER_ID = %d", idUser, idFriend);
        return jdbcTemplate.update(sqlQuery) > 0;
    }

    private boolean findRequestsFriendship(Integer firstId, Integer secondId) {
        String sqlQuery = String.format("SELECT COUNT(*)\n" +
                "FROM FRIENDS\n" +
                "WHERE (FIRST_USER_ID = %d OR SECOND_USER_ID = %d)" +
                " AND (FIRST_USER_ID = %d OR SECOND_USER_ID = %d)", firstId, firstId, secondId, secondId);
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class) == 1;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User(resultSet.getString("USER_EMAIL")
                , resultSet.getString("USER_LOGIN")
                , resultSet.getString("USER_NAME")
                , resultSet.getDate("USER_BIRTHDAY").toLocalDate());
        user.setId(resultSet.getInt("USER_ID"));
        return user;
    }
}