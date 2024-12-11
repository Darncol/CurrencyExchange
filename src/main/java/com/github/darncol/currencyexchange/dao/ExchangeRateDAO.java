package com.github.darncol.currencyexchange.dao;

import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.ExchangeRate;

import java.util.List;

public interface ExchangeRateDAO {
    List<ExchangeRate> getExchangeRates();
    ExchangeRate getExchangeRate(Currency from, Currency to);
    void saveExchangeRate(ExchangeRate exchangeRate);
}
