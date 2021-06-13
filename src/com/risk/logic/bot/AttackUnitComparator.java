package com.risk.logic.bot;

import java.util.Comparator;

public class AttackUnitComparator implements Comparator<AttackUnit> {

    public int compare(AttackUnit src, AttackUnit dst) {
        return Integer.compare(src.getProbability(), dst.getProbability());
    }
}
