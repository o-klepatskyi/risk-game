package gui.menus;

import gui.Main;
import util.res.Fonts;
import util.res.Images;
import util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
// todo add setKeyListener method to manipulate with mouse
public class MultiplayerMenu extends Menu {
    private JButton serverButton, clientButton, backButton;
    private GridBagConstraints gbc;

    private int menuOptionChosen;

    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    public MultiplayerMenu() {
        initWindow();
        initButtons();

        //menuOptionChosen = 1;
        //highlightOption(menuOptionChosen);
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

    private void initButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        serverButton = new JButton("Create room");
        serverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.openServerMenu();
                SoundPlayer.buttonClickedSound();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 1;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(serverButton);

        clientButton = new JButton("Connect");
        clientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.openClientMenu();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 2;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(clientButton);

        backButton = new JButton("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.openMainMenu();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 3;
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
            case 1:
                serverButton.setForeground(Color.YELLOW);
                serverButton.setText("< Create room >");
                SoundPlayer.optionChosenSound();
                break;
            case 2:
                clientButton.setForeground(Color.YELLOW);
                clientButton.setText("< Connect >");
                SoundPlayer.optionChosenSound();
                break;
            case 3:
                backButton.setForeground(Color.YELLOW);
                backButton.setText("< Back >");
                SoundPlayer.optionChosenSound();
                break;
        }
    }

    private void resetButtons() {
        serverButton.setForeground(Color.WHITE);
        serverButton.setText("Create room");

        clientButton.setForeground(Color.WHITE);
        clientButton.setText("Connect");

        backButton.setForeground(Color.WHITE);
        backButton.setText("Back");
    }
}

