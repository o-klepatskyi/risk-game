package gui.sidePanels;

import javax.swing.*;
import java.awt.*;

public class AttackPanel extends SidePanel {
    private ValueJLabel territoryChosen;

    public AttackPanel() {
        setOpaque(false);
        territoryChosen = new ValueJLabel("Territory chosen:\n");
        territoryChosen.setFont(LABEL_FONT);
        territoryChosen.setForeground(Color.WHITE);
        territoryChosen.setHorizontalTextAlignment(SwingConstants.CENTER);
        add(territoryChosen);
    }
}
