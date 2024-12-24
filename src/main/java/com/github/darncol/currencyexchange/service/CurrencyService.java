package com.github.darncol.currencyexchange.service;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.CurrencyDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyService {
    private CurrencyDAO currencyDAO;

    public CurrencyService(CurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    public List<CurrencyDTO> getCurrenciesDTO() {
        List<Currency> currencies = currencyDAO.getAllCurrencies();
        List<CurrencyDTO> dtos = currencyToDTOs(currencies);

        return dtos;
    }

    public void addNewCurrency(String code, String fullname, String sign) {
            validateCurrencyCode(code);
            validateCurrencyExists(code);

            Currency currency = new Currency(0, code, fullname, sign);
            currencyDAO.addCurrency(currency);
    }

    private CurrencyDTO currencyToDTO(Currency currency) {
        return new CurrencyDTO(currency);
    }

    private List<CurrencyDTO> currencyToDTOs(List<Currency> currencies) {
        return currencies.stream().map(this::currencyToDTO).collect(Collectors.toList());
    }

    private void validateCurrencyCode(String code) {
        if (code == null || code.length() != 3 || !code.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("Currency code must be exactly 3 uppercase letters.");
        }
    }

    private void validateCurrencyExists(String code) {
        if (currencyDAO.getCurrencyByCode(code) != null) {
            throw new IllegalArgumentException("Currency with code " + code + " already exist.");
        }
    }
}