package logic.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostname;
    private int port;
    private String userName;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    final MultiplayerManager manager;

    public Client(String hostname, int port, String userName, final MultiplayerManager manager) {
        this.hostname = hostname;
        this.port = port;
        this.userName = userName;
        this.manager = manager;
    }

    public void execute() {
        try {
            socket = new Socket(hostname, port);

            try {
                OutputStream output = socket.getOutputStream();
                objectOutputStream = new ObjectOutputStream(output);
            } catch (IOException ex) {
                System.err.println("Error getting output stream: " + ex.getMessage());
                ex.printStackTrace();
            }

            objectOutputStream.writeObject(new Message(MessageType.USERNAME, userName));

            new ClientReadThread(socket, this).start();
            System.out.println("User '" + userName + "' connected to the server");


            sendMessage(new Message(MessageType.OK));
        } catch (UnknownHostException ex) {
            System.err.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("I/O Error: " + ex.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    String getUserName() {
        return this.userName;
    }

    public void sendMessage(Message msg) {
        System.out.println(userName + " sends message: " + msg);
        try {
            objectOutputStream.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            System.err.println("Error writing to server: " + ex.getMessage());
        }
    }
}
