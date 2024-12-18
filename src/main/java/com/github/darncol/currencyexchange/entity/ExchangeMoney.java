package com.github.darncol.currencyexchange.entity;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ExchangeMoney extends ExchangeRate {
    @Expose
    private BigDecimal amount;
    @Expose
    private BigDecimal convertedAmount;

    public ExchangeMoney(ExchangeRate rate, BigDecimal amount) {
        super(rate.getId(), rate.getBaseCurrency(), rate.getTargetCurrency(), rate.getRate());
        this.setAmount(amount);
        this.convertedAmount = getAmount().multiply(rate.getRate());
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            this.amount = amount;
        } else {
            this.amount = BigDecimal.ZERO;
        }
    }

    @Override
    public String toString() {
        return "ExchangeMoney{" +
                "convertedAmount=" + convertedAmount +
                ", amount=" + amount +
                '}';
    }
}
