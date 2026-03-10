package com.example.demoVanillaJava.service;

import com.example.demoVanillaJava.shared.dto.CreateUserDto;
import com.example.demoVanillaJava.shared.dto.LoginDto;
import com.example.demoVanillaJava.shared.dto.UserDto;
import com.example.demoVanillaJava.spi.sql.NonSelectDBConnector;
import com.example.demoVanillaJava.spi.sql.SelectDBConnector;

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

    public UserDto getByLogin(LoginDto loginDto) { // Вот тут обычно используется ORM
        try {
            String cmstr = "select id, login, password, name from \"user\" where login = ? ";
            PreparedStatement ps = selectDBConnector.getPreparedStatement(cmstr);
            ps.setString(1, loginDto.getLogin());

            String[][] result = selectDBConnector.tryConnect(ps);
            if (result.length == 2) {
                UserDto user = new UserDto();
                user.setId(Long.parseLong(result[1][0]));
                user.setLogin(result[1][1]);
                user.setPassword(result[1][2]);
                user.setName(result[1][3]);
                return user;
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения пользователей");
        }
    }

    public void create(CreateUserDto createUserDto) { // Вот тут обычно используется ORM
        try {
            String cmstr = "INSERT INTO \"user\" " +
                    "(id, login, password, name) " +
                    "VALUES(?, ?, ?, ?)";

            PreparedStatement ps = nonSelectDBConnector.getPreparedStatement(cmstr);
            ps.setLong(1, new Random().nextLong());
            ps.setString(2, createUserDto.getLogin());
            ps.setString(3, AuthorizationService.getInstance().sha256Hash(createUserDto.getPassword()));
            ps.setString(4, createUserDto.getName());
            nonSelectDBConnector.tryConnect(ps);

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания пользователя");
        }
    }

    public void update(UserDto updateUserDto) { // Вот тут обычно используется ORM
        try {
            String cmstr = "UPDATE \"user\" " +
                    "SET login = ?, " +
                    "password = ?, " +
                    "\"name\" = ? " +
                    "WHERE id = ?;";

            PreparedStatement ps = nonSelectDBConnector.getPreparedStatement(cmstr);
            ps.setLong(4, updateUserDto.getId());
            ps.setString(1, updateUserDto.getLogin());
            ps.setString(2, AuthorizationService.getInstance().sha256Hash(updateUserDto.getPassword()));
            ps.setString(3, updateUserDto.getName());
            nonSelectDBConnector.tryConnect(ps);

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления пользователя");
        }
    }

    public void delete(Long id) { // Вот тут обычно используется ORM
        try {
            String cmstr = "DELETE FROM \"user\" WHERE id = ?;";
            PreparedStatement ps = nonSelectDBConnector.getPreparedStatement(cmstr);
            ps.setLong(1, id);
            nonSelectDBConnector.tryConnect(ps);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления пользователя");
        }
    }

}
