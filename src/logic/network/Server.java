package logic.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private int port;
    private MultiplayerManager manager;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public Server(int port, MultiplayerManager manager) {
        this.manager = manager;
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port);){

            System.out.println("Chat Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();

                // todo: close connection to the server with back button
            }
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Delivers a message from one user to others (broadcasting)
     */
    void broadcast(Message message, UserThread excludeUser) throws IOException {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    /**
     * Stores username of the newly connected client.
     */
    boolean addUserName(String userName) {
        return userNames.add(userName);
    }

    /**
     * When a client is disconnected, removes the associated username and UserThread
     */
    void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }

    public boolean hasUser(String userName) {
        return userNames.contains(userName);
    }
}