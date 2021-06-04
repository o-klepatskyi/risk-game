package gui.multiplayer_menu;

import gui.HintTextField;
import gui.player_menu.PlayerMenu;
import gui.player_menu.PlayerNameField;
import logic.Game;
import logic.network.MultiplayerManager;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// todo: design
public class ServerMenu extends JPanel {

    private HintTextField portField;
    private HintTextField nameField;
    private final JFrame frame;

    private final MultiplayerManager multiplayerManager = new MultiplayerManager(new Game());

    private String username;
    private int portNumber;

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
        enterButton.setVisible(true);
        return enterButton;
    }

    private void openPlayerMenu() {
        PlayerMenu pm = new PlayerMenu(frame, multiplayerManager);

        multiplayerManager.setPlayerMenu(pm);
        multiplayerManager.startServer(portNumber, username, frame);

        setVisible(false);
        frame.remove(this);
    }

    private boolean getServerInfo() {
        username = nameField.getText();
        try {
            portNumber = Integer.parseInt(portField.getText());
        } catch (Exception e) {
            return false;
        }

        if (username.length() == 0) {
            return false;
        }

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
                currentMenu.setVisible(false);
                frame.remove(currentMenu);
                frame.add(new MultiplayerMenu(frame));
                frame.revalidate();
                frame.repaint();
                frame.pack();
            }
        });
        return backButton;
    }

    private HintTextField getNameField() {
        if (nameField == null) {
            nameField = new HintTextField("Enter username");
            nameField.setSize(new Dimension(150,35));
            nameField.setDocument(new PlainDocument() {
                @Override
                public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                    if (str == null)
                        return;

                    if ((getLength() + str.length()) <= PlayerNameField.MAX_CHARACTERS) {
                        super.insertString(offset, str, attr);
                    }
                }
            });
        }
        return nameField;
    }
}

