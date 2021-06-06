package logic.network;

import logic.Game;
import logic.Graph;
import logic.Player;
import logic.Territory;

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
        if (!(type == USERNAME || type == CONNECTION_CLOSED_BY_ADMIN)) {
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

    public Message(MessageType type, Territory src, int troops) {
        if (type != REINFORCE) throw new InvalidParameterException("Message type exception");
        this.type = type;
        this.src = src;
        this.troops = troops;
    }

    public Message(MessageType type, Territory src, Territory dst, Territory newSrc, Territory newDst) {
        if (type != ATTACK) throw new InvalidParameterException("Message type exception");
        this.type = type;
        this.src = src;
        this.dst = dst;
        this.newSrc = newSrc;
        this.newDst = newDst;
    }

    public Message(MessageType type, Territory src, Territory dst, int troops) {
        if (type != FORTIFY) throw new InvalidParameterException("Message type exception");
        this.type = type;
        this.src = src;
        this.dst = dst;
        this.troops = troops;
    }

    public String toString() {
        String str = hashCode() + "\nMessage[type= " + type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (troops != message.troops) return false;
        if (type != message.type) return false;
        if (username != null ? !username.equals(message.username) : message.username != null) return false;
        if (players != null ? !players.equals(message.players) : message.players != null) return false;
        if (gameGraph != null ? !gameGraph.equals(message.gameGraph) : message.gameGraph != null) return false;
        if (src != null ? !src.equals(message.src) : message.src != null) return false;
        if (dst != null ? !dst.equals(message.dst) : message.dst != null) return false;
        if (newSrc != null ? !newSrc.equals(message.newSrc) : message.newSrc != null) return false;
        return newDst != null ? newDst.equals(message.newDst) : message.newDst == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (players != null ? players.hashCode() : 0);
        result = 31 * result + (gameGraph != null ? gameGraph.hashCode() : 0);
        result = 31 * result + (src != null ? src.hashCode() : 0);
        result = 31 * result + (dst != null ? dst.hashCode() : 0);
        result = 31 * result + (newSrc != null ? newSrc.hashCode() : 0);
        result = 31 * result + (newDst != null ? newDst.hashCode() : 0);
        result = 31 * result + troops;
        return result;
    }
}
