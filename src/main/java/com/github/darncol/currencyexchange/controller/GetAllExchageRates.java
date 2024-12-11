package com.github.darncol.currencyexchange.controller;

import com.github.darncol.currencyexchange.dao.ExchangeRateDAOImpl;
import com.github.darncol.currencyexchange.entity.ExchangeRate;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class GetAllExchageRates extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IOException("JDBC Driver not found", e);
        }

        ExchangeRateDAOImpl dao = new ExchangeRateDAOImpl();
        Gson gson = new Gson();

        List<ExchangeRate> exchangeRates = dao.getExchangeRates();
        String json = gson.toJson(exchangeRates);
        resp.getWriter().write(json);
    }
}
