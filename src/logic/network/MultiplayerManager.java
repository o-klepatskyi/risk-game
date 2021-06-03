package logic.network;

import logic.Game;
import logic.Player;

public final class MultiplayerManager {
    private Server server;
    public Client client;

    public final NetworkMode networkMode;
    public Game game;

    public MultiplayerManager(Game game) {
        this.game = game;
        this.networkMode = NetworkMode.SERVER;
    }

    public MultiplayerManager() {
        this.networkMode = NetworkMode.CLIENT;
    }

    public void startServer(int portNumber, String userName) {
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
        startClient("127.0.0.1", portNumber, userName);
        game.addPlayer(new Player(userName, game.colorModel.chooseFirstAvailableColor(), false)); // todo
    }

    public void startClient(String ipAddress, int portNumber, String username) {
        if (client != null) {
            System.err.println("Client is already activated.");
            return;
        }
        client = new Client(ipAddress, portNumber, username);
        new Thread(() -> client.execute()).start();
    }

    public void sendMessage(Message msg) {
        client.sendMessage(msg);
    }

    void addPlayer(Player p) {
        game.addPlayer(p);
    }


    public void close() {
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
