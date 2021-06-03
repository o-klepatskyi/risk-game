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


            Message userName = (Message) objectInputStream.readObject();
            System.out.println("Server thread received message: " + userName);
            if (userName.type != USERNAME || server.hasUser(userName.msg)) {
                sendMessage(new Message(NAME_ERROR));
            } else if (server.getUserNames().size() == 6) {
                sendMessage(new Message(MAX_PLAYERS_ERROR));
            } else {
                sendMessage(new Message(OK));
                server.addUserName(userName.msg);
                this.username = userName.msg;
                printUsers();
                Message clientMessage;

                do {
                    System.out.println(userName.msg + " thread listening...");
                    clientMessage = (Message) objectInputStream.readObject();
                    System.out.println("Server received: " + clientMessage);

                    if (clientMessage.type == COLOR_CHANGED || clientMessage.type == BOT_ADDED || clientMessage.type == PLAYER_DELETED) {
                        server.broadcast(new Message(PLAYERS, clientMessage.players), null);
                    }
                    if (clientMessage.type == CONNECTION_CLOSED_BY_ADMIN) {
                        server.sendMessage(new Message(CONNECTION_CLOSED_BY_ADMIN), clientMessage.msg);
                    }
                } while (clientMessage.type != CLOSE_CONNECTION);


                server.removeUser(username, this);
                socket.close();
                System.out.println("User '" + username + "' left server.");
            }
        } catch (Exception ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Sends a list of online users to the newly connected user.
     */
    void printUsers() throws Exception {
        objOutputStream.writeObject(getUsersMessage());
    }

    public Message getUsersMessage() throws Exception {
        return new Message(MessageType.PLAYERS, server.manager.getPlayers());
    }

    /**
     * Sends a message to the client.
     */
    void sendMessage(Message message) throws IOException {
        objOutputStream.writeObject(message);
    }
}
