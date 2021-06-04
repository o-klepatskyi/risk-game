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
            System.err.println("Error getting client input stream: " + ex.getMessage());
            //ex.printStackTrace();
        }
    }

    public void run() {
        Message first_response = null;
        try {
            first_response = (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            showConnectionErrorMessage();
            client.openMainMenu();
            client.close();
            return;
        }
        if (first_response == null || first_response.type == NAME_ERROR) {
            showDuplicateNameError();
            client.openMainMenu();
            client.close();
        } else if (first_response.type == INVALID_NAME) {
            showInvalidNameError();
            client.openMainMenu();
            client.close();
        } else if (first_response.type != OK) {
            showConnectionErrorMessage();
            client.openMainMenu();
            client.close();
        } else {
            client.openPlayerMenu();
            mainCycle();
        }
    }

    private void showInvalidNameError() {
        JOptionPane.showMessageDialog(null,
                "Name " + client.getUserName() + " can not be used. Please choose another username and reconnect.",
                "Invalid username",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showDuplicateNameError() {
        JOptionPane.showMessageDialog(null,
                "Name " + client.getUserName() + " is already occupied",
                "Duplicate username",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showConnectionErrorMessage() {
        JOptionPane.showMessageDialog(null,
                "Error occurred while connecting to the room.",
                "Connection error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void mainCycle() {
        while (true) {
            try {
                System.out.println(client.username + " is waiting for message...");
                Message response = (Message) objectInputStream.readObject();
                System.out.println(client.username + " received: " + response);
                if (response.type == PLAYERS) {
                    client.manager.updatePlayerMenu(response.players);
                }
                if (response.type == START_GAME) {
                    client.manager.startGame(response.gameGraph);
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
