package com.github.darncol.currencyexchange.controller;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.CurrencySQLite;
import com.github.darncol.currencyexchange.dao.ExchangeRateDAO;
import com.github.darncol.currencyexchange.dao.ExchangeRateSQLite;
import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.ExchangeRate;
import com.github.darncol.currencyexchange.service.ExchangeRateService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet({"/exchangeRates", "/exchangeRate/*"})
public class ExchangeRateApi extends HttpServlet {
    private final ExchangeRateService exchangeRateService = new ExchangeRateService(new ExchangeRateSQLite(), new CurrencySQLite());
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<ExchangeRate> exchangeRates = exchangeRateService.getExchangeRates();
        String json = gson.toJson(exchangeRates);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        try {
            exchangeRateService.addExchangeRate(baseCurrencyCode, targetCurrencyCode, rate);
            resp.getWriter().write("{\"message\":\"" + "Added" + "\"}");
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (IllegalArgumentException e) {
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        try (var reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String rate = sb.toString().substring(5);

        String baseCurrencyCode = req.getPathInfo().substring(1, 4);
        String targetCurrencyCode = req.getPathInfo().substring(4);

        try {
            exchangeRateService.updateExchangeRate(baseCurrencyCode, targetCurrencyCode, rate);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
