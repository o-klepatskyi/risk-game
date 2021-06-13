package com.risk.gui.menus;

import com.risk.gui.common_elements.HintTextField;
import com.risk.gui.Main;
import com.risk.gui.player_menu.PlayerNameField;
import com.risk.util.resources.Fonts;
import com.risk.util.resources.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// todo add setKeyListener method to manipulate with mouse
public class ClientMenu extends Menu {
    private HintTextField portField, ipField, nameField;
    private JButton enterButton, backButton;
    private GridBagConstraints gbc;
    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    private String username, ipAddress;
    private int portNumber;
    private int menuOptionChosen;

    public ClientMenu() {
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
        nameField = new HintTextField("Enter username", PlayerNameField.MAX_CHARACTERS);
        fields.add(nameField);
        ipField = new HintTextField("Enter IP-address");
        fields.add(ipField);
        portField = new HintTextField("Enter port number");
        fields.add(portField);

        for (JTextField field : fields) {
            field.setPreferredSize(new Dimension(SIZE/2,35));
            field.setFont(Fonts.BUTTON_FONT.deriveFont(20f));
            add(field, gbc);
        }
    }

    private void initButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        enterButton = new JButton("Connect");
        enterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                if (!getClientInfo()) {
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
                menuOptionChosen = 4;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(enterButton);

        backButton = new JButton("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.openMultiplayerMenu();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 5;
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
            case 4:
                enterButton.setForeground(Color.YELLOW);
                enterButton.setText("< Connect >");
                SoundPlayer.optionChosenSound();
                break;
            case 5:
                backButton.setForeground(Color.YELLOW);
                backButton.setText("< Back >");
                SoundPlayer.optionChosenSound();
                break;
        }
    }

    private void resetButtons() {
        enterButton.setForeground(Color.WHITE);
        enterButton.setText("Connect");

        backButton.setForeground(Color.WHITE);
        backButton.setText("Back");
    }

    private void openPlayerMenu() {
        Main.openLoadingMenu();
        Main.createMultiplayerManager(ipAddress, portNumber, username);
    }

    private boolean getClientInfo() {
        username = nameField.getText();
        ipAddress = ipField.getText();
        try {
            portNumber = Integer.parseInt(portField.getText());
        } catch (Exception e) {
            return false;
        }
        if (username.length() == 0 || ipAddress.length() == 0 || portNumber < 1 || portNumber >= 0xFFFF) {
            return false;
        }
        return true;
    }
}