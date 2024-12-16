package com.github.darncol.currencyexchange.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(sqliteURL());
    }

    private static String sqliteURL() {
        String relativePath = "identifier.sqlite";
        String resource = DataBaseManager.class.getClassLoader().getResource(relativePath).getPath();

        return "jdbc:sqlite:" + resource;
    }
}
