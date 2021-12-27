package com.company;

import java.util.Map;

public class Country2015 {
    private final String country;
    private final String region;
    private final int rank;
    private final float happynesScore;
    private final float standardError;
    private final float economy;
    private final float family;
    private final float health;
    private final float freedom;
    private final float trust;
    private final float generosity;
    private final float dystopiaResidual;

    public Country2015(Map<String, String> elements) {
        this.country = elements.get("Country");
        this.region = elements.get("Region");
        this.rank = Integer.parseInt(elements.get("Happiness Rank"));
        this.happynesScore = Float.parseFloat(elements.get("Happiness Score"));
        this.standardError = Float.parseFloat(elements.get("Standard Error"));
        this.economy = Float.parseFloat(elements.get("Economy (GDP per Capita)"));
        this.family = Float.parseFloat(elements.get("Family"));
        this.health = Float.parseFloat(elements.get("Health (Life Expectancy)"));
        this.freedom = Float.parseFloat(elements.get("Freedom"));
        this.trust = Float.parseFloat(elements.get("Trust (Government Corruption)"));
        this.generosity = Float.parseFloat(elements.get("Generosity"));
        this.dystopiaResidual = Float.parseFloat(elements.get("Dystopia Residual"));
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public float getHappynesScore() {
        return happynesScore;
    }

    public int getRank() {
        return rank;
    }

    public float getStandardError() {
        return standardError;
    }

    public float getEconomy() {
        return economy;
    }

    public float getFamily() {
        return family;
    }

    public float getHealth() {
        return health;
    }

    public float getFreedom() {
        return freedom;
    }

    public float getTrust() {
        return trust;
    }

    public float getGenerosity() {
        return generosity;
    }

    public float getDystopiaResidual() {
        return dystopiaResidual;
    }

    public float[] getFloatData() {return new float[] {
            happynesScore,
            standardError,
            economy,
            family,
            health,
            freedom,
            trust,
            generosity};
    }
}