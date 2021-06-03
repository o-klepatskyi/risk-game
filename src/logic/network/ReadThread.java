package logic.network;

import java.io.*;
import java.net.Socket;

public class ReadThread extends Thread {
    private ObjectInputStream objectInputStream;
    private final Client client;

    public ReadThread(Socket socket, Client client) {
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            //reader = new BufferedReader(new InputStreamReader(input));
            objectInputStream = new ObjectInputStream(input);
        } catch (IOException ex) {
            System.err.println("Error getting input stream: " + ex.getMessage());
            //ex.printStackTrace();
        }
    }

    public void run() {
        Message first_response = null;
        try {
            first_response = (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        if (first_response == null || first_response.type.equals(MessageType.NAME_ERROR)) {
            System.out.println("Name " + client.getUserName() + " is already occupied");
            client.close();
        } else if (!first_response.type.equals(MessageType.OK)) {
            System.out.println("Error occurred while connecting to the room.");
            client.close();
        } else {
            while (true) {
                try {
                    System.out.println("Client waiting for message...");
                    Message response = (Message) objectInputStream.readObject();
                    System.out.println(response);
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Error reading from server: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }
            }
        }
    }
}
