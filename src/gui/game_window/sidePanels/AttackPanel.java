package gui.game_window.sidePanels;

import gui.game_window.GameWindow;
import logic.Game;
import logic.Territory;
import util.res.SoundPlayer;

import logic.network.Message;
import logic.network.MessageType;
import util.exceptions.DstNotStatedException;
import util.exceptions.IllegalNumberOfAttackTroopsException;
import util.exceptions.SrcNotStatedException;
import util.exceptions.WrongTerritoriesPairException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AttackPanel extends SidePanel {
    private JLabel alliedTroopsLabel, enemyTroopsLabel,  alliedTroops, enemyTroops;
    private ValueJLabel victoryChance;
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

        alliedTroops = new JLabel("<none>");
        labels.add(alliedTroops);

        enemyTroopsLabel = new JLabel("Enemy troops:");
        labels.add(enemyTroopsLabel);

        enemyTroops = new ValueJLabel("<none>");
        labels.add(enemyTroops);

        victoryChance = new ValueJLabel("Victory chance:", "0%");
        labels.add(victoryChance);

        for (JLabel label : labels) {
            label.setFont(LABEL_FONT);
            label.setForeground(FONT_COLOR);
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
                endAttack();
            }
        });
        add(endAttack);
    }

    private void endAttack() {
        SoundPlayer.buttonClickedSound();
        gameWindow.nextPhase();
        if (gameWindow.game.isMultiplayer) {
            gameWindow.game.manager.sendMessage(new Message(MessageType.END_ATTACK));
        }
    }

    private void attack() {
        if (attackButton.isEnabled()) {
            SoundPlayer.buttonClickedSound();
            try {
                Territory src = Territory.getIdentical(this.src), dst = Territory.getIdentical(this.dst);
                gameWindow.attack();
                if (gameWindow.game.isMultiplayer) {
                    Territory newSrc = findTerritory(src.getName()), newDst = findTerritory(dst.getName());
                    gameWindow.game.manager.sendMessage(new Message(MessageType.ATTACK, src,dst, newSrc, newDst));
                }
            } catch (DstNotStatedException | SrcNotStatedException | IllegalNumberOfAttackTroopsException | WrongTerritoriesPairException e) {
                e.printStackTrace();
            }
        }
    }

    private Territory findTerritory(String name) {
        for (Territory t : gameWindow.game.getGameGraph().getTerritories()) {
            if (t.getName().equals(name)) return t;
        }
        return null;
    }

    @Override
    public void updateTerritories(Territory src, Territory dst) {
        super.updateTerritories(src, dst);
        if (src != null) {
            alliedTroops.setText(src.getName() + " - " + src.getTroops());
            alliedTroops.setForeground(src.getOwner().getColor());
        } else {
            alliedTroops.setText("<none>");
            alliedTroops.setForeground(Color.white);
        }

        if (dst != null) {
            enemyTroops.setText(dst.getName() + " - " + dst.getTroops());
            enemyTroops.setForeground(dst.getOwner().getColor());
        } else {
            enemyTroops.setText("<none>");
            enemyTroops.setForeground(Color.white);
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
