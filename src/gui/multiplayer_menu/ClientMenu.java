package gui.multiplayer_menu;

import gui.HintTextField;
import gui.player_menu.PlayerMenu;
import gui.player_menu.PlayerNameField;
import logic.network.MultiplayerManager;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// todo: design
// todo: similar methods to parent class
public class ClientMenu extends JPanel {

    private HintTextField portField;
    private HintTextField ipField;
    private HintTextField nameField;
    private final JFrame frame;

    private String username;
    private String ipAddress;
    private int portNumber;

    private final MultiplayerManager multiplayerManager = new MultiplayerManager();

    ClientMenu(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(500,500));
        setLayout(new GridLayout(0,1,15,15));
        add(new FillerButton(100,0));
        add(getNameField());
        add(getIPField());
        add(getPortNumberField());
        add(getEnterButton());
        add(getBackButton());
        add(new FillerButton(100,0));
        setVisible(true);
    }

    private void openPlayerMenu() {
        PlayerMenu pm = new PlayerMenu(frame, multiplayerManager);

        multiplayerManager.setPlayerMenu(pm);
        multiplayerManager.startClient(ipAddress, portNumber, username, frame);
        setVisible(false);
        frame.remove(this);
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

    private JTextField getIPField() {
        if (ipField == null) {
            ipField = new HintTextField("Enter IP address");
            ipField.setSize(new Dimension(150,35));
        }
        return ipField;
    }

    private JButton getEnterButton() {
        JButton enterButton = new JButton("Connect");
        enterButton.setSize(new Dimension(150,35));
        enterButton.setVisible(true);
        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!getClientInfo()) {
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

    private boolean getClientInfo() {
        username = nameField.getText();
        ipAddress = ipField.getText();
        try {
            portNumber = Integer.parseInt(portField.getText());
        } catch (Exception e) {
            return false;
        }

        if (username.length() == 0 || ipAddress.length() == 0) {
            return false;
        }

        return true;
    }

    private JTextField getPortNumberField() {
        if (portField == null) {
            portField = new HintTextField("Enter port number");
            portField.setSize(new Dimension(100,35));
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
}

