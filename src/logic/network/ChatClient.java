package logic.network;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private String hostname;
    private int port;
    private String userName;
    private PrintWriter writer;
    private Socket socket;

    public ChatClient(String hostname, int port, String userName) {
        this.hostname = hostname;
        this.port = port;
        this.userName = userName;
    }

    public void execute() {
        try {
            socket = new Socket(hostname, port);

            try {
                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);
            } catch (IOException ex) {
                System.out.println("Error getting output stream: " + ex.getMessage());
                ex.printStackTrace();
            }

            writer.println(userName);

            new ReadThread(socket, this).start();
            System.out.println("Connected to the chat server");
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }

    }

    String getUserName() {
        return this.userName;
    }

    public void sendMessage(String msg) {
        System.out.println(userName + " sends message: " + msg);
        writer.println(msg);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("Error writing to server: " + ex.getMessage());
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}
