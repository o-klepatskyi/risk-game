package gui.sidePanels;

import gui.GameWindow;
import gui.ValueJLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReinforcementsPanel extends SidePanel {

    private ValueJLabel reinforcementsGot, reinforcementsLeft, fromTerritories, fromContinentsControlled, territoryChosen, territory;
    private ArrayList<ValueJLabel> labels = new ArrayList<>();

    private JSpinner troopsLeftSpinner;
    private JButton deployTroopsButton;

    private final int labelWidth = (int) (GameWindow.WIDTH * 0.25), labelHeight = 30;

    public ReinforcementsPanel() {
        setLayout(new FlowLayout());

        initLabels();

        troopsLeftSpinner = new JSpinner(new SpinnerNumberModel());
        troopsLeftSpinner.setPreferredSize(new Dimension(labelWidth/5, labelHeight));
        deployTroopsButton = new JButton("Deploy troops");
        deployTroopsButton.setPreferredSize(new Dimension(labelWidth/2, labelHeight));
        add(troopsLeftSpinner);
        add(deployTroopsButton);
    }

    private void initLabels() {
        reinforcementsGot = new ValueJLabel("Reinforcements got: ");
        reinforcementsLeft = new ValueJLabel("Reinforcements Left: ");
        fromTerritories = new ValueJLabel("From territories: ");
        fromContinentsControlled = new ValueJLabel("From continents controlled: ", "");
        // todo: add continents
        territoryChosen = new ValueJLabel("Territory chosen: ", "");
        territory = new ValueJLabel("");

        labels.add(reinforcementsGot);
        labels.add(fromTerritories);
        labels.add(fromContinentsControlled);
        while (labels.size() < 10)  labels.add(new ValueJLabel("", ""));
        labels.add(reinforcementsLeft);
        labels.add(territoryChosen);
        labels.add(territory);


        for (JLabel label : labels) {
            label.setPreferredSize(new Dimension(labelWidth, labelHeight));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(font);
            label.setForeground(Color.white);
            label.setOpaque(false);
            add(label);
        }
    }

    private final Font font = new Font("Blackadder ITC", Font.BOLD, labelHeight);
}
