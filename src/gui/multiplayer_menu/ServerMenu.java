package gui.multiplayer_menu;

import gui.HintTextField;
import gui.player_menu.PlayerMenu;
import logic.Game;
import logic.network.MultiplayerManager;
import logic.network.NetworkMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// todo: design
public class ServerMenu extends JPanel {

    private HintTextField portField;
    private HintTextField nameField;
    private final JFrame frame;

    private final MultiplayerManager multiplayerManager = new MultiplayerManager(new Game());

    ServerMenu(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(500,500));
        setLayout(new GridLayout(0,1,15,15));
        add(new FillerButton(100,0));
        add(getNameField());
        add(getPortNumberField());
        add(getEnterButton());
        add(getBackButton());
        setVisible(true);
    }

    private JButton getEnterButton() {
        JButton enterButton = new JButton("Create room");
        enterButton.setSize(new Dimension(100,35));
        enterButton.setVisible(true);
        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!getServerInfo()) {
                    JOptionPane.showMessageDialog(null,
                            "Enter all the information carefully.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    openPlayerMenu();
                }
            }
        });
        return enterButton;
    }

    private void openPlayerMenu() {
        frame.remove(this);
        frame.add(new PlayerMenu(frame, multiplayerManager));
        frame.pack();
    }

    private boolean getServerInfo() {
        String username = nameField.getText();
        int portNumber;
        try {
            portNumber = Integer.parseInt(portField.getText());
        } catch (Exception e) {
            return false;
        }

        if (username.length() == 0) {
            return false;
        }

        multiplayerManager.startServer(portNumber, username);

        return true;
    }

    private JTextField getPortNumberField() {
        if (portField == null) {
            portField = new HintTextField("Enter port number");
            portField.setSize(150,35);
        }
        return portField;
    }

    private JButton getBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setSize(new Dimension(100,35));
        backButton.setVisible(true);
        JPanel currentMenu = this;
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.remove(currentMenu);
                frame.add(new MultiplayerMenu(frame));
                frame.pack();
            }
        });
        return backButton;
    }

    private HintTextField getNameField() {
        if (nameField == null) {
            nameField = new HintTextField("Enter username");
            nameField.setSize(new Dimension(150,35));
        }
        return nameField;
    }
}

