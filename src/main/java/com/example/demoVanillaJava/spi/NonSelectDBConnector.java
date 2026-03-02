package com.example.demoVanillaJava.spi;

import java.sql.*;

public class NonSelectDBConnector extends DBConnector {

    public void tryConnect(String sql) throws SQLException {

        Connection connection = DriverManager.getConnection(URL + "/" + DATA_BASE_NAME, USER_NAME, PASSWORD);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        // Получение данных запроса
        ResultSet resultSet = statement.executeQuery(sql);
    }

    public void tryConnect(PreparedStatement preparedStatement) throws SQLException{
        ResultSet resultSet = preparedStatement.executeQuery();
    }

}
