package com.risk.gui.menus;

import com.risk.gui.Main;
import com.risk.logic.Player;
import com.risk.util.res.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class GameOverMenu extends Menu {
    private final Player player;
    private JButton menu, log, exit;
    private GridBagConstraints gbc;
    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    private int menuOptionChosen;

    public GameOverMenu(Player player) {
        SoundPlayer.gameOverSound();
        this.player = player;

        initWindow();
        initButtons();

        menuOptionChosen = 1;
        highlightOption(menuOptionChosen);
    }

    private void initWindow() {
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(85, 0, -70, 0);
    }

    private void resetButtons() {
        menu.setForeground(Color.WHITE);
        menu.setText("Menu");

        log.setForeground(Color.WHITE);
        log.setText("Open logs");

        exit.setForeground(Color.WHITE);
        exit.setText("Exit");
    }

    private void highlightOption(int option) {
        resetButtons();
        switch (option) {
            case 1:
                menu.setForeground(Color.YELLOW);
                menu.setText("< Menu >");
                SoundPlayer.optionChosenSound();
                break;
            case 2:
                log.setForeground(Color.YELLOW);
                log.setText("< Open logs >");
                SoundPlayer.optionChosenSound();
                break;
            case 3:
                exit.setForeground(Color.YELLOW);
                exit.setText("< Exit >");
                SoundPlayer.optionChosenSound();
                break;
        }
    }

    private void initButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        JButton winnerLabel = new JButton("Winner:");
        buttons.add(winnerLabel);
        JButton winnerName = new JButton(player.getName());
        buttons.add(winnerName);


        menu = new JButton("Menu");
        menu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.openMainMenu();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 1;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(menu);

        log = new JButton("Open logs");
        log.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                openLog();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 2;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(log);

        exit = new JButton("Exit");
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                System.exit(0);
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 3;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(exit);

        for (JButton button: buttons) {
            button.setFont(LABEL_FONT);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            add(button,gbc);
        }

        float fontSize = 35f;
        winnerName.setForeground(player.getColor());
        while (winnerName.getPreferredSize().width > 3*SIZE/4 - SIZE / 12) {
            winnerName.setFont(LABEL_FONT.deriveFont(--fontSize));
        }
    }

    private void openLog() {
        try {
            new ProcessBuilder("Notepad.exe", "logs.txt").start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not open logs.txt file",
                    "Error opening logs.txt",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
