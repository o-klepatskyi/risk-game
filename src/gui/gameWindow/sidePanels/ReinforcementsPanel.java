package gui.gameWindow.sidePanels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class ReinforcementsPanel extends SidePanel {

    private ValueJLabel reinforcementsGot, reinforcementsLeft, fromTerritories;
    private JLabel fromContinentsControlled, territoryChosen, territory;
    private ArrayList<JLabel> labels = new ArrayList<>();

    private JSpinner troopsLeftSpinner;
    private JButton deployTroopsButton;

    public ReinforcementsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initLabels();
        initButtons();
    }

    private void initLabels() {
        reinforcementsGot = new ValueJLabel("Reinforcements got: ");
        reinforcementsLeft = new ValueJLabel("Reinforcements Left: ");
        fromTerritories = new ValueJLabel("From territories: ");
        fromContinentsControlled = new JLabel("From continents controlled: ");
        // todo: add continents
        territoryChosen = new JLabel("Territory chosen:");
        territory = new JLabel("<none>");

        labels.add(reinforcementsGot);
        labels.add(fromTerritories);
        labels.add(fromContinentsControlled);
        while (labels.size() < 10)  labels.add(new JLabel(" "));
        labels.add(reinforcementsLeft);
        labels.add(territoryChosen);
        labels.add(territory);


        for (JLabel label : labels) {
            label.setFont(LABEL_FONT);
            label.setForeground(Color.white);
            label.setAlignmentX(0.5f);
            label.setBorder(new LineBorder(Color.red));
            add(label);
        }
    }

    private void initButtons() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        troopsLeftSpinner = new JSpinner(new SpinnerNumberModel());
        Dimension troopsSpinnerSize = new Dimension(LABEL_WIDTH/5, LABEL_HEIGHT);
        troopsLeftSpinner.setPreferredSize(troopsSpinnerSize);
        troopsLeftSpinner.setMaximumSize(troopsSpinnerSize);
        troopsLeftSpinner.setMinimumSize(troopsSpinnerSize);
        deployTroopsButton = new JButton("Deploy troops");
        deployTroopsButton.setPreferredSize(new Dimension(LABEL_WIDTH/2, LABEL_HEIGHT));
        bottomPanel.add(troopsLeftSpinner);
        bottomPanel.add(deployTroopsButton);
        add(bottomPanel);
    }
}
