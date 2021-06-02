package logic.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private final ChatClient client;

    public ReadThread(Socket socket, ChatClient client) {
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
        String first_response = null;
        try {
            first_response = reader.readLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if (first_response == null || first_response.equals(NetworkMessage.NAME_ERROR.toString())) {
            System.out.println("Name " + client.getUserName() + " is already occupied");
            client.close();
        } else if (!first_response.equals(NetworkMessage.OK.toString())) {
            System.out.println("Error occurred while connecting to the room.");
            client.close();
        } else {
            while (true) {
                try {
                    System.out.println("Client waiting for message...");
                    String response = reader.readLine();
                    System.out.println("\n" + response);

                    if (client.getUserName() != null) {
                        //System.out.print(response);
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
