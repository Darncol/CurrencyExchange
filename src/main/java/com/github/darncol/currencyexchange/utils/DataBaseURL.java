package com.github.darncol.currencyexchange.utils;

public class DataBaseURL {

    private DataBaseURL() {}

    public static String sqliteURL() {
        String relativePath = "identifier.sqlite";
        String resource = DataBaseURL.class.getClassLoader().getResource(relativePath).getPath();

        return "jdbc:sqlite:" + resource;
    }
}
