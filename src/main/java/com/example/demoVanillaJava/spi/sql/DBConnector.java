package com.example.demoVanillaJava.spi.sql;

import java.sql.*;

public class DBConnector {

    protected final String URL = "jdbc:postgresql://localhost:5432";
    protected final String DATA_BASE_NAME = "demoVanillaJava_db";
    protected final String USER_NAME = "admin";
    protected final String PASSWORD = "postgres";

    public PreparedStatement getPreparedStatement(String strSQL) throws SQLException{

        Connection connection = DriverManager.getConnection(URL + "/" + DATA_BASE_NAME, USER_NAME, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(strSQL);
        return preparedStatement;

    }

}
