package com.github.darncol.currencyexchange;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.CurrencyDAOImpl;
import com.github.darncol.currencyexchange.dao.ExchangeRateDAO;
import com.github.darncol.currencyexchange.dao.ExchangeRateDAOImpl;
import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.CurrencyDTO;
import com.github.darncol.currencyexchange.entity.ExchangeRate;
import com.github.darncol.currencyexchange.service.CurrencyService;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CurrencyDAO dao = new CurrencyDAOImpl();
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAOImpl();
        CurrencyService service = new CurrencyService();
        Gson gson = new Gson();
    }
}
