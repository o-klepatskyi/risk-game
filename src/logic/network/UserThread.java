package logic.network;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {
    private final Socket socket;
    private final Server server;
    private ObjectOutputStream objOutputStream;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            OutputStream output = socket.getOutputStream();
            objOutputStream = new ObjectOutputStream(output);

            //writer = new PrintWriter(output, true);

            Message userName = (Message) objectInputStream.readObject();
            System.out.println("Server thread received message: " + userName);
            if (userName.type != MessageType.USERNAME || server.hasUser(userName.msg)) {
                sendMessage(new Message(MessageType.NAME_ERROR));
            } else if (server.getUserNames().size() == 6) {
                sendMessage(new Message(MessageType.MAX_PLAYERS_ERROR));
            } else {
                sendMessage(new Message(MessageType.OK));
                server.addUserName(userName.msg);
                printUsers();
                Message serverMessage = null, clientMessage = null;

                do {
                    System.out.println(userName.msg + " thread listening...");
                    clientMessage = (Message) objectInputStream.readObject();
                    System.out.println("Server received: " + clientMessage);

                    if (clientMessage.type == MessageType.COLOR_CHANGED) {
                        server.broadcast(new Message(MessageType.PLAYERS, clientMessage.players), this);
                    }


                } while (clientMessage.type != MessageType.CLOSE_CONNECTION);

                server.removeUser(userName.msg, this);
                socket.close();

//                serverMessage = userName + " has quit.";
//                server.broadcast(serverMessage, this);
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
