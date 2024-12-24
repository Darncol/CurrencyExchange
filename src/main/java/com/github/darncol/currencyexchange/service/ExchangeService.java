package com.github.darncol.currencyexchange.service;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.ExchangeRateDAO;
import com.github.darncol.currencyexchange.entity.ExchangeMoney;
import com.github.darncol.currencyexchange.entity.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeService extends ExchangeRateService {

    public ExchangeService(ExchangeRateDAO exchangeRateDAO, CurrencyDAO currencyDAO) {
        super(exchangeRateDAO, currencyDAO);
    }

    public ExchangeMoney exchangeMoney(String from, String to, String amount) {
        BigDecimal amountBigDecimal = new BigDecimal(amount);

        if(amountBigDecimal  == null) {
            throw new IllegalArgumentException("Please provide a valid amount");
        }

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
            throw new IllegalArgumentException("Cannot get exchange rate from " + from + " to " + to);
        }

        BigDecimal reversedRate = BigDecimal.ONE.divide(
                reversedExchangeRate.getRate(), 6, RoundingMode.HALF_UP
        );
        reversedExchangeRate.setRate(reversedRate);

        return reversedExchangeRate;
    }
}
