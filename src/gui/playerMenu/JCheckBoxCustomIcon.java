package gui.playerMenu;

import java.awt.*;
import java.io.File;

import javax.swing.*;

class JCheckBoxCustomIcon extends JCheckBox {

    public JCheckBoxCustomIcon(int width, int height) {
        setSize(width, height);
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);

        height -= 5;

        setIcon(new ImageIcon(new ImageIcon("res" + File.separator + "checkbox-icons" + File.separator + "disabledIcon.jpg").getImage().getScaledInstance(height, height,  Image.SCALE_SMOOTH)));

        setSelectedIcon(new ImageIcon(new ImageIcon("res" + File.separator + "checkbox-icons" + File.separator + "selectedIcon.jpg").getImage().getScaledInstance(height, height,  Image.SCALE_SMOOTH)));

        setDisabledIcon(new ImageIcon(new ImageIcon("res" + File.separator + "checkbox-icons" + File.separator + "disabledIcon.jpg").getImage().getScaledInstance(height, height,  Image.SCALE_SMOOTH)));
    }
}
