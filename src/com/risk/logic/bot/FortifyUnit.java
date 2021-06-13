package com.risk.logic.bot;

import com.risk.logic.Territory;

public class FortifyUnit {
    private int troops;
    private Territory src, dst;

    public FortifyUnit(Territory src, Territory dst, int troops) {
        this.src = src;
        this.dst = dst;
        this.troops = troops;
    }

    public int getTroops() {
        return troops;
    }

    public Territory getSrc() {
        return src;
    }

    public Territory getDst() {
        return dst;
    }

    public void setTroops(int troops) {
        this.troops = troops;
    }

    public void setSrc(Territory src) {
        this.src = src;
    }

    public void setDst(Territory dst) {
        this.dst = dst;
    }

    @Override
    public String toString() {
        return "FortifyUnit{" +
                "troops=" + troops +
                ", src=" + src +
                ", dst=" + dst +
                '}';
    }
}
