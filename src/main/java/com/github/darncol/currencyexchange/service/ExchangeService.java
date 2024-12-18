package com.github.darncol.currencyexchange.service;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.ExchangeRateDAO;
import com.github.darncol.currencyexchange.entity.ExchangeMoney;
import com.github.darncol.currencyexchange.entity.ExchangeRate;

import java.math.BigDecimal;

public class ExchangeService extends ExchangeRateService {

    public ExchangeService(ExchangeRateDAO exchangeRateDAO, CurrencyDAO currencyDAO) {
        super(exchangeRateDAO, currencyDAO);
    }

    public ExchangeMoney exchangeMoney(ExchangeRate exchangeRate, String amount) {
        BigDecimal amountBigDecimal = new BigDecimal(amount);
        return new ExchangeMoney(exchangeRate, amountBigDecimal);
    }
}
