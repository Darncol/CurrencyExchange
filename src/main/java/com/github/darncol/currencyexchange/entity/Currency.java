package com.github.darncol.currencyexchange.entity;

import com.google.gson.annotations.SerializedName;

public class Currency {
    private int id;
    private String code;
    private String fullName;
    private String sign;

    public Currency(int id, String code, String fullName, String sign) {
        validateCurrencyCode(code);
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        validateCurrencyCode(code);
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSign() {
        return sign;
    }

    public void validateCurrencyCode(String code) {
        if (code == null || code.length() != 3) {
            throw new IllegalArgumentException("Currency code must be exactly 3 uppercase letters.");
        }
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
