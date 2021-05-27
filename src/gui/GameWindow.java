package gui;

import gui.sidePanels.ReinforcementsPanel;
import gui.sidePanels.SidePanel;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel {
    public GameWindow(Game game) {
        final int WIDTH = 1280, HEIGHT = 720;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());

        SidePanel reinforcementsPanel = new ReinforcementsPanel();
        reinforcementsPanel.setPreferredSize(new Dimension((int) (WIDTH*0.25), (int) (HEIGHT*0.9)));
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
//        gbc.weightx = 1;
//        gbc.weighty = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gameFlow.setOpaque(true);
        gameFlow.setBackground(Color.GREEN);
        this.add(gameFlow, gbc);

        gbc.gridx = 0;
//        gbc.weightx = 0.25;
//        gbc.weighty = 0.95;
        gbc.gridy = 1;
        this.add(reinforcementsPanel, gbc);

//        gbc.weightx = 0.75;
        gbc.gridx = 1;
        this.add(map, gbc);


        System.out.println(reinforcementsPanel.getWidth() + " " + reinforcementsPanel.getHeight());
    }

}
