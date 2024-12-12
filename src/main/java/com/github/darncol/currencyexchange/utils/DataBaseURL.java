package com.github.darncol.currencyexchange.utils;

import javax.xml.crypto.Data;
import java.net.URL;

public class DataBaseURL {

    private DataBaseURL() {}

    public static String sqlitURL() {
        String relativePath = "identifier.sqlite";
        String resource = DataBaseURL.class.getClassLoader().getResource(relativePath).getPath();

        return "jdbc:sqlite:" + resource;
    }
}
