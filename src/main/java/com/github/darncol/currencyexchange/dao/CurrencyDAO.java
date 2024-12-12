package com.github.darncol.currencyexchange.dao;

import com.github.darncol.currencyexchange.entity.Currency;

import java.util.List;

public interface CurrencyDAO {
    List<Currency> getAllCurrencies();
    Currency getCurrencyByCode(String code);
    void addCurrency(Currency currency);
    boolean isCurrencyExists(String code);
}
