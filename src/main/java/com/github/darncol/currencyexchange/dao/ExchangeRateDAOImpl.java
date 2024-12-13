package com.github.darncol.currencyexchange.dao;

import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.ExchangeRate;
import com.github.darncol.currencyexchange.utils.DataBaseURL;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAOImpl implements ExchangeRateDAO {
    private final String url = DataBaseURL.sqliteURL();


    @Override
    public List<ExchangeRate> getExchangeRates() {
        List<ExchangeRate> exchangeRatesList = new ArrayList<>();
        final String query = """
                SELECT  
                ex.id,
                ex.rate,
                bc.id AS basecurrencyid,
                bc.code AS basecurrencycode,
                bc.fullname AS basecurrencyfullname,
                bc.sign AS basecurrencysign,
                tc.id AS targetcurrencyid,
                tc.code AS targetcurrencycode,
                tc.fullname AS targetcurrencyfullname,
                tc.sign AS targetcurrencysign
                FROM exchangerates ex
                JOIN currencies bc ON bc.id = ex.basecurrencyid
                JOIN currencies tc ON tc.id = ex.targetcurrencyid
                """;
        Gson gson = new Gson();

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);) {

            while (resultSet.next()) {
                Currency baseCurrency = new Currency(
                        resultSet.getInt("basecurrencyid"),
                        resultSet.getString("basecurrencycode"),
                        resultSet.getString("basecurrencyfullname"),
                        resultSet.getString("basecurrencysign")
                );

                Currency targetCurrency = new Currency(
                        resultSet.getInt("targetcurrencyid"),
                        resultSet.getString("targetcurrencycode"),
                        resultSet.getString("targetcurrencyfullname"),
                        resultSet.getString("targetcurrencysign")
                );

                ExchangeRate exchangeRate = new ExchangeRate(
                        resultSet.getInt("id"),
                        baseCurrency,
                        targetCurrency,
                        resultSet.getBigDecimal("rate")
                );

                exchangeRatesList.add(exchangeRate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exchangeRatesList;
    }

    @Override
    public ExchangeRate getExchangeRate(Currency from, Currency to) {
        String query = "SELECT * FROM exchangerates WHERE basecurrencyid = ? AND targetcurrencyid = ?";
        ExchangeRate exchangeRate = null;

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, from.getId());
            preparedStatement.setInt(2, to.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    Currency baseCurrency = new Currency(
                            resultSet.getInt("basecurrencyid"),
                            resultSet.getString("basecurrencycode"),
                            resultSet.getString("basecurrencyfullname"),
                            resultSet.getString("basecurrencysign")
                    );

                    Currency targetCurrency = new Currency(
                            resultSet.getInt("targetcurrencyid"),
                            resultSet.getString("targetcurrencycode"),
                            resultSet.getString("targetcurrencyfullname"),
                            resultSet.getString("targetcurrencysign")
                    );

                    exchangeRate = new ExchangeRate(
                            resultSet.getInt("id"),
                            baseCurrency,
                            targetCurrency,
                            resultSet.getBigDecimal("rate")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exchangeRate;
    }

    @Override
    public void saveExchangeRate(ExchangeRate exchangeRate) {
        String query = "INSERT INTO ExchangeRates (basecurrencyid, targetcurrencyid, rate) VALUES (?, ?, ?)";

        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, exchangeRate.getBaseCurrency().getId());
            preparedStatement.setInt(2, exchangeRate.getTargetCurrency().getId());
            preparedStatement.setBigDecimal(3, exchangeRate.getRate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateExchangeRate(ExchangeRate exchangeRate) {
        final String query = "UPDATE ExchangeRates SET rate = ? WHERE basecurrencyid = ? AND targetcurrencyid = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
