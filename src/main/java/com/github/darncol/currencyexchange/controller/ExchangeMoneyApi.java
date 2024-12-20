package com.github.darncol.currencyexchange.controller;

import com.github.darncol.currencyexchange.dao.CurrencySQLite;
import com.github.darncol.currencyexchange.dao.ExchangeRateSQLite;
import com.github.darncol.currencyexchange.entity.ExchangeMoney;
import com.github.darncol.currencyexchange.entity.ExchangeRate;
import com.github.darncol.currencyexchange.service.ExchangeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringReader;

@WebServlet("/exchange")
public class ExchangeMoneyApi extends HttpServlet {
    private final ExchangeService exchangeService = new ExchangeService(new ExchangeRateSQLite(), new CurrencySQLite());
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");

        try {
            ExchangeMoney exchangeMoney = exchangeService.exchangeMoney(from, to, amount);
            String json = gson.toJson(exchangeMoney);
            resp.getWriter().write(json);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (IllegalArgumentException e) {
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
