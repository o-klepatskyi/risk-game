package logic.network;

public final class MultiplayerManager {
    private ChatServer server;
    public ChatClient client;

    public final NetworkMode networkMode;

    public MultiplayerManager(NetworkMode networkMode) {
        this.networkMode = networkMode;
    }

    public void startServer(int portNumber, String userName) {
        if (networkMode != NetworkMode.SERVER) {
            System.err.println("Wrong network mode.");
            return;
        }
        if (server != null) {
            System.err.println("Server is already activated.");
            return;
        }
        server = new ChatServer(portNumber);
        new Thread(() -> server.execute()).start();
        startClient("127.0.0.1", portNumber, userName);
    }

    public void startClient(String ipAddress, int portNumber, String username) {
        if (client != null) {
            System.err.println("Client is already activated.");
            return;
        }
        client = new ChatClient(ipAddress, portNumber, username);
        new Thread(() -> client.execute()).start();
    }
}
