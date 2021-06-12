package com.risk.logic.network;

import java.io.*;
import java.net.Socket;
import static com.risk.logic.network.MessageType.*;

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

            if (userNameMsg == null || userNameMsg.type != USERNAME) {
                sendMessage(new Message(CONNECTION_CLOSED_BY_ADMIN));
            } else if (userNameMsg.username.equals(MultiplayerManager.BOT_NAME)) {
                sendMessage(new Message(INVALID_NAME_ERROR));
            } else if(server.hasUser(userNameMsg.username)) {
                sendMessage(new Message(DUPLICATE_NAME_ERROR));
            } else if (server.manager.getPlayers().size() == 6) {
                sendMessage(new Message(MAX_PLAYERS_ERROR));
            } else if (server.manager.isGameStarted()) {
                sendMessage(new Message(GAME_STARTED_ERROR));
            } else {
                this.username = userNameMsg.username;
                sendMessage(new Message(OK));
                updateUsers(username);
                sendMessage(new Message(MAP_CHANGED, server.manager.getCurrentMapInComboBox()));

                Message clientMessage;
                do {
                    System.out.println(userNameMsg.username + " thread listening...");
                    clientMessage = (Message) objectInputStream.readObject();
                    System.out.println("Server thread received: " + clientMessage);
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
                        type == FORTIFY ||
                        type == END_FORTIFY ||
                        type == MAP_CHANGED) {
                        server.broadcast(clientMessage, this);
                    }
                } while (clientMessage.type != CLOSE_CONNECTION_BY_CLIENT);
            }
            socket.close();
            server.removeUser(username, this);
            System.out.println("User '" + username + "' left server.");
        } catch (Exception ex) {
            System.err.println("Error in UserThread " + username + ": " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void updateUsers(String username) throws IOException {
        server.addUserName(username);
        server.broadcast(getUsersMessage());
    }

    public Message getUsersMessage() {
        return new Message(MessageType.PLAYERS, server.manager.getPlayers());
    }

    /**
     * Sends a message to the client.
     */
    void sendMessage(Message message) throws IOException {
        if (!socket.isClosed()) {
            System.out.println(username + " thread sends: " + message);
            objOutputStream.writeObject(message);
        }
    }
}
