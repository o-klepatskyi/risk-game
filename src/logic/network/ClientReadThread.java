package logic.network;

import util.exceptions.*;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import static logic.network.MessageType.*;

public class ClientReadThread extends Thread {
    private ObjectInputStream objectInputStream;
    private final Client client;

    public ClientReadThread(Socket socket, Client client) {
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            objectInputStream = new ObjectInputStream(input);
        } catch (IOException ex) {
            System.err.println("Error getting client input stream: " + ex.getMessage());
            //ex.printStackTrace();
        }
    }

    public void run() {
        Message first_response = null;
        try {
            first_response = (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            showConnectionErrorMessage();
            client.close();
            return;
        }

        if (first_response == null || first_response.type == NAME_ERROR) {
            showDuplicateNameError();
            client.close();
            return;
        }
        if (first_response.type == INVALID_NAME) {
            showInvalidNameError();
            client.close();
            return;
        }
        if (first_response.type != OK) {
            showConnectionErrorMessage();
            client.close();
            return;
        }
        client.openPlayerMenu();
        mainCycle();

    }

    private void showInvalidNameError() {
        JOptionPane.showMessageDialog(null,
                "Name " + client.getUserName() + " can not be used. Please choose another username and reconnect.",
                "Invalid username",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showDuplicateNameError() {
        JOptionPane.showMessageDialog(null,
                "Name " + client.getUserName() + " is already occupied",
                "Duplicate username",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showConnectionErrorMessage() {
        JOptionPane.showMessageDialog(null,
                "Error occurred while connecting to the room.",
                "Connection error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void mainCycle() {
        try {
            while (true) {
                System.out.println(client.username + " is waiting for message...");
                Message response = (Message) objectInputStream.readObject();
                System.out.println(client.username + " received: " + response);

                MessageType type = response.type;

                if (type == PLAYERS) {
                    client.manager.updatePlayerMenu(response.players);
                }
                if (type == START_GAME) {
                    client.manager.startGame(response.gameGraph);
                }
                if (type == CONNECTION_CLOSED_BY_ADMIN) {
                    client.sendMessage(new Message(CLOSE_CONNECTION));
                    client.close();
                    break;
                }
                if (type == REINFORCE) {
                    client.manager.game.reinforce(response.troops, response.src);
                }
                if (type == END_REINFORCE || response.type == END_ATTACK) {
                    client.manager.game.getGameWindow().nextPhase();
                }
                if (type == ATTACK) {
                    client.manager.game.attack(response.srcName, response.srcTroops, response.srcOwner, response.dstName, response.dstTroops, response.dstOwner);
                }
                if (type == FORTIFY) {
                    client.manager.game.getGameWindow().fortify(response.src, response.dst, response.troops);
                }
            }
        } catch (SocketException ex) {
            System.err.println("Socket closed.");
        }
        catch (IOException | ClassNotFoundException | WrongTerritoriesPairException | IllegalNumberOfFortifyTroopsException | SrcNotStatedException | DstNotStatedException ex) {
            System.out.println("Error reading from server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
