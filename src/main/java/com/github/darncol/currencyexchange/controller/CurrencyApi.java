package com.github.darncol.currencyexchange.controller;

import com.github.darncol.currencyexchange.dao.CurrencyDAO;
import com.github.darncol.currencyexchange.dao.CurrencySQLite;
import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.CurrencyDTO;
import com.github.darncol.currencyexchange.service.CurrencyService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrencyApi extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService(new CurrencySQLite());
    private final Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<CurrencyDTO> dtos = currencyService.getCurrenciesDTO();
        String json = gson.toJson(dtos);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code").toUpperCase();
        String fullname = req.getParameter("name");
        String sign = req.getParameter("sign");

        try {
            currencyService.addNewCurrency(code, fullname, sign);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"Server error\"}");
        }
    }
}
