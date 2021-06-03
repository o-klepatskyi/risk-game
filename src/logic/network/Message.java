package logic.network;

import java.io.Serializable;

public class Message implements Serializable {

    private String msg;
    public final MessageType type;

    Message(MessageType type, String username) throws Exception {
        if (type != MessageType.USERNAME) {
            throw new Exception("Message exception"); // todo
        }
        this.type = type;
        msg = username;
    }

    Message(MessageType type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }
}
