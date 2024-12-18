package com.github.darncol.currencyexchange.entity;

import java.math.BigDecimal;

public class ExchangeMoney extends ExchangeRate {
    private BigDecimal amount;
    private BigDecimal convertedAmount;

    public ExchangeMoney(ExchangeRate rate) {
        super(rate.getId(), rate.getBaseCurrency(), rate.getTargetCurrency(), rate.getRate());
        this.amount = BigDecimal.ZERO;
        this.convertedAmount = BigDecimal.ZERO;
    }

    public ExchangeMoney(ExchangeRate rate, BigDecimal amount, BigDecimal convertedAmount) {
        super(rate.getId(), rate.getBaseCurrency(), rate.getTargetCurrency(), rate.getRate());
        this.setAmount(amount);
        this.setConvertedAmount(convertedAmount);
    }

    public ExchangeMoney(int id, Currency baseCurrency, Currency targetCurrency, BigDecimal rate, BigDecimal convertedAmount, BigDecimal amount) {
        super(id, baseCurrency, targetCurrency, rate);
        this.setAmount(amount);
        this.setConvertedAmount(convertedAmount);
    }

    @Override
    public String toString() {
        return "ExchangeMoney{" +
                "convertedAmount=" + convertedAmount +
                ", amount=" + amount +
                '}';
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        if (convertedAmount != null && convertedAmount.compareTo(BigDecimal.ZERO) > 0) {
            this.convertedAmount = convertedAmount;
        } else {
            this.convertedAmount = BigDecimal.ZERO;
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) < 0) {
            this.amount = amount;
        } else {
            this.amount = BigDecimal.ZERO;
        }
    }
}
