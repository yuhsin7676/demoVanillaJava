package com.example.demoVanillaJava.spi;

import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SelectDBConnector extends DBConnector{

    public String[][] tryConnect(String selectSQL) throws SQLException {

        Connection connection = DriverManager.getConnection(URL + "/" + DATA_BASE_NAME, USER_NAME, PASSWORD);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        // Получение данных запроса
        ResultSet resultSet = statement.executeQuery(selectSQL);
        return getResult(resultSet);
    }

    public String[][] tryConnect(PreparedStatement preparedStatement) throws SQLException{
        ResultSet resultSet = preparedStatement.executeQuery();
        return getResult(resultSet);
    }

    private String[][] getResult(ResultSet resultSet) throws SQLException{

        int NUM_OF_COLUMNS = resultSet.getMetaData().getColumnCount();
        resultSet.last();
        int NUM_OF_ROW = resultSet.getRow();
        resultSet.beforeFirst();

        // Создание таблицы
        String[][] result = new String[NUM_OF_ROW + 1][NUM_OF_COLUMNS];

        for(int i = 1; i <= NUM_OF_COLUMNS; i++)
            result[0][i - 1] = resultSet.getMetaData().getColumnName(i);

        for (int j = 1; j <= NUM_OF_ROW; j++){
            resultSet.next();
            for(int i = 1; i <= NUM_OF_COLUMNS; i++)
                result[j][i - 1] = resultSet.getString(i);
        }

        return result;

    }

}
