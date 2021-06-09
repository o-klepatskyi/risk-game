package logic.bot;
import logic.Territory;

import java.security.InvalidParameterException;

import static logic.bot.BotMoveType.*;
public class BotMove {

    public final BotMoveType type;
    public Territory territory;
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
        territory = t;
        this.troops = troops;
    }

    @Override
    public String toString() {
        return "BotMove{" +
                "type=" + type +
                ", territory=" + territory +
                ", troops=" + troops +
                '}';
    }
}
