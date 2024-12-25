package com.github.darncol.currencyexchange.service;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.ExchangeRateDAO;
import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.ExchangeMoney;
import com.github.darncol.currencyexchange.entity.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeService extends ExchangeRateService {

    public ExchangeService(ExchangeRateDAO exchangeRateDAO, CurrencyDAO currencyDAO) {
        super(exchangeRateDAO, currencyDAO);
    }

    public ExchangeMoney exchangeMoney(String from, String to, String amount) {
        BigDecimal amountBigDecimal;

        try {
            amountBigDecimal = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please provide a valid amount");
        }

        ExchangeRate exchangeRate = getExchangeRate(from, to);

        if (exchangeRate == null) {
            exchangeRate = getReversedExchangeRate(to, from);
        }

        if (exchangeRate == null) {
            exchangeRate = getFromUSDExchangeRate(from, to);
        }

        return new ExchangeMoney(exchangeRate, amountBigDecimal);
    }

    private ExchangeRate getReversedExchangeRate(String to, String from) {
        ExchangeRate reversedExchangeRate = getExchangeRate(to, from);

        if (reversedExchangeRate == null) {
            return null;
        }

        BigDecimal reversedRate = BigDecimal.ONE.divide(
                reversedExchangeRate.getRate(), 6, RoundingMode.HALF_UP
        );
        reversedExchangeRate.setRate(reversedRate);

        return reversedExchangeRate;
    }

    private ExchangeRate getFromUSDExchangeRate(String from, String to) {
        final String usd = "USD";

        ExchangeRate baseCurrencyToUSD = getExchangeRate(from, usd);
        ExchangeRate uSDToTargetCurrency = getExchangeRate(usd, to);

        if(baseCurrencyToUSD == null) {
            baseCurrencyToUSD = getReversedExchangeRate(usd, from);
        }

        if(uSDToTargetCurrency == null) {
            uSDToTargetCurrency = getReversedExchangeRate(to, usd);
        }

        if (baseCurrencyToUSD == null || uSDToTargetCurrency == null) {
            throw new IllegalArgumentException("Cannot get exchange rate from " + from + " to " + to);
        }

        return new ExchangeRate(
                0,
                baseCurrencyToUSD.getBaseCurrency(),
                uSDToTargetCurrency.getTargetCurrency(),
                baseCurrencyToUSD.getRate().multiply(uSDToTargetCurrency.getRate())
        );
    }
}
