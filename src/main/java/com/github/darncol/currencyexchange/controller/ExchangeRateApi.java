package com.github.darncol.currencyexchange.controller;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.CurrencySQLite;
import com.github.darncol.currencyexchange.dao.ExchangeRateDAO;
import com.github.darncol.currencyexchange.dao.ExchangeRateSQLite;
import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.ExchangeRate;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRateApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ExchangeRateSQLite dao = new ExchangeRateSQLite();
        Gson gson = new Gson();

        List<ExchangeRate> exchangeRates = dao.getExchangeRates();
        String json = gson.toJson(exchangeRates);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rateStr = req.getParameter("rate");
        BigDecimal rate = BigDecimal.valueOf(Double.parseDouble(rateStr));

        if (baseCurrencyCode == null || targetCurrencyCode == null || rate == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            ExchangeRateDAO exchangeRateDAO = new ExchangeRateSQLite();
            CurrencyDAO currencyDAO = new CurrencySQLite();

            Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
            Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);

            ExchangeRate exchangeRate = new ExchangeRate(
                    0,
                    baseCurrency,
                    targetCurrency,
                    rate
            );

            exchangeRateDAO.saveExchangeRate(exchangeRate);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeRate exchangeRate;
        BigDecimal rateBigDecimal;
        String rate = req.getParameter("rate");
        String baseCurrencyCode = req.getPathInfo().substring(1, 4);
        String targetCurrencyCode = req.getPathInfo().substring(4);
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateSQLite();
        CurrencyDAO currencyDAO = new CurrencySQLite();
        Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);

        try {
            rateBigDecimal = BigDecimal.valueOf(Double.parseDouble(rate));
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
            return;
        }

        if (baseCurrency == null || targetCurrency == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        exchangeRate = exchangeRateDAO.getExchangeRate(baseCurrency, targetCurrency);

        if (exchangeRate == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        exchangeRate.setRate(rateBigDecimal);
        exchangeRateDAO.updateExchangeRate(exchangeRate);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
