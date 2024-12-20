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

    public ExchangeMoney exchangeMoney(String from, String to, String amount) {
        BigDecimal amountBigDecimal = new BigDecimal(amount);

        ExchangeRate exchangeRate = getExchangeRate(from, to);

        if (exchangeRate == null) {
            exchangeRate = getReversedExchangeRate(to, from);
        }

        if (exchangeRate == null) {
            throw new IllegalArgumentException("No exchange rate found for " + from + " to " + to);
        }

        return new ExchangeMoney(exchangeRate, amountBigDecimal);
    }

    private ExchangeRate getReversedExchangeRate(String to, String from) {
        ExchangeRate reversedExchangeRate = getExchangeRate(to, from);

        if (reversedExchangeRate == null) {
            throw new IllegalArgumentException("Cannot get reverse exchange rate from " + from + " to " + to);
        }
        BigDecimal rate = reversedExchangeRate.getRate().divide(new BigDecimal(100));
        reversedExchangeRate.setRate(rate);

        return reversedExchangeRate;
    }
}
