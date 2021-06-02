package logic.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;

    public ReadThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            //ex.printStackTrace();
        }
    }

    public void run() {
        int first_response = -1;
        try {
            first_response = Integer.parseInt(reader.readLine());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if (first_response == ChatServer.NAME_ERROR) {
            System.out.println("Name " + client.getUserName() + " is already occupied");
            client.close();
        } else if (first_response != ChatServer.OK) {
            System.out.println("Error occurred while connecting to the room.");
            client.close();
        } else {
            while (true) {
                try {
                    System.out.println("Client waiting for message...");
                    String response = reader.readLine();
                    System.out.println("\n" + response);

                    // prints the username after displaying the server's message
                    if (client.getUserName() != null) {
                        System.out.print(response);
                        System.out.println(response);
                    }
                } catch (IOException ex) {
                    System.out.println("Error reading from server: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }
            }
        }
    }
}
