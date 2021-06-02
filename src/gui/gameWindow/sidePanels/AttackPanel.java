package gui.gameWindow.sidePanels;

import gui.gameWindow.GameWindow;
import logic.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AttackPanel extends SidePanel {
    private JLabel alliedTroopsLabel, enemyTroopsLabel;
    private ValueJLabel alliedTroops, enemyTroops, victoryChance;
    private JButton attackButton, endAttack;
    private GameWindow gameWindow;

    public AttackPanel(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        initLabels();
        initButtons();
    }

    private void initLabels() {
        ArrayList<JLabel> labels = new ArrayList<>();

        alliedTroopsLabel = new JLabel("Allied troops:");
        labels.add(alliedTroopsLabel);

        alliedTroops = new ValueJLabel();
        labels.add(alliedTroops);

        enemyTroopsLabel = new JLabel("Enemy troops:");
        labels.add(enemyTroopsLabel);

        enemyTroops = new ValueJLabel();
        labels.add(enemyTroops);

        victoryChance = new ValueJLabel("Victory chance:", "0%");
        labels.add(victoryChance);

        for (JLabel label : labels) {
            label.setFont(LABEL_FONT);
            label.setForeground(Color.WHITE);
            label.setAlignmentX(0.5f);
            add(label);
        }
    }

    private void initButtons() {
        Dimension buttonSize = new Dimension(LABEL_WIDTH, LABEL_HEIGHT);

        attackButton = new JButton("Attack");
        attackButton.setPreferredSize(buttonSize);
        attackButton.setAlignmentX(0.5f);
        attackButton.setFont(BUTTON_FONT);
        attackButton.setEnabled(false);
        attackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                attack();
            }
        });
        add(attackButton);

        add(Box.createRigidArea(new Dimension(0,50)));

        endAttack = new JButton("End attack");
        endAttack.setPreferredSize(buttonSize);
        endAttack.setAlignmentX(0.5f);
        endAttack.setFont(BUTTON_FONT);
        endAttack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameWindow.nextPhase();
            }
        });
        add(endAttack);
    }

    private void attack() {
        if (attackButton.isEnabled()) {
            gameWindow.attack();
        }
    }

    @Override
    public void updateTerritories(Territory src, Territory dst) {
        super.updateTerritories(src, dst);
        if (src != null) {
            alliedTroops.setTextWithoutValue(src.getName() + " -");
            alliedTroops.setValue(src.getTroops());
            alliedTroops.setForeground(src.getOwner().getColor());
        } else {
            alliedTroops.clear();
            alliedTroops.setForeground(Color.black);
        }

        if (dst != null) {
            enemyTroops.setTextWithoutValue(dst.getName() + " -");
            enemyTroops.setValue(dst.getTroops());
            enemyTroops.setForeground(dst.getOwner().getColor());
        } else {
            enemyTroops.clear();
            enemyTroops.setForeground(Color.black);
        }

        if (src != null && dst != null) {
            attackButton.setEnabled(true);
            victoryChance.setValue(gameWindow.calculateProbability() + "%");
        } else {
            attackButton.setEnabled(false);
            victoryChance.setValue("0%");
        }
    }
}
