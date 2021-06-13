package com.risk.gui.menus;

import com.risk.util.resources.Images;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    protected final int SIZE = 500;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Images.MAIN_MENU_BG, 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(Images.MENU_PANEL, SIZE/6, SIZE/3-SIZE/80, 3*SIZE/4 - SIZE / 12, SIZE/2+SIZE/7, null);
    }
}
