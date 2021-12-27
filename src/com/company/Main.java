package com.company;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        List<Country2015> dataCSV = ParserCSV.ParseCountry2015Csv("C:\\Users\\Элиана\\Downloads\\country2015.csv");
        DBase dbInstance = DBase.getInstance();
        Map<String, Float> dataDb = dbInstance.getCountryEconomy();
        Tuple<String, Float> maxEconomy = dbInstance.getCountryWithMaxEconomy();
        String middleCountry = dbInstance.getMiddleCountry();
        System.out.println("Самая богатая страна: " + maxEconomy.getFirst() + " с показателем экономики: " + maxEconomy.getSecond());
        System.out.println("Самая средняя страна: " + middleCountry);

        EventQueue.invokeLater(() -> {
            CountryEconomyChart ex = new CountryEconomyChart(dataDb);
            ex.setVisible(true);
        });
    }
}