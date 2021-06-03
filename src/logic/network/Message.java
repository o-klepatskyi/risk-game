package logic.network;

import logic.Player;

import java.io.Serializable;
import java.util.Collection;

public class Message implements Serializable {

    private String msg;
    private Collection<Player> players;
    public final MessageType type;

    Message(MessageType type, String username) throws Exception {
        if (type != MessageType.USERNAME) {
            throw new Exception("Message type exception"); // todo
        }
        this.type = type;
        this.msg = username;
    }

    Message(MessageType type) {
        this.type = type;
    }

    Message(MessageType type, Collection<Player> players) throws Exception {
        if (type != MessageType.PLAYERS) {
            throw new Exception("Message type exception"); // todo
        }
        this.type = type;
        this.players = players;
    }

    public String getMsg() {
        return msg;
    }

    public String toString() {
        return "Message[type= " + type + ", msg= " + msg + "]";
    }
}
