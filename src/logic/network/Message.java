package logic.network;

import logic.*;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Collection;
import static logic.network.MessageType.*;

public class Message implements Serializable {
    public final MessageType type;

    public String username;
    public Collection<Player> players;
    public Graph gameGraph;
    public Territory src, dst, newSrc, newDst;
    public int troops;

    public Message(MessageType type, String username) {
        if (!(type == USERNAME || type == CONNECTION_CLOSED_BY_ADMIN || type == PLAYER_LEFT_IN_GAME)) {
            throw new InvalidParameterException("Message type exception");
        }
        this.type = type;
        this.username = username;
    }

    public Message(MessageType type) {
        this.type = type;
    }

    public Message(MessageType type, Collection<Player> players) {
        if (!(     type == PLAYERS
                || type == COLOR_CHANGED
                || type == BOT_ADDED
                || type == PLAYER_DELETED)) {
            throw new InvalidParameterException("Message type exception");
        }
        this.type = type;
        this.players = players;
    }

    public Message(MessageType type, Graph gameGraph) {
        if (type != START_GAME) throw new InvalidParameterException("Message type exception");
        this.type = type;
        this.gameGraph = gameGraph;
    }

    public Message(MessageType type, String srcName, int srcTroops, Player srcOwner, String dstName, int dstTroops, Player dstOwner) {
        if (type != ATTACK) throw new InvalidParameterException("Message type exception");
        this.type = type;
        this.srcName = srcName;
        this.srcTroops = srcTroops;
        this.srcOwner = srcOwner;
        this.dstName = dstName;
        this.dstTroops = dstTroops;
        this.dstOwner = dstOwner;
    }

    public String srcName, dstName;
    public int srcTroops;
    public int dstTroops;
    public Player srcOwner;
    public Player dstOwner;

    public Message(MessageType type, Territory src, int troops) {
        if (type != REINFORCE) throw new InvalidParameterException("Message type exception");
        this.type = type;
        this.src = src;
        this.troops = troops;
    }

    public Message(MessageType type, Territory src, Territory dst, int troops) {
        if (type != FORTIFY) throw new InvalidParameterException("Message type exception");
        this.type = type;
        this.src = src;
        this.dst = dst;
        this.troops = troops;
    }

    public String toString() {
        String str = "Message[type= " + type;
        if (username != null) {
            str += ", username = "  + username;
        }
        if (players != null) {
            str += ", players= " + players;
        }
        if (gameGraph != null) {
            str += ", game= " + gameGraph;
        }
        if (src != null) {
            str += ", \nsrc= " + src;
        }
        if (dst != null) {
            str += ", \ndst= " + dst;
        }
        if (troops != 0) {
            str += ", troops= " + troops;
        }
        if (newSrc != null) {
            str += ", \nnewSrc= " + newSrc;
        }
        if (newDst != null) {
            str += ", \nnewDst= " + newDst;
        }
        str += "]";
        return str;
    }
}
