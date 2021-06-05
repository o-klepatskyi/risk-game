package gui.player_menu;

import java.awt.*;
import static util.Images.*;

import javax.swing.*;

class JCheckBoxCustomIcon extends JCheckBox {

    public JCheckBoxCustomIcon(int width, int height) {
        setSize(width, height);
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);

        height -= 5;

        setIcon(new ImageIcon(DISABLED_CHECKBOX.getScaledInstance(height, height,  Image.SCALE_SMOOTH)));
        setSelectedIcon(new ImageIcon(SELECTED_CHECKBOX.getScaledInstance(height, height,  Image.SCALE_SMOOTH)));
        setDisabledSelectedIcon(new ImageIcon(SELECTED_CHECKBOX.getScaledInstance(height, height,  Image.SCALE_SMOOTH)));
        setDisabledIcon(new ImageIcon(DISABLED_CHECKBOX.getScaledInstance(height, height,  Image.SCALE_SMOOTH)));
    }
}
