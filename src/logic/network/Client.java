package logic.network;

import gui.menus.main_menu.MainMenu;

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

    public void close(MessageType cause) {
        try {
            isClosed = true;
            socket.close();

            if (cause != MessageType.GAME_OVER) {
                openMainMenu();
                JOptionPane.showMessageDialog(null,
                        "Connection closed: " + cause,
                        "Lost connection with server",JOptionPane.ERROR_MESSAGE);
            }
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
        manager.frame.getContentPane().removeAll();
        manager.frame.add(new MainMenu(manager.frame));
        manager.frame.getContentPane().repaint();
        manager.frame.pack();
    }
}
