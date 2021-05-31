package gui.gameWindow.sidePanels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReinforcementsPanel extends SidePanel {

    private ValueJLabel reinforcementsGot, reinforcementsLeft, fromTerritories, fromContinentsControlled, territoryChosen, territory;
    private ArrayList<ValueJLabel> labels = new ArrayList<>();

    private JSpinner troopsLeftSpinner;
    private JButton deployTroopsButton;

    public ReinforcementsPanel() {
        setLayout(new FlowLayout());

        initLabels();

        troopsLeftSpinner = new JSpinner(new SpinnerNumberModel());
        troopsLeftSpinner.setPreferredSize(new Dimension(LABEL_WIDTH /5, LABEL_HEIGHT));
        deployTroopsButton = new JButton("Deploy troops");
        deployTroopsButton.setPreferredSize(new Dimension(LABEL_WIDTH /2, LABEL_HEIGHT));
        add(troopsLeftSpinner);
        add(deployTroopsButton);
    }

    private void initLabels() {
        reinforcementsGot = new ValueJLabel("Reinforcements got: ");
        reinforcementsLeft = new ValueJLabel("Reinforcements Left: ");
        fromTerritories = new ValueJLabel("From territories: ");
        fromContinentsControlled = new ValueJLabel("From continents controlled: ", "");
        // todo: add continents
        territoryChosen = new ValueJLabel("Territory chosen:\n");

        labels.add(reinforcementsGot);
        labels.add(fromTerritories);
        labels.add(fromContinentsControlled);
        while (labels.size() < 10)  labels.add(new ValueJLabel("", ""));
        labels.add(reinforcementsLeft);
        labels.add(territoryChosen);


        for (ValueJLabel label : labels) {
            label.setFont(LABEL_FONT);
            label.setForeground(Color.white);
            label.setHorizontalTextAlignment(SwingConstants.CENTER);
            add(label);
        }
    }
}
