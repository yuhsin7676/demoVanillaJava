package com.example.demoVanillaJava.service;

import com.example.demoVanillaJava.shared.dto.CreateUserDto;
import com.example.demoVanillaJava.shared.dto.UserDto;
import com.example.demoVanillaJava.spi.NonSelectDBConnector;
import com.example.demoVanillaJava.spi.SelectDBConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserService {

    SelectDBConnector selectDBConnector = new SelectDBConnector();
    NonSelectDBConnector nonSelectDBConnector = new NonSelectDBConnector();

    public List<UserDto> getAll() { // Вот тут обычно используется ORM
        try {
            String cmstr = "select id, login, name from \"user\"";
            String[][] result = selectDBConnector.tryConnect(cmstr);
            List<UserDto> users = new ArrayList<>();
            for (int row = 1; row < result.length; row++) {
                UserDto user = new UserDto();
                user.setId(Long.parseLong(result[row][0]));
                user.setLogin(result[row][1]);
                user.setName(result[row][2]);
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения пользователей");
        }
    }

    public void create(CreateUserDto createUserDto) { // Вот тут обычно используется ORM
        try {
            String cmstr = "INSERT INTO \"user\" " +
                    "(id, login, name) " +
                    "VALUES(?, ?, ?)";

            PreparedStatement ps = nonSelectDBConnector.getPreparedStatement(cmstr);
            ps.setLong(1, new Random().nextLong());
            ps.setString(2, createUserDto.getLogin());
            ps.setString(3, createUserDto.getName());
            nonSelectDBConnector.tryConnect(ps);

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания пользователя");
        }
    }

    public void update(UserDto updateUserDto) { // Вот тут обычно используется ORM
        try {
            String cmstr = "UPDATE \"user\" " +
                    "SET login = ?, " +
                    "\"name\" = ? " +
                    "WHERE id = ?;";

            PreparedStatement ps = nonSelectDBConnector.getPreparedStatement(cmstr);
            ps.setLong(3, updateUserDto.getId());
            ps.setString(1, updateUserDto.getLogin());
            ps.setString(2, updateUserDto.getName());
            nonSelectDBConnector.tryConnect(ps);

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления пользователя");
        }
    }

    public void delete(Long id) { // Вот тут обычно используется ORM
        try {
            String cmstr = "DELETE \"user\" WHERE id = ?;";
            PreparedStatement ps = nonSelectDBConnector.getPreparedStatement(cmstr);
            ps.setLong(1, id);
            nonSelectDBConnector.tryConnect(ps);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления пользователя");
        }
    }

}
