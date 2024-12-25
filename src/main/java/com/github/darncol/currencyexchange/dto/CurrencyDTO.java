package com.github.darncol.currencyexchange.dto;

import com.github.darncol.currencyexchange.entity.Currency;
import com.google.gson.annotations.SerializedName;

public class CurrencyDTO {
    private String code;
    @SerializedName("name")
    private String fullName;
    private String sign;

    public CurrencyDTO(Currency currency) {
        this.code = currency.getCode();
        this.fullName = currency.getFullName();
        this.sign = currency.getSign();
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSign() {
        return sign;
    }
}
