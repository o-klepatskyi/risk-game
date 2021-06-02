package logic.network;

public final class MultiplayerManager {
    private ChatServer server;
    public ChatClient client;

    public final NetworkMode networkMode;

    public MultiplayerManager(NetworkMode networkMode) {
        this.networkMode = networkMode;
    }

    public void startServer(int portNumber, String userName) {
        server = new ChatServer(portNumber);
        new Thread(() -> server.execute()).start();
        startClient("127.0.0.1", portNumber, userName);
    }

    public void startClient(String ipAddress, int portNumber, String username) {
        client = new ChatClient(ipAddress, portNumber, username);
        new Thread(() -> client.execute()).start();
    }
}
