package gui.game_window;

import gui.game_window.sidePanels.*;
import gui.game_window.topPanel.Logo;
import gui.game_window.topPanel.TopPanel;
import logic.Game;
import logic.GameOption;
import logic.Territory;
import util.*;

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

        sidePanel = SidePanel.getEmptyPanel();
        gameFlow = new TopPanel();

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
        gameMap = game.getGameMap();
        this.add(gameMap, gbc);

        updatePhase();
    }

    public void updatePhase() {
        GameOption gameOption = game.getGameOption();
        updateSidePanel(gameOption);
        gameFlow.updatePhase(game.getCurrentPlayer(), gameOption);
        gameMap.drawField();
    }

    private void updateSidePanel(GameOption gameOption) {
        remove(sidePanel);
        if (game.isMultiplayer && !game.isCurrentPlayerActive()) {
            sidePanel = SidePanel.getEmptyPanel();
        } else {
            remove(sidePanel);
            switch (gameOption) {
                case REINFORCEMENT:
                    sidePanel = createReinforcementsPanel();
                    break;
                case ATTACK:
                    sidePanel = new AttackPanel(this);
                    break;
                case FORTIFY:
                    sidePanel = new FortifyPanel(this);
                    break;
                default:
                    sidePanel = SidePanel.getEmptyPanel();
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(sidePanel, gbc);
    }

    private SidePanel createReinforcementsPanel() {
        ReinforcementsPanel reinf = new ReinforcementsPanel(this, game.getCurrentPlayer().getBonus());
        return reinf;
    }

    public void updateChosenTerritories(Territory src, Territory dst) {
        sidePanel.updateTerritories(src, dst);
    }

    public void reinforce(int reinforcedTroops) throws SrcNotStatedException, IllegalNumberOfReinforceTroopsException {
        game.reinforce(reinforcedTroops);
    }

    public void nextPhase() {
        game.nextPhase();
        updatePhase();
    }

    public void attack() throws DstNotStatedException, WrongTerritoriesPairException, IllegalNumberOfAttackTroopsException, SrcNotStatedException {
        game.attack();
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

    public void fortify(int troopsToTransfer) throws DstNotStatedException, WrongTerritoriesPairException, IllegalNumberOfFortifyTroopsException, SrcNotStatedException {
        game.fortify(troopsToTransfer);
        nextPhase();
    }

    public void fortify(Territory src, Territory dst, int troopsToTransfer) throws WrongTerritoriesPairException, IllegalNumberOfFortifyTroopsException, DstNotStatedException, SrcNotStatedException {
        game.fortify(src, dst, troopsToTransfer);
        nextPhase();
    }
}
