package gui.gameWindow.sidePanels;

import gui.gameWindow.GameWindow;
import logic.Territory;
import util.Fonts;
import util.SoundPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ReinforcementsPanel extends SidePanel {

    private ValueJLabel reinforcementsGot, reinforcementsLeft, fromTerritories;
    private JLabel fromContinentsControlled, territoryChosen, territory;

    private JSpinner troopsLeftSpinner;
    private JButton deployTroopsButton;

    private int playerBonus, troopsLeft;
    private GameWindow gameWindow;

    public ReinforcementsPanel(GameWindow gameWindow, int bonus) {
        this.gameWindow = gameWindow;
        playerBonus = bonus;
        troopsLeft = bonus;
        initLabels();
        initButtons();
    }

    private void initLabels() {
        reinforcementsGot = new ValueJLabel("Reinforcements got: ", playerBonus);
        reinforcementsLeft = new ValueJLabel("Reinforcements Left: ", troopsLeft);
        fromTerritories = new ValueJLabel("From territories: ");
        fromContinentsControlled = new JLabel("From continents controlled: ");
        // todo: add continents
        territoryChosen = new JLabel("Territory chosen:");
        territory = new JLabel("<none>");

        ArrayList<JLabel> labels = new ArrayList<>();

        labels.add(reinforcementsGot);
        labels.add(fromTerritories);
        labels.add(fromContinentsControlled);
        while (labels.size() < 10)  labels.add(new JLabel(" "));
        labels.add(reinforcementsLeft);
        labels.add(territoryChosen);
        labels.add(territory);


        for (JLabel label : labels) {
            label.setFont(LABEL_FONT);
            label.setForeground(FONT_COLOR);
            label.setAlignmentX(0.5f);
            //label.setBorder(new LineBorder(Color.red));
            add(label);
        }
    }

    private void initButtons() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        troopsLeftSpinner = new JSpinner(new SpinnerNumberModel(troopsLeft, 1, troopsLeft, 1));
        ((JSpinner.DefaultEditor) troopsLeftSpinner.getEditor()).getTextField().setEditable(false);
        Dimension troopsSpinnerSize = new Dimension(LABEL_WIDTH/5, LABEL_HEIGHT);
        troopsLeftSpinner.setPreferredSize(troopsSpinnerSize);
        troopsLeftSpinner.setMaximumSize(troopsSpinnerSize);
        troopsLeftSpinner.setMinimumSize(troopsSpinnerSize);
        deployTroopsButton = new JButton("Deploy troops");
        deployTroopsButton.setPreferredSize(new Dimension(LABEL_WIDTH/2, LABEL_HEIGHT));
        deployTroopsButton.setFont(Fonts.BUTTON_FONT.deriveFont((float) LABEL_HEIGHT-10));
        deployTroopsButton.setEnabled(false);
        deployTroopsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (deployTroopsButton.isEnabled()) {
                    SoundPlayer.buttonClickedSound();
                    reinforce();
                }
            }
        });
        bottomPanel.add(troopsLeftSpinner);
        bottomPanel.add(deployTroopsButton);
        add(bottomPanel);
    }

    @Override
    public void updateTerritories(Territory src, Territory dst) {
        super.updateTerritories(src, dst);
        deployTroopsButton.setEnabled(true);
        if (src == null) {
            territory.setText("<none>");
            deployTroopsButton.setEnabled(false);
        } else {
            territory.setText(src.getName());
            deployTroopsButton.setEnabled(true);
        }
    }

    public void reinforce() {
        int reinforcedTroops = (int) troopsLeftSpinner.getValue();
        gameWindow.reinforce(reinforcedTroops);
        troopsLeft -= reinforcedTroops;
        if (troopsLeft == 0) {
            gameWindow.nextPhase();
        } else {
            troopsLeftSpinner.setModel(new SpinnerNumberModel(troopsLeft, 1, troopsLeft, 1));
            reinforcementsLeft.setValue(troopsLeft);
            deployTroopsButton.setEnabled(false);
        }
    }
}
