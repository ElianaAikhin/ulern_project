package com.company;

import com.company.Country2015;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParserCSV {
    public static List<Country2015> ParseCountry2015Csv(String fileName) {
        try {
            List<Country2015> countyList = new ArrayList<>();
            List<String> fileLines = Files.readAllLines(Paths.get("C:\\Users\\Элиана\\Downloads\\country2015.csv"));
            String[] headers = fileLines.remove(0).split(",");
            for (String fileLine : fileLines) {
                String[] values = fileLine.split(",");
                Map<String, String> data = IntStream.range(0, headers.length).boxed().collect(Collectors.toMap(i -> headers[i], i -> values[i]));
                countyList.add(new Country2015(data));
            }
            return countyList;
        } catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Данные не были обработанны!");
            return new ArrayList<>();
        }

    }
}
