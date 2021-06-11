package gui.game_window;

import gui.game_window.sidePanels.*;
import gui.game_window.topPanel.Logo;
import gui.game_window.topPanel.TopPanel;
import logic.Game;
import logic.GamePhase;
import logic.Territory;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel {

    public final static int WIDTH = 1280, HEIGHT = 720;

    private SidePanel sidePanel;
    private TopPanel gameFlow;
    public final Game game;
    private GameMap gameMap;
    private GridBagConstraints gbc = new GridBagConstraints();

    public GameWindow(Game game) {
        this.game = game;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());

        sidePanel = new SidePanel(this);
        gameFlow = new TopPanel(game);

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel gameTitle = new Logo();
        this.add(gameTitle, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;

        this.add(gameFlow, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(sidePanel, gbc);

        gbc.gridx = 1;
        gameMap = game.gameMap;
        this.add(gameMap, gbc);
    }

    public void update() {
        GamePhase gamePhase = game.getGamePhase();
        updateSidePanel(gamePhase);
        gameFlow.updatePanel();
        gameMap.drawField();
    }

    private void updateSidePanel(GamePhase gamePhase) {
        remove(sidePanel);
        if ((game.isMultiplayer && !game.isCurrentPlayerActive()) || game.getCurrentPlayer().isBot()) {
            sidePanel = new SidePanel(this);
        } else {
            remove(sidePanel);
            switch (gamePhase) {
                case REINFORCEMENT:
                    sidePanel = new ReinforcementsPanel(this);
                    break;
                case ATTACK:
                    sidePanel = new AttackPanel(this);
                    break;
                case FORTIFY:
                    sidePanel = new FortifyPanel(this);
                    break;
                default:
                    sidePanel = new SidePanel(this);
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(sidePanel, gbc);
    }

    public void updateChosenTerritories(Territory src, Territory dst) {
        sidePanel.updateTerritories(src, dst);
    }

    public int calculateProbability() {
        try {
            return game.calculateProbability();
        } catch (Exception e) {
            System.out.print("CALCULATE in Game Window: ");
            System.err.println(e.getMessage());
        }
        return 0;
    }
}
