package gui;

import gui.sidePanels.*;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel {

    public final static int WIDTH = 1280, HEIGHT = 720;

    public GameWindow(Game game) {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());

        SidePanel sidePanel = new ReinforcementsPanel(); // size is set inside SidePanel
        //SidePanel sidePanel = new AttackPanel();

        Map map = new Map(game);
        map.setPreferredSize(new Dimension((int) (WIDTH*0.75), (int) (HEIGHT*0.9)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel gameTitle = new JPanel();
        gameTitle.setOpaque(true);
        gameTitle.setBackground(Color.RED);
        this.add(gameTitle, gbc);

        JPanel gameFlow = new JPanel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gameFlow.setOpaque(true);
        gameFlow.setBackground(Color.GREEN);
        this.add(gameFlow, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(sidePanel, gbc);

        gbc.gridx = 1;
        this.add(map, gbc);
    }

}
