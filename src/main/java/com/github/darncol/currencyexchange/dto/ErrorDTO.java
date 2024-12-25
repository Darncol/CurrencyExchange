package com.github.darncol.currencyexchange.dto;

import com.google.gson.Gson;

public class ErrorDTO {
    private static final Gson GSON = new Gson();

    public static String message(String message) {
        return GSON.toJson(new Message(message));
    }

    private static class Message {
        private final String message;

        public Message(String message) {
            this.message = message;
        }
    }
}
