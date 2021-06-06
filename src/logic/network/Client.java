package logic.network;

import gui.main_menu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostname;
    private int port;
    public final String username;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    final MultiplayerManager manager;

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
            System.err.println("Server not found with IP-address " + ex.getMessage());
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "Server not found",
                    JOptionPane.ERROR_MESSAGE);
            openMainMenu();
        } catch (IOException ex) {
            System.err.println("I/O Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
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

    public void close() {
        try {
            socket.close();
            openMainMenu();
            JOptionPane.showMessageDialog(null,
                    "Connection closed",
                    "Lost connection",JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            System.err.println("Error writing to server: " + ex.getMessage());
        }
    }

    public void openPlayerMenu() {
       manager.frame.add(manager.playerMenu);
       manager.frame.revalidate();
       manager.frame.repaint();
       manager.frame.pack();
    }

    public void openMainMenu() {
        if (manager.frame.isAncestorOf(manager.playerMenu)) {
            manager.frame.remove(manager.playerMenu);
        }
        manager.frame.add(new MainMenu(manager.frame));
        manager.frame.pack();
    }
}
