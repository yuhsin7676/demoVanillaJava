package com.example.demoVanillaJava.spi;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseService {

    private final String URL = "jdbc:postgresql://localhost:5432/demoVanillaJava_db";
    private final String DATA_BASE_NAME = "demoVanillaJava_db";
    private final String USER_NAME = "admin";
    private final String PASSWORD = "postgres";
    private final String CHANGELOG_PATH = "/db/migration/init_user.sql";
    private final String CHANGELOG_SCHEMA = "public";
    private final String UPDATE_SCHEMA = "public";

    public void migration() {

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            connection.prepareStatement(String.format("SET search_path TO '%s'", UPDATE_SCHEMA)).execute(); // Без этого не робит

            JdbcConnection jdbcConnection = new JdbcConnection(connection);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            database.setDefaultSchemaName(CHANGELOG_SCHEMA);

            Liquibase liquibase = new Liquibase(CHANGELOG_PATH, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
            System.out.println("Liquibase update successful.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
