package com.github.darncol.currencyexchange.service;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.ExchangeRateDAO;
import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateService {
    private final ExchangeRateDAO exchangeRateDAO;
    private final CurrencyDAO currencyDAO;

    public ExchangeRateService(ExchangeRateDAO exchangeRateDAO, CurrencyDAO currencyDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
        this.currencyDAO = currencyDAO;
    }

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRateDAO.getExchangeRates();
    }

    public ExchangeRate getExchangeRate(String from, String to) {
        Currency baseCurrency = currencyDAO.getCurrencyByCode(from);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(to);

        return exchangeRateDAO.getExchangeRate(baseCurrency, targetCurrency);
    }

    public void addExchangeRate(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);

        BigDecimal rateBigDecimal = new BigDecimal(rate);

        if (baseCurrency == null || targetCurrency == null) {
            throw new IllegalArgumentException("Currency not found");
        }

        if (baseCurrencyCode.equals(targetCurrencyCode)) {
            throw new IllegalArgumentException("Currency and target currency are the same");
        }

        if (rateBigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Rate must be greater than zero");
        }

        if (getExchangeRate(baseCurrencyCode, targetCurrencyCode) != null) {
            throw new IllegalArgumentException("Exchange rate already exists");
        }

        ExchangeRate exchangeRate = new ExchangeRate(0, baseCurrency, targetCurrency, rateBigDecimal);
        exchangeRateDAO.saveExchangeRate(exchangeRate);
    }

    public void updateExchangeRate(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        BigDecimal rateBigDecimal = new BigDecimal(rate);
        Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);

        if (baseCurrency == null || targetCurrency == null || rate == null) {
            throw new IllegalArgumentException("Currency or rate not found");
        }

        ExchangeRate exchangeRate = exchangeRateDAO.getExchangeRate(baseCurrency, targetCurrency);


        if (exchangeRate == null) {
            throw new IllegalArgumentException("Exchange rate not found");
        }

        exchangeRate.setRate(rateBigDecimal);
        exchangeRateDAO.updateExchangeRate(exchangeRate);
    }
}
