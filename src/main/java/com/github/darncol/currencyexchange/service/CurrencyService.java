package com.github.darncol.currencyexchange.service;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.CurrencyDAOImpl;
import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.CurrencyDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyService {

    public CurrencyDTO currencyToDTO(Currency currency) {
        return new CurrencyDTO(currency);
    }

    public List<CurrencyDTO> currencyToDTOs(List<Currency> currencies) {
        return currencies.stream().map(this::currencyToDTO).collect(Collectors.toList());
    }
}
