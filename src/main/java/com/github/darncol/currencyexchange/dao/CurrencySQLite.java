package com.github.darncol.currencyexchange.dao;

import com.github.darncol.currencyexchange.entity.Currency;
import com.github.darncol.currencyexchange.utils.DataBaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencySQLite implements CurrencyDAO {

    @Override
    public List<Currency> getAllCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        String query = "SELECT * FROM currencies";

        try (
                Connection connection = DataBaseManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {

            while (resultSet.next()) {
                currencies.add(new Currency(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("fullname"),
                        resultSet.getString("sign")
                ));
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Error getting currencies from database");
        }

        return currencies;
    }

    @Override
    public Currency getCurrencyByCode(String code) {
        Currency currency = null;
        String query = "SELECT * FROM currencies WHERE code = ?";

        try (
                Connection connection = DataBaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {

                if (resultSet.next()) {
                    currency = new Currency(
                            resultSet.getInt("id"),
                            resultSet.getString("code"),
                            resultSet.getString("fullname"),
                            resultSet.getString("sign")
                    );
                }
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("no such currency");
        }

        return currency;
    }

    @Override
    public void addCurrency(Currency currency) {
        String query = "INSERT INTO currencies(code, fullname, sign) VALUES(?, ?, ?)";

        try (
                Connection connection = DataBaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException("Error inserting currency");
        }
    }
}
