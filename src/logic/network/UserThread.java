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
            if (userName.type != MessageType.USERNAME || server.hasUser(userName.getMsg())) {
                sendMessage(new Message(MessageType.NAME_ERROR));
            } else {
                sendMessage(new Message(MessageType.OK));
                printUsers();
                server.addUserName(userName.getMsg());
                Message serverMessage, clientMessage;

                do {
                    System.out.println(userName + " thread listening...");
                    clientMessage = (Message) objectInputStream.readObject();
                    serverMessage = new Message(MessageType.USERNAME, "kek");
                    server.broadcast(serverMessage, null);
                } while (!clientMessage.equals(MessageType.CLOSE_CONNECTION.toString()));

                server.removeUser(userName.getMsg(), this);
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
    void printUsers() throws IOException {
        if (server.hasUsers()) {
            objOutputStream.writeObject("Connected users: " + server.getUserNames());
        } else {
            objOutputStream.writeObject("No other users connected");
        }
    }

    /**
     * Sends a message to the client.
     */
    void sendMessage(Message message) throws IOException {
        objOutputStream.writeObject(message);
    }
}
