package com.risk.logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Continent implements Serializable {
    public final String name;
    public final int bonus;
    private final ArrayList<Territory> territories = new ArrayList<>();

    public Continent(String name, int bonus) {
        this.name = name;
        this.bonus = bonus;
    }

    public void addTerritory(Territory t) {
        territories.add(t);
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }
}
