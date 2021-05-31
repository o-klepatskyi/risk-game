package gui.gameWindow.sidePanels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AttackPanel extends SidePanel {
    private ValueJLabel territoryChosen, alliedTroops, victoryChance;
    private JButton attackButton;

    public AttackPanel() {
        setOpaque(false);
        setLayout(new FlowLayout());

        initLabels();

        attackButton = new JButton("Attack");
    }

    private void initLabels() {
        ArrayList<ValueJLabel> labels = new ArrayList<>();

        territoryChosen = new ValueJLabel("Territory chosen:\n");
        labels.add(territoryChosen);

        alliedTroops = new ValueJLabel("Allied troops:");
        labels.add(alliedTroops);

//        JLabel enemyTroops = new JLabel("Enemy troops: <none>");
//        enemyTroops.setFont(LABEL_FONT);
//        enemyTroops.setForeground(Color.WHITE);
//        enemyTroops.setHorizontalAlignment(SwingConstants.CENTER);
//        add(enemyTroops);

        ValueJLabel asd = new ValueJLabel("E");
        System.out.println(asd.getText());
        labels.add(asd);

//        MultiLineLabel asdasd = new MultiLineLabel("E123451231213121312312313");
//        asdasd.setFont(LABEL_FONT);
//        asdasd.setForeground(Color.white);
//        add(asdasd);

        JLabel asdasdasd = new JLabel("E none");
        asdasdasd.setForeground(Color.white);
        asdasdasd.setFont(LABEL_FONT);
        add(asdasdasd);

        victoryChance = new ValueJLabel("Victory chance:");
        labels.add(victoryChance);

        for (ValueJLabel label : labels) {
            label.setFont(LABEL_FONT);
            label.setForeground(Color.WHITE);
            //label.setHorizontalTextAlignment(SwingConstants.CENTER);
            add(label);
        }
    }
}
