package com.github.darncol.currencyexchange.entity;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ExchangeRate {
    @Expose
    private int id;
    @Expose
    private Currency baseCurrency;
    @Expose
    private Currency targetCurrency;
    @Expose
    private BigDecimal rate;

    public ExchangeRate(int id, Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        if (rate != null && rate.compareTo(BigDecimal.ZERO) > 0) {
            this.rate = rate;
        }
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", baseCurrency=" + baseCurrency +
                ", targetCurrency=" + targetCurrency +
                ", rate=" + rate +
                '}';
    }
}
