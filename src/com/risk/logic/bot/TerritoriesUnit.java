package com.risk.logic.bot;

import com.risk.logic.Territory;

import java.util.Comparator;

public class TerritoriesUnit {
    private int probability;
    private Territory src, dst;

    public TerritoriesUnit(Territory src, Territory dst, int probability) {
        this.src = src;
        this.dst = dst;
        this.probability = probability;
    }

    public int getProbability() {
        return probability;
    }

    public Territory getSrc() {
        return src;
    }

    public Territory getDst() {
        return dst;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public void setSrc(Territory src) {
        this.src = src;
    }

    public void setDst(Territory dst) {
        this.dst = dst;
    }

    @Override
    public String toString() {
        return "AttackUnit{" +
                "probability=" + probability +
                ", src=" + src +
                ", dst=" + dst +
                '}';
    }
}
