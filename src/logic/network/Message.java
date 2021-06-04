package logic.network;

import logic.Game;
import logic.Player;

import java.io.Serializable;
import java.util.Collection;
import static logic.network.MessageType.*;

public class Message implements Serializable {
    public final MessageType type;

    public String username;
    public Collection<Player> players;
    public Game game;

    public Message(MessageType type, String username) throws Exception {
        if (!(type == USERNAME || type == CONNECTION_CLOSED_BY_ADMIN)) {
            throw new Exception("Message type exception"); // todo unique exception
        }
        this.type = type;
        this.username = username;
    }

    public Message(MessageType type) {
        this.type = type;
    }

    public Message(MessageType type, Collection<Player> players) throws Exception {
        if (!(     type == PLAYERS
                || type == COLOR_CHANGED
                || type == BOT_ADDED
                || type == PLAYER_DELETED)) {
            throw new Exception("Message type exception"); // todo unique exception
        }
        this.type = type;
        this.players = players;
    }

    public String toString() {
        String str = "Message[type= " + type;
        if (username != null) {
            str += ", msg = "  + username;
        }
        if (players != null) {
            str += ", players= " + players;
        }
        if (game != null) {
            str += ", game= " + game;
        }
        str += "]";
        return str;
    }
}
