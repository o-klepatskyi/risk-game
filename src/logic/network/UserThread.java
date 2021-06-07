package logic.network;

import java.io.*;
import java.net.Socket;
import static logic.network.MessageType.*;

public class UserThread extends Thread {
    private final Socket socket;
    private final Server server;
    public String username;
    private ObjectOutputStream objOutputStream;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            OutputStream output = socket.getOutputStream();
            objOutputStream = new ObjectOutputStream(output);

            Message userNameMsg = (Message) objectInputStream.readObject();

            if (userNameMsg.type != USERNAME) {
                sendMessage(new Message(CLOSE_CONNECTION));
            } else if (userNameMsg.username.equals(MultiplayerManager.BOT_NAME)) {
                sendMessage(new Message(INVALID_NAME));
            } else if(server.hasUser(userNameMsg.username)) {
                sendMessage(new Message(NAME_ERROR));
            } else if (server.getUserNames().size() == 6) {
                sendMessage(new Message(MAX_PLAYERS_ERROR));
            } else {
                server.addUserName(userNameMsg.username);
                this.username = userNameMsg.username;
                sendMessage(new Message(OK));
                printUsers();
                Message clientMessage;

                do {
                    System.out.println(userNameMsg.username + " thread listening...");
                    clientMessage = (Message) objectInputStream.readObject();
                    System.out.println("Server received: " + clientMessage);
                    MessageType type = clientMessage.type;
                    if (type == COLOR_CHANGED || type == BOT_ADDED || type == PLAYER_DELETED) {
                        server.broadcast(new Message(PLAYERS, clientMessage.players));
                    }
                    if (type == CONNECTION_CLOSED_BY_ADMIN) {
                        server.sendMessage(new Message(CONNECTION_CLOSED_BY_ADMIN), clientMessage.username);
                    }

                    if (type == REINFORCE ||
                        type == END_REINFORCE ||
                        type == END_ATTACK ||
                        type == ATTACK ||
                        type == FORTIFY) {
                        server.broadcast(clientMessage, this);
                    }
                } while (clientMessage.type != CLOSE_CONNECTION);


                server.removeUser(username, this);
                socket.close();
                System.out.println("User '" + username + "' left server.");
            }
        } catch (Exception ex) {
            System.err.println("Error in UserThread: " + ex.getMessage());
            //ex.printStackTrace();
            server.removeUser(username, this);
        }
    }

    /**
     * Sends a list of online users to the newly connected user.
     */
    void printUsers() throws Exception {
        objOutputStream.writeObject(getUsersMessage());
    }

    public Message getUsersMessage() {
        return new Message(MessageType.PLAYERS, server.manager.getPlayers());
    }

    /**
     * Sends a message to the client.
     */
    void sendMessage(Message message) throws IOException {
        System.out.println(username + " thread sends: " + message);
        objOutputStream.writeObject(message);
    }
}
