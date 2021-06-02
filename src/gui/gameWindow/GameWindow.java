package gui.gameWindow;

import gui.gameWindow.sidePanels.*;
import gui.gameWindow.topPanel.Logo;
import gui.gameWindow.topPanel.TopPanel;
import logic.Game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel {

    public final static int WIDTH = 1280, HEIGHT = 720;

    private SidePanel sidePanel;
    private TopPanel gameFlow;

    public GameWindow(Game game) {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());

        sidePanel = SidePanel.getEmptyPanel();
        gameFlow = new TopPanel();

        GridBagConstraints gbc = new GridBagConstraints();
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
        this.add(game.getGameMap(), gbc);
    }

}
