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
    public final Set<String> userNames = new HashSet<>();
    private final Set<UserThread> userThreads = new HashSet<>();

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
    public void broadcast(Message message, UserThread excludeUser) throws IOException {
        for (UserThread userThread : userThreads) {
            if (userThread != excludeUser) {
                userThread.sendMessage(message);
            }
        }
    }

    public void broadcast(Message message) throws IOException {
        broadcast(message, null);
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
            if (manager.playerMenu != null) {
                manager.playerMenu.removePlayer(userName);
                try {
                    broadcast(new Message(MessageType.PLAYERS, manager.getPlayers()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    broadcast(new Message(MessageType.PLAYER_LEFT_IN_GAME, userName));
                } catch (IOException e) {
                    System.err.println("Couldn't broadcast message USER_LEFT in server.removeUser()");
                    //e.printStackTrace();
                }
            }

        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    public boolean hasUser(String userName) {
        return userNames.contains(userName);
    }

    public void sendMessage(Message message, String username) throws IOException {
        UserThread thread = getThreadByName(username);
        if (thread != null) thread.sendMessage(message);
    }

    public UserThread getThreadByName(String username) {
        for (UserThread userThread : userThreads) {
            if (userThread.username.equals(username)) {
                return userThread;
            }
        }
        return null;
    }
}