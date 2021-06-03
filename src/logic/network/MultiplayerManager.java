package logic.network;

import gui.player_menu.PlayerMenu;
import logic.Game;
import logic.Player;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import static logic.network.NetworkMode.*;

public final class MultiplayerManager {
    public Server server;
    public Client client;

    public final NetworkMode networkMode;
    public Game game;
    public PlayerMenu playerMenu;
    public JFrame frame;

    public MultiplayerManager(Game game) {
        this.game = game;
        this.networkMode = SERVER;
    }

    public MultiplayerManager() {
        this.networkMode = CLIENT;
    }

    public void startServer(int portNumber, String userName, JFrame frame) {
        if (networkMode != SERVER) {
            System.err.println("Wrong network mode."); // todo exceptions
            return;
        }
        server = new Server(portNumber, this);
        new Thread(() -> server.execute()).start();
        startClient("127.0.0.1", portNumber, userName, frame);
    }

    public void startClient(String ipAddress, int portNumber, String username, JFrame frame) {
        this.frame = frame;
        if (client != null) {
            System.err.println("Client is already activated.");
            return;
        }
        client = new Client(ipAddress, portNumber, username, this);
        new Thread(() -> client.execute()).start();
    }

    public void sendMessage(Message msg) {
        client.sendMessage(msg);
    }

    void addPlayer(String username) {
        playerMenu.addPlayer(username);
    }

    public Collection<Player> getPlayers() {
        return playerMenu.getPlayers();
    }

    public void setPlayerMenu(PlayerMenu playerMenu) {
        this.playerMenu = playerMenu;
    }

    public void updatePlayerMenu(Collection<Player> players) {
        playerMenu.updatePlayers(players);
    }

    public void closeServer() {
        try {
            server.broadcast(new Message(MessageType.CONNECTION_CLOSED_BY_ADMIN), null);
            server.isClosed = true;
            new Socket("127.0.0.1", server.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
