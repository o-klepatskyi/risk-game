package logic.network;

public class Multiplayer {
    private static ChatServer server;
    private static ChatClient client;

    public static void startServer(int portNumber, String userName) {
        server = new ChatServer(portNumber);
        new Thread(() -> server.execute()).start();
        startClient("127.0.0.1", portNumber, userName);
    }

    public static void startClient(String ipAddress, int portNumber, String username) {
        client = new ChatClient(ipAddress, portNumber, username);
        new Thread(() -> client.execute()).start();
    }

    public static void sendMessage(String msg) {
        client.sendMessage(msg);
    }
}
