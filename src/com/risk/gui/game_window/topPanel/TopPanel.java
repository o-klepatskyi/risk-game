package com.risk.gui.game_window.topPanel;

import com.risk.gui.game_window.GameWindow;
import com.risk.logic.Game;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    static final int WIDTH = (int) (GameWindow.WIDTH*0.75);
    static final int HEIGHT = (int) (GameWindow.HEIGHT*0.1);

    private final Game game;
    private GamePhasePanel gamePhasePanel;
    private PlayersStatePanel playersStatePanel;

    public TopPanel(Game game) {
        this.game = game;
        setOpaque(true);
        setLayout(new FlowLayout(FlowLayout.LEADING, 0,0));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        initPanels();
    }

    private void initPanels() {
        gamePhasePanel = new GamePhasePanel();
        playersStatePanel = new PlayersStatePanel(game);

        add(gamePhasePanel);
        add(playersStatePanel);
    }

    public void updatePanel() {
        gamePhasePanel.updatePhase(game.getCurrentPlayer(), game.getGamePhase());
        playersStatePanel.updatePlayers();
    }
}
