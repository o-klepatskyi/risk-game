package gui.sidePanels;

import javax.swing.*;
import java.awt.*;

public abstract class SidePanel extends JPanel {

    public SidePanel() {
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout());
    }
}
