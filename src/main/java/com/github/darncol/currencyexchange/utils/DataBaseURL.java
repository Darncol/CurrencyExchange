package com.github.darncol.currencyexchange.utils;

import java.net.URL;

public class DataBaseURL {

    private DataBaseURL() {}

    public static String sqlitURL() {
        // Используем загрузку ресурса из classpath
        String relativePath = "identifier.sqlite";
        String resource = DataBaseURL.class.getClassLoader().getResource(relativePath).getPath();

        return "jdbc:sqlite:" + resource;
    }
}
