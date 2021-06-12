package com.risk.logic.bot;
import com.risk.logic.Territory;

import java.security.InvalidParameterException;

import static com.risk.logic.bot.BotMoveType.*;
public class BotMove {

    public final BotMoveType type;
    public Territory src, dst;
    public int troops;


    BotMove(BotMoveType type) {
        if (type != END_REINFORCEMENT && type != END_ATTACK && type != END_FORTIFY)
            throw new InvalidParameterException("Invalid bot move type");
        this.type = type;
    }

    BotMove(BotMoveType type, Territory t, int troops) {
        if (type != REINFORCEMENT)
            throw new InvalidParameterException("Invalid bot move type");
        this.type = type;
        src = t;
        this.troops = troops;
    }

    BotMove(BotMoveType type, Territory src, Territory dst) {
        if (type != ATTACK)
            throw new InvalidParameterException("Invalid bot move type");
        this.type = type;
        this.src = src;
        this.dst = dst;
    }

    BotMove(BotMoveType type, Territory src, Territory dst, int troops) {
        if (type != FORTIFY)
            throw new InvalidParameterException("Invalid bot move type");
        this.type = type;
        this.src = src;
        this.dst = dst;
        this.troops = troops;
    }

    @Override
    public String toString() {
        return "BotMove{" +
                "type=" + type +
                ", territory=" + src +
                ", troops=" + troops +
                '}';
    }
}
