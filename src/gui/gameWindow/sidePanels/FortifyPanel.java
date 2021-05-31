package gui.gameWindow.sidePanels;

import util.Fonts;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class FortifyPanel extends SidePanel {
    private JLabel territoryChosen, territory;

    private JSpinner troopsToTransferSpinner;
    private JButton fortifyButton;

    public FortifyPanel() {
        initLabels();
        initButtons();
    }

    private void initLabels() {
        ArrayList<JLabel> labels = new ArrayList<>();

        territoryChosen = new JLabel("Territory chosen:");
        territory = new JLabel("<none>");

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
        troopsToTransferSpinner = new JSpinner(new SpinnerNumberModel());
        Dimension troopsSpinnerSize = new Dimension(LABEL_WIDTH/5, LABEL_HEIGHT);
        troopsToTransferSpinner.setPreferredSize(troopsSpinnerSize);
        troopsToTransferSpinner.setMaximumSize(troopsSpinnerSize);
        troopsToTransferSpinner.setMinimumSize(troopsSpinnerSize);
        fortifyButton = new JButton("Fortify");
        fortifyButton.setFont(Fonts.BUTTON_FONT.deriveFont((float) LABEL_HEIGHT-10));
        fortifyButton.setPreferredSize(new Dimension(LABEL_WIDTH/3, LABEL_HEIGHT));
        bottomPanel.add(troopsToTransferSpinner);
        bottomPanel.add(fortifyButton);
        add(bottomPanel);
    }
}
