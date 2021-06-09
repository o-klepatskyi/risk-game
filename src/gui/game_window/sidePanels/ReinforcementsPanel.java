package gui.game_window.sidePanels;

import gui.game_window.GameWindow;
import logic.Territory;
import logic.network.Message;
import logic.network.MessageType;
import util.res.Fonts;
import util.res.SoundPlayer;
import util.exceptions.IllegalNumberOfReinforceTroopsException;
import util.exceptions.SrcNotStatedException;

import javax.swing.*;
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
    private ArrayList<String> labelsBonuses;

    public ReinforcementsPanel(GameWindow gameWindow, int bonus, ArrayList<String> labels) {
        super(gameWindow);
        playerBonus = bonus;
        troopsLeft = bonus;
        this.labelsBonuses = labels;
        initLabels();
        initButtons();
    }

    private void initLabels() {
        reinforcementsGot = new ValueJLabel("Reinforcements got: ", playerBonus);
        reinforcementsLeft = new ValueJLabel("Reinforcements Left: ", troopsLeft);

        fromContinentsControlled = new JLabel("From continents controlled: ");
        territoryChosen = new JLabel("Territory chosen:");
        territory = new JLabel("<none>");

        ArrayList<JLabel> labels = new ArrayList<>();

        labels.add(reinforcementsGot);
        labels.add(new JLabel(labelsBonuses.get(0)));
        if (labelsBonuses.size() > 1) {
            labels.add(fromContinentsControlled);
            for (int i = 1; i < labelsBonuses.size(); i++) {
                labels.add(new JLabel(labelsBonuses.get(i)));
            }
        }
        while (labels.size() < 10)  labels.add(new JLabel(" "));
        labels.add(reinforcementsLeft);
        labels.add(territoryChosen);
        labels.add(territory);


        for (JLabel label : labels) {
            label.setFont(LABEL_FONT);
            label.setForeground(FONT_COLOR);
            label.setAlignmentX(0.5f);
            add(label);
        }
    }

    private void initButtons() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        System.out.println("Troops left: " + troopsLeft);
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

        try {
            Territory src = Territory.getIdentical(this.src);
            gameWindow.game.reinforce(reinforcedTroops);

            if (gameWindow.game.isMultiplayer) {
                gameWindow.game.manager.sendMessage(new Message(MessageType.REINFORCE, src, reinforcedTroops));
            }

            troopsLeft -= reinforcedTroops;

        } catch (SrcNotStatedException | IllegalNumberOfReinforceTroopsException e) {
            e.printStackTrace();
        }
    }
}
