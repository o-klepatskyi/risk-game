package com.risk.logic.bot;

import java.util.Comparator;

public class TerritoriesUnitComparator implements Comparator<TerritoriesUnit> {

    public int compare(TerritoriesUnit src, TerritoriesUnit dst) {
        return Integer.compare(src.getProbability(), dst.getProbability());
    }
}
