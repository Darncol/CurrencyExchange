package com.github.darncol.currencyexchange.controller;

import com.github.darncol.currencyexchange.dao.CurrencyDAOImpl;
import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.CurrencyDTO;
import com.github.darncol.currencyexchange.service.CurrencyService;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class GetAllCurrencies extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IOException("JDBC Driver not found", e);
        }

        CurrencyDAOImpl dao = new CurrencyDAOImpl();
        CurrencyService service = new CurrencyService();
        Gson gson = new Gson();

        List<Currency> currencies = dao.getAllCurrencies();
        List<CurrencyDTO> dtos = service.currencyToDTOs(currencies);

        String json = gson.toJson(dtos);
        response.getWriter().write(json);
    }
}
