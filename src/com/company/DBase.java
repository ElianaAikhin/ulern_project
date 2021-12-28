package com.company;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBase {
    private static DBase instance = null;

    private Connection connection;

    public static DBase getInstance(){
        if (instance == null)
            instance = new DBase();
        return instance;
    }

    private DBase(){
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite3");
            System.out.println("База Подключена!");
        } catch (SQLException e) {
            System.out.println("Драйвер базы данных не был подключен!!");
            e.printStackTrace();
        }
    }

    public void createTable() {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS 'happynes_country' " +
                    "('country' VARCHAR(50) PRIMARY KEY, " +
                    "'region' VARCHAR(100)," +
                    "'rank' INTEGER," +
                    "'happynesScore' FLOAT," +
                    "'standardError' FLOAT," +
                    "'economy' FLOAT," +
                    "'family' FLOAT," +
                    "'health' FLOAT," +
                    "'freedom' FLOAT," +
                    "'trust' FLOAT," +
                    "'generosity' FLOAT," +
                    "'dystopiaResidual' FLOAT);");
            System.out.println("Таблица создана или уже существует.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Float> getCountryEconomy(){
        try (Statement statement = this.connection.createStatement()) {
            Map<String, Float> dataset = new HashMap<>();
            ResultSet dataFromDb = statement.executeQuery("SELECT country, economy FROM happynes_country");
            while (dataFromDb.next())
                dataset.put(dataFromDb.getString("country"), dataFromDb.getFloat("economy"));
            return dataset;
        } catch (SQLException e){
            System.out.println("Ошибка при выполнении запроса!");
            e.printStackTrace();
            return null;
        }
    }

    public Tuple<String, Float> getCountryWithMaxEconomy(){
        try (Statement statement = this.connection.createStatement()) {
            ResultSet dataFromDb = statement.executeQuery("SELECT country, economy FROM happynes_country WHERE economy = )" +
                    "SELECT MAX(economy) FROM happynes_country WHERE region = 'Latin America and Caribbean' OR region = 'Eastern Asia')");
            return new Tuple<>(dataFromDb.getString("country"), dataFromDb.getFloat("economy"));
        } catch (SQLException e){
            System.out.println("Невозможно получить данные о стране1");
            e.printStackTrace();
            return null;
        }
    }

    public String getMiddleCountry(){
        try (Statement statement = this.connection.createStatement()) {
            ResultSet middledata = statement.executeQuery(
                    "SELECT MIN(ABS((SELECT AVG(economy) FROM happynes_country)-economy) + " +
                            "ABS((SELECT AVG(family) FROM happynes_country)-family) +" +
                            "ABS((SELECT AVG(health) FROM happynes_country)-health) +" +
                            "ABS((SELECT AVG(freedom) FROM happynes_country)-freedom) +" +
                            "ABS((SELECT AVG(generosity) FROM happynes_country)-generosity) +" +
                            "ABS((SELECT AVG(trust) FROM happynes_country)-trust) +" +
                            "ABS((SELECT AVG(dystopiaResidual) FROM happynes_country)-dystopiaResidual)) as Result, country " +
                            "FROM happynes_country " +
                            "WHERE Region = 'Western Europe' or Region = 'North America'");
            return middledata.getString("Country");
        } catch (SQLException e){
            System.out.println("Невозможно получить данные о стране2");
            e.printStackTrace();
            return "";
        }
    }

    public void addCountryList(List<Country2015> country) {
        if (country.size() < 1)
            return;
        String sqlQuery = "INSERT INTO happynes_country VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = this.connection.prepareStatement(sqlQuery)) {
            connection.setAutoCommit(false);
            for (Country2015 countryObj : country) {
                statement.setObject(1, countryObj.getCountry());
                statement.setObject(2, countryObj.getRegion());
                statement.setObject(3, countryObj.getRank());
                statement.setObject(4, countryObj.getHappynesScore());
                statement.setObject(5, countryObj.getStandardError());
                statement.setObject(6, countryObj.getEconomy());
                statement.setObject(7, countryObj.getFamily());
                statement.setObject(8, countryObj.getHealth());
                statement.setObject(9, countryObj.getFreedom());
                statement.setObject(10, countryObj.getTrust());
                statement.setObject(11, countryObj.getGenerosity());
                statement.setObject(12, countryObj.getDystopiaResidual());

                statement.addBatch();
            }
            statement.executeBatch();
            statement.clearBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
