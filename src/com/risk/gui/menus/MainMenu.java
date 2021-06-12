package com.risk.gui.menus;

import com.risk.gui.Main;
import com.risk.util.res.Fonts;
import com.risk.util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainMenu extends Menu {
    private JButton play, multiplayer, rules, settings, exit;
    private GridBagConstraints gbc;
    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    private int menuOptionChosen;

    public MainMenu() {
        initWindow();
        initButtons();
        setKeyListener();

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
        play.setForeground(Color.WHITE);
        play.setText("Play");

        multiplayer.setForeground(Color.WHITE);
        multiplayer.setText("Multiplayer");

        rules.setForeground(Color.WHITE);
        rules.setText("Rules");

        settings.setForeground(Color.WHITE);
        settings.setText("Settings");

        exit.setForeground(Color.WHITE);
        exit.setText("Exit");
    }

    private void highlightOption(int option) {
        resetButtons();
        switch (option) {
            case 1:
                play.setForeground(Color.YELLOW);
                play.setText("< Play >");
                SoundPlayer.optionChosenSound();
                break;
            case 2:
                multiplayer.setForeground(Color.YELLOW);
                multiplayer.setText("< Multiplayer >");
                SoundPlayer.optionChosenSound();
                break;
            case 3:
                rules.setForeground(Color.YELLOW);
                rules.setText("< Rules >");
                SoundPlayer.optionChosenSound();
                break;
            case 4:
                settings.setForeground(Color.YELLOW);
                settings.setText("< Settings >");
                SoundPlayer.optionChosenSound();
                break;
            case 5:
                exit.setForeground(Color.YELLOW);
                exit.setText("< Exit >");
                SoundPlayer.optionChosenSound();
                break;
        }
    }

    private void initButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        play = new JButton("Play");
        play.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Main.openPlayerMenu();
                SoundPlayer.buttonClickedSound();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 1;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(play);

        multiplayer = new JButton("Multiplayer");
        multiplayer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.openMultiplayerMenu();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 2;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(multiplayer);

        rules = new JButton("Rules");
        rules.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.openRulesMenu();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 3;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(rules);

        settings = new JButton("Settings");
        settings.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.openSettingsMenu();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 4;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(settings);

        exit = new JButton("Exit");
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                System.exit(0);
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 5;
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
    }

    private void setKeyListener() {
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_UP) {
                    if(menuOptionChosen == 1)
                        menuOptionChosen = 5;
                    else
                        menuOptionChosen--;
                    highlightOption(menuOptionChosen);
                }
                else if(key == KeyEvent.VK_DOWN) {
                    if(menuOptionChosen == 5)
                        menuOptionChosen = 1;
                    else
                        menuOptionChosen++;
                    highlightOption(menuOptionChosen);
                }
                else if(key == KeyEvent.VK_ENTER) {
                    switch(menuOptionChosen){
                        case 1:
                            SoundPlayer.buttonClickedSound();
                            Main.openPlayerMenu();
                            break;
                        case 2:
                            SoundPlayer.buttonClickedSound();
                            Main.openMultiplayerMenu();
                            break;
                        case 3:
                            SoundPlayer.buttonClickedSound();
                            Main.openRulesMenu();
                            break;
                        case 4:
                            SoundPlayer.buttonClickedSound();
                            Main.openSettingsMenu();
                        case 5:
                            SoundPlayer.buttonClickedSound();
                            System.exit(0);
                            break;
                    }
                }
            }
        });
    }
}
