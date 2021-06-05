package gui.multiplayer_menu;

import gui.HintTextField;
import gui.player_menu.PlayerMenu;
import logic.network.MultiplayerManager;
import logic.network.NetworkMode;
import util.res.Fonts;
import util.res.Images;
import util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// todo add setKeyListener method to manipulate with mouse
public class ServerMenu extends JPanel {

    private HintTextField portField, nameField;
    private final JFrame frame;
    private final JPanel panel;
    private JButton enterButton, backButton;
    private GridBagConstraints gbc;

    private final int SIZE = 500;
    private int menuOptionChosen;

    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    private String username;
    private int portNumber;

    ServerMenu(JFrame frame) {
        this.frame = frame;
        panel = this;

        initWindow();
        initTextFields();
        initButtons();
    }

    private void initWindow() {
        setFocusable(true);
        setPreferredSize(new Dimension(SIZE,SIZE));
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(85,0,-70,0);
    }

    private void initTextFields() {
        ArrayList<JTextField> fields = new ArrayList<>();
        nameField = new HintTextField("Enter username", 20);
        fields.add(nameField);
        portField = new HintTextField("Enter port number", 20);
        fields.add(portField);

        for (JTextField field : fields) {
            field.setPreferredSize(new Dimension(SIZE/2,35));
            field.setFont(Fonts.BUTTON_FONT.deriveFont(20f));
            add(field, gbc);
        }
    }

    private void initButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        enterButton = new JButton("Create room");
        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                if (!getServerInfo()) {
                    JOptionPane.showMessageDialog(null,
                            "Enter all the information carefully.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    openPlayerMenu();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 3;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(enterButton);

        backButton = new JButton("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                back();
                SoundPlayer.buttonClickedSound();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 4;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(backButton);

        for (JButton button: buttons) {
            button.setFont(LABEL_FONT);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            add(button,gbc);
        }
    }

    private void highlightOption(int option) {
        resetButtons();
        switch (option) {
            case 3:
                enterButton.setForeground(Color.YELLOW);
                enterButton.setText("< Create room >");
                SoundPlayer.optionChosenSound();
                break;
            case 4:
                backButton.setForeground(Color.YELLOW);
                backButton.setText("< Back >");
                SoundPlayer.optionChosenSound();
                break;
        }
    }

    private void resetButtons() {
        enterButton.setForeground(Color.WHITE);
        enterButton.setText("Create room");

        backButton.setForeground(Color.WHITE);
        backButton.setText("Back");
    }

    private void openPlayerMenu() {
        MultiplayerManager multiplayerManager = new MultiplayerManager(NetworkMode.SERVER);
        PlayerMenu pm = new PlayerMenu(frame, multiplayerManager);

        System.out.println("ServerMenu.openPlayerMenu: " + pm.getPlayers());

        multiplayerManager.setPlayerMenu(pm);
        multiplayerManager.startServer(portNumber, username, frame);

        setVisible(false);
        frame.remove(this);
    }

    private boolean getServerInfo() {
        username = nameField.getText();
        try {
            portNumber = Integer.parseInt(portField.getText());
            if (portNumber < 1 || portNumber >= 0xFFFF) {
                throw new Exception("Wrong port number");
            }
        } catch (Exception e) {
            return false;
        }

        if (username.length() == 0) {
            return false;
        }

        return true;
    }

    private void back() {
        panel.setVisible(false);
        frame.remove(panel);
        frame.add(new MultiplayerMenu(frame));
        frame.pack();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Images.MAIN_MENU_BG, 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(Images.MENU_PANEL, SIZE/6, SIZE/3 + SIZE/40, 3*SIZE/4 - SIZE / 12, SIZE/2+SIZE/20+SIZE/40, null);
    }
}

