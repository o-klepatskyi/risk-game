package logic.network;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    public boolean isClosed = false;
    public final int port;
    MultiplayerManager manager;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public Server(int port, MultiplayerManager manager) {
        this.manager = manager;
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)){

            System.out.println("Chat Server is listening on port " + port);

            while (!isClosed) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error occurred while setting up the server. Please restart the game.",
                    "Server error",
                    JOptionPane.ERROR_MESSAGE);
            System.err.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }

        System.out.println("Server closed.");
    }

    /**
     * Delivers a message from one user to others (broadcasting)
     */
    void broadcast(Message message, UserThread excludeUser) throws IOException {
        for (UserThread userThread : userThreads) {
            if (userThread != excludeUser) {
                userThread.sendMessage(message);
            }
        }
    }

    /**
     * Stores username of the newly connected client.
     */
    void addUserName(String userName) {
        System.out.println("Adding username '" + userName + "'");
        userNames.add(userName);
        manager.addPlayer(userName);
    }

    /**
     * When a client is disconnected, removes the associated username and UserThread
     */
    void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            manager.playerMenu.removePlayer(userName);
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    public boolean hasUser(String userName) {
        return userNames.contains(userName);
    }

    public void sendMessage(Message message, String username) throws IOException {
        for (UserThread userThread : userThreads) {
            if (userThread.username.equals(username)) {
                userThread.sendMessage(message);
            }
        }
    }
}