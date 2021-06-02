package gui.gameWindow.sidePanels;

import logic.Territory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AttackPanel extends SidePanel {
    private JLabel territoryChosen, territory, victoryChance;
    private ValueJLabel alliedTroops, enemyTroops;
    private JButton attackButton, endAttack;

    public AttackPanel() {
        initLabels();
        initButtons();
    }

    private void initLabels() {
        ArrayList<JLabel> labels = new ArrayList<>();

        territoryChosen = new JLabel("Territory chosen:");
        labels.add(territoryChosen);

        territory = new JLabel("<none>");
        labels.add(territory);

        alliedTroops = new ValueJLabel("Allied troops:");
        labels.add(alliedTroops);

        enemyTroops = new ValueJLabel("Enemy troops:");
        labels.add(enemyTroops);

        victoryChance = new ValueJLabel("Victory chance:");
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
        add(attackButton);

        add(Box.createRigidArea(new Dimension(0,50)));

        endAttack = new JButton("End attack");
        endAttack.setPreferredSize(buttonSize);
        endAttack.setAlignmentX(0.5f);
        endAttack.setFont(BUTTON_FONT);
        add(endAttack);
    }

    @Override
    public void updateTerritories(Territory src, Territory dst) {
        super.updateTerritories(src, dst);

    }
}
