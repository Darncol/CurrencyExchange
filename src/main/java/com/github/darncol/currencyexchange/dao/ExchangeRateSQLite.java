package com.github.darncol.currencyexchange.dao;

import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.entity.ExchangeRate;
import com.github.darncol.currencyexchange.utils.DataBaseManager;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateSQLite implements ExchangeRateDAO {

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

        try (
                Connection connection = DataBaseManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {

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
            throw new IllegalArgumentException("can't get currencies from database");
        }

        return exchangeRatesList;
    }

    @Override
    public ExchangeRate getExchangeRate(Currency from, Currency to) {
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
                WHERE ex.basecurrencyid = ? AND ex.targetcurrencyid = ?;
                """;
        ExchangeRate exchangeRate = null;

        try (
                Connection connection = DataBaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
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
            throw new IllegalArgumentException("can't get Exchange rate from database");
        }

        return exchangeRate;
    }

    @Override
    public void saveExchangeRate(ExchangeRate exchangeRate) {
        String query = "INSERT INTO ExchangeRates (basecurrencyid, targetcurrencyid, rate) VALUES (?, ?, ?)";

        try (
                Connection connection = DataBaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, exchangeRate.getBaseCurrency().getId());
            preparedStatement.setInt(2, exchangeRate.getTargetCurrency().getId());
            preparedStatement.setBigDecimal(3, exchangeRate.getRate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("can't save exchange rate to database");
        }
    }

    @Override
    public void updateExchangeRate(ExchangeRate exchangeRate) {
        final String query = "UPDATE ExchangeRates SET rate = ? WHERE basecurrencyid = ? AND targetcurrencyid = ?";

        try (
                Connection connection = DataBaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setBigDecimal(1, exchangeRate.getRate());
            preparedStatement.setInt(2, exchangeRate.getBaseCurrency().getId());
            preparedStatement.setInt(3, exchangeRate.getTargetCurrency().getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new IllegalArgumentException("can't update exchange rate to database");
        }
    }
}
