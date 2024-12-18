package com.github.darncol.currencyexchange.service;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.ExchangeRateDAO;

public class ExchangeService {
    private CurrencyDAO currencyDAO;
    private ExchangeRateDAO exchangeRateDAO;

    public ExchangeService(CurrencyDAO currencyDAO, ExchangeRateDAO exchangeRateDAO) {
        this.currencyDAO = currencyDAO;
        this.exchangeRateDAO = exchangeRateDAO;
    }


}
