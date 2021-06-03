package logic.network;

import gui.player_menu.PlayerMenu;
import logic.Game;
import logic.Player;

import javax.swing.*;
import java.util.Collection;

public final class MultiplayerManager {
    private static Server server;
    public Client client;

    public final NetworkMode networkMode;
    public Game game;
    public PlayerMenu playerMenu;
    public JFrame frame;

    public MultiplayerManager(Game game) {
        this.game = game;
        this.networkMode = NetworkMode.SERVER;
    }

    public MultiplayerManager() {
        this.networkMode = NetworkMode.CLIENT;
    }

    public void startServer(int portNumber, String userName, JFrame frame) {
        if (networkMode != NetworkMode.SERVER) {
            System.err.println("Wrong network mode."); // todo exceptions
            return;
        }
        if (server != null) {
            System.err.println("Server is already activated.");
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
        //game.addPlayer();
        System.out.println("Adding the same player in manager...");
        playerMenu.addPlayer(username);
    }

    public static boolean canStart() {
        return server == null;
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
}
