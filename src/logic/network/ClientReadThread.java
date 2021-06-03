package logic.network;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import static logic.network.MessageType.*;

public class ClientReadThread extends Thread {
    private ObjectInputStream objectInputStream;
    private final Client client;

    public ClientReadThread(Socket socket, Client client) {
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
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
            JOptionPane.showMessageDialog(null,
                    "Name " + client.getUserName() + " is already occupied",
                    "Duplicate username",
                    JOptionPane.ERROR_MESSAGE);
            client.openMainMenu();
            client.close();
        } else if (!first_response.type.equals(MessageType.OK)) {
            System.out.println("Error occurred while connecting to the room.");
            JOptionPane.showMessageDialog(null,
                    "Error occurred while connecting to the room.",
                    "Connection error",
                    JOptionPane.ERROR_MESSAGE);
            client.openMainMenu();
            client.close();
        } else {
            client.openPlayerMenu();
            while (true) {
                try {
                    System.out.println(client.username + " is waiting for message...");
                    Message response = (Message) objectInputStream.readObject();
                    System.out.println(client.username + " received: " + response);
                    if (response.type == PLAYERS) {
                        client.manager.updatePlayerMenu(response.players);
                    }
                    if (response.type == CONNECTION_CLOSED_BY_ADMIN) {
                        client.sendMessage(new Message(CLOSE_CONNECTION));
                        client.close();
                        break;
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Error reading from server: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }
            }
        }
    }
}
