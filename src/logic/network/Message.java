package logic.network;

import logic.Game;
import logic.Graph;
import logic.Player;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Collection;
import static logic.network.MessageType.*;

public class Message implements Serializable {
    public final MessageType type;

    public String username;
    public Collection<Player> players;
    public Graph gameGraph;

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
        str += "]";
        return str;
    }
}
