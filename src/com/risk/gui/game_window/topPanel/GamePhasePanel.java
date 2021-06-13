package com.risk.gui.game_window.topPanel;

import com.risk.logic.GamePhase;
import com.risk.logic.Player;
import com.risk.util.resources.Fonts;

import javax.swing.*;
import java.awt.*;

class GamePhasePanel extends JPanel {
    private final int WIDTH = TopPanel.WIDTH;
    private final int HEIGHT = TopPanel.HEIGHT/2;

    private JLabel currentState;
    private JLabel currentPlayer;

    public GamePhasePanel() {
        setOpaque(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        initLabels();
    }

    private void initLabels() {
        if (currentPlayer == null && currentState == null) {
            currentPlayer = new JLabel();
            currentPlayer.setFont(Fonts.BUTTON_FONT.deriveFont((float) HEIGHT - 5));
            currentPlayer.setVerticalAlignment(SwingConstants.CENTER);
            add(currentPlayer);

            currentState = new JLabel();
            currentState.setFont(Fonts.BUTTON_FONT.deriveFont((float) HEIGHT - 5));
            currentState.setVerticalAlignment(SwingConstants.CENTER);
            add(currentState);
        }
    }

    public void updatePhase(Player player, GamePhase gamePhase) {
        currentPlayer.setText(player.getName());
        currentPlayer.setForeground(player.getColor());

        if (gamePhase == GamePhase.REINFORCEMENT) {
            currentState.setText(" - " + gamePhase + " - " + player.getBonus() + " troops left");
        } else {
            currentState.setText(" - " + gamePhase);
        }
    }
}
