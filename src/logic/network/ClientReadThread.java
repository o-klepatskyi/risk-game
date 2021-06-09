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
        }
    }

    public void run() {
        Message first_response = null;
        try {
            first_response = (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            showConnectionErrorMessage();
            client.close(CONNECTION_ERROR);
            return;
        }

        if (first_response == null || first_response.type == NAME_ERROR) {
            showDuplicateNameError();
            client.close(NAME_ERROR);
            return;
        }
        if (first_response.type == INVALID_NAME) {
            showInvalidNameError();
            client.close(INVALID_NAME);
            return;
        }
        if (first_response.type != OK) {
            showConnectionErrorMessage();
            client.close(CONNECTION_ERROR);
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
                    client.manager.startGame(response.map);
                }
                if (type == CONNECTION_CLOSED_BY_ADMIN) {
                    client.sendMessage(new Message(CLOSE_CONNECTION_BY_CLIENT));
                    client.close(CONNECTION_CLOSED_BY_ADMIN);
                    break;
                }
                if (type == REINFORCE) {
                    client.manager.game.reinforce(response.troops, response.src);
                }
                if (type == END_REINFORCE || response.type == END_ATTACK || response.type == END_FORTIFY) {
                    client.manager.game.nextPhase();
                }
                if (type == ATTACK) {
                    client.manager.game.attack(response.srcName, response.srcTroops, response.srcOwner, response.dstName, response.dstTroops, response.dstOwner);
                }
                if (type == FORTIFY) {
                    client.manager.game.fortify(response.src, response.dst, response.troops);
                }
                if(type == SKIP_MOVE) {
                    String username = client.manager.game.getCurrentPlayer().getName();
                    client.manager.game.nextPlayerTurn();
                    JOptionPane.showMessageDialog(null,
                            "Player " + username + " has lost connection. Skipping his move.",
                            "Player disconnected.",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (type == PLAYER_LEFT_IN_GAME) {
                    client.manager.skipDisconnectedUserMove(response.username);
                    JOptionPane.showMessageDialog(null,
                            "Player " + response.username + " has lost connection.",
                            "Player disconnected.",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (type == MAP_CHANGED) {
                    client.manager.changeMap(response.mapType);
                }
            }
        } catch (SocketException ex) {
            System.err.println("Socket closed.");
            if (!client.isClosed) {
                client.close(SERVER_CLOSED_ERROR);
            }
        } catch(EOFException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException | ClassNotFoundException | WrongTerritoriesPairException | IllegalNumberOfFortifyTroopsException | SrcNotStatedException | DstNotStatedException ex) {
            System.out.println("Error reading from server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
