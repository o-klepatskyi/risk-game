package logic.network;

import gui.Main;
import gui.player_menu.PlayerMenu;
import logic.Game;
import logic.Player;
import logic.maps.Map;
import logic.maps.MapType;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static logic.network.NetworkMode.*;
import static logic.network.MessageType.*;

public final class MultiplayerManager {
    public Server server;
    public Client client;

    public final NetworkMode networkMode;
    public Game game;
    public PlayerMenu playerMenu;
    public ArrayList<Player> players;
    private boolean isGameStarted = false;

    public static final String BOT_NAME = "Bot";

    public MultiplayerManager(NetworkMode mode) {
        this.networkMode = mode;
    }

    public void startServer(int portNumber, String userName) {
        if (networkMode != SERVER) {
            throw new InvalidParameterException("Wrong network mode");
        }
        server = new Server(portNumber, this);
        new Thread(() -> {
            try {
                server.execute();
            } catch (IOException ignored) {
                Main.openMainMenu();
                JOptionPane.showMessageDialog(null,
                        "Error occurred while setting up the server. Please restart the game.",
                        "Server error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }).start();
        System.out.println(server == null);
        startClient("127.0.0.1", portNumber, userName);
    }

    public void startClient(String ipAddress, int portNumber, String username) {
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
        if (networkMode == SERVER) {
            try {
                server.broadcast(new Message(CONNECTION_CLOSED_BY_ADMIN));
                server.isClosed = true;
                new Socket("127.0.0.1", server.port); // to close the cycle in Server class
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeClient() {
        sendMessage(new Message(CLOSE_CONNECTION_BY_CLIENT));
        client.close(CLOSE_CONNECTION_BY_CLIENT);
        if (networkMode == SERVER) closeServer();
    }

    public void initGame() {
        players = new ArrayList<>(getPlayers());
        Map map = playerMenu.getSelectedMap();
        map.initGraph(players);
        if (Main.isShuffling()) Collections.shuffle(players);
        try {
            server.broadcast(new Message(START_GAME, map, players));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * client-only
     */
    public void startGame(Map map, Collection<Player> players) {
        game = new Game(players, map, this);
        isGameStarted = true;
        game.start();
        Main.openGameWindow(game.gameWindow);
        playerMenu = null;
    }

    public void gameOver() {
        if (networkMode == SERVER) {
            server.isClosed = true;
            try {
                new Socket("127.0.0.1", server.port); // to close the cycle in Server class
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            client.close(GAME_OVER);
        }
    }

    public void skipDisconnectedUserMove(String username) {
        if (game.getCurrentPlayer().getName().equals(username)) {
            System.out.println("it is " + username +"'s move. Skipping it.");
            game.nextPlayerTurn();
        }
    }

    public void changeMap(MapType mapType) {
        if (!isGameStarted) {
            playerMenu.changeMap(mapType);
        }
    }

    public MapType getCurrentMapInComboBox() {
        return playerMenu.getSelectedMapType();
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }
}
