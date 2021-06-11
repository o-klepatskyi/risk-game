package logic.network;

import gui.Main;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private final String hostname;
    private final int port;
    public final String username;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    final MultiplayerManager manager;

    boolean isClosed = false;

    public Client(String hostname, int port, String userName, final MultiplayerManager manager) {
        this.hostname = hostname;
        this.port = port;
        this.username = userName.trim();
        this.manager = manager;
    }

    public void execute() {
        try {
            socket = new Socket(hostname, port);

            OutputStream output = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(output);

            objectOutputStream.writeObject(new Message(MessageType.USERNAME, username));

            new ClientReadThread(socket, this).start();
            System.out.println("User '" + username + "' connected to the server");
        } catch (UnknownHostException ex) {
            System.err.println("Server not found: " + ex.getMessage());
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "Server not found",
                    JOptionPane.ERROR_MESSAGE);
            openMainMenu();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Connection refused on address " + hostname + ":" + port,
                    "I/O Error",
                    JOptionPane.ERROR_MESSAGE);
            openMainMenu();
        }
    }

    String getUserName() {
        return this.username;
    }

    public void sendMessage(Message msg) {
        System.out.println(username + " sends message: " + msg);
        try {
            objectOutputStream.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(MessageType cause) {
        try {
            isClosed = true;
            socket.close();

            if (cause != MessageType.GAME_OVER) {
                openMainMenu();
                switch (cause) {
                    case CONNECTION_ERROR -> showConnectionErrorMessage();
                    case DUPLICATE_NAME_ERROR -> showDuplicateNameError();
                    case INVALID_NAME -> showInvalidNameError();
                    case CONNECTION_CLOSED_BY_ADMIN -> showConnectionClosedByAdminError();
                    case CLOSE_CONNECTION_BY_CLIENT -> showConnectionClosedByClientError();
                    // todo SERVER_CLOSED_ERROR
                    default -> showError(cause);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error writing to server: " + ex.getMessage());
        }
    }

    private void showConnectionClosedByClientError() {
        JOptionPane.showMessageDialog(null,
                "Connection closed with server.",
                "Lost connection with server", JOptionPane.ERROR_MESSAGE);
    }

    private void showConnectionClosedByAdminError() {
        JOptionPane.showMessageDialog(null,
                "Connection closed by admin.",
                "Lost connection with server", JOptionPane.ERROR_MESSAGE);
    }

    private void showError(MessageType cause) {
        JOptionPane.showMessageDialog(null,
                "Connection closed: " + cause,
                "Lost connection with server", JOptionPane.ERROR_MESSAGE);
    }

    public void openPlayerMenu() {
        Main.openPlayerMenu();
    }

    public void openMainMenu() {
        Main.openMainMenu();
    }

    private void showInvalidNameError() {
        JOptionPane.showMessageDialog(null,
                "Name " + getUserName() + " can not be used.\nPlease choose another username and reconnect.",
                "Invalid username",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showDuplicateNameError() {
        JOptionPane.showMessageDialog(null,
                "Name " + getUserName() + " is already occupied",
                "Duplicate username",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showConnectionErrorMessage() {
        JOptionPane.showMessageDialog(null,
                "Error occurred while connecting to the room.",
                "Connection error",
                JOptionPane.ERROR_MESSAGE);
    }
}
