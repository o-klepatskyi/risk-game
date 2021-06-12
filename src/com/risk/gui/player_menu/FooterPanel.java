package com.risk.gui.player_menu;

import com.risk.gui.Main;
import com.risk.logic.maps.MapType;
import com.risk.util.res.Fonts;
import com.risk.util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class FooterPanel extends JPanel {

    private static final int WIDTH = PlayerMenu.WIDTH-50;
    private static final int HEIGHT = PlayerMenu.HEIGHT/10;
    private static final Font BUTTON_FONT = Fonts.BUTTON_FONT.deriveFont((float) HEIGHT-24);

    private JButton addPlayerButton;
    private JButton startButton;
    private JComboBox<MapType> comboBox;
    private final PlayerMenu parent;

    FooterPanel(PlayerMenu parent) {
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        setBackground(new Color(255, 255, 255, 123));

        this.parent = parent;

        JLabel mapLabel = new JLabel("Map: ");
        mapLabel.setFont(Fonts.LABEL_FONT.deriveFont(20f));
        add(mapLabel);
        comboBox = new MapComboBox();
        comboBox.setPreferredSize(new Dimension(WIDTH/5+10, HEIGHT-10));
        add(comboBox);
        add(getAddPlayerButton());
        add(getStartButton());
        add(getBackButton());

        setVisible(true);
    }

    public JComboBox getMapComboBox() {
        return comboBox;
    }

    public JButton getAddPlayerButton() {
        if (addPlayerButton == null) {
            addPlayerButton = new JButton("Add player");
            addPlayerButton.setPreferredSize(new Dimension(WIDTH/5+10, HEIGHT-10));
            addPlayerButton.setFont(BUTTON_FONT.deriveFont(18f));
            if (Main.isMultiplayer()) {
                addPlayerButton.setText("Add bot");
                if (Main.isServer()) {
                    addPlayerButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (addPlayerButton.isEnabled()) {
                                parent.addBot();
                            }
                        }
                    });
                } else {
                    addPlayerButton.setEnabled(false);
                    addPlayerButton.setToolTipText("Only room owner can add bots");
                }
            } else {
                addPlayerButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (addPlayerButton.isEnabled()) {
                            SoundPlayer.buttonClickedSound();
                            parent.addPlayer();
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        SoundPlayer.optionChosenSound();
                    }
                });
            }
        }
        return addPlayerButton;
    }

    public JButton getStartButton() {
        if (startButton == null) {
            startButton = new JButton("Start game");
            startButton.setPreferredSize(new Dimension(WIDTH/5+10, HEIGHT-10));
            startButton.setFont(BUTTON_FONT.deriveFont(18f));
            startButton.setEnabled(false);
            if (Main.isMultiplayer() && !Main.isServer()) {
                startButton.setToolTipText("Only room owner can start the game");
            } else {
                startButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (startButton.isEnabled()) {
                            SoundPlayer.buttonClickedSound();
                            parent.startGame();
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        SoundPlayer.optionChosenSound();
                    }
                });
            }
        }
        return startButton;
    }

    private JButton getBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(WIDTH/5+10, HEIGHT-10));
        backButton.setFont(BUTTON_FONT.deriveFont(18f));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                closeAction();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
            }
        });
        return backButton;
    }

    private void closeAction() {
        if (Main.isMultiplayer()) {
            Main.manager.closeClient();
        } else {
            Main.openMainMenu();
        }
    }
}
