package gui.sidePanels;

import gui.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class SidePanel extends JPanel {
    private final Image bgImg = new ImageIcon("res" + File.separator + "side-panel-bg.jpg").getImage().getScaledInstance(320, 700,  Image.SCALE_SMOOTH);

    public SidePanel() {
        setOpaque(true);
        setPreferredSize(new Dimension((int) (GameWindow.WIDTH*0.25), (int) (GameWindow.HEIGHT*0.9)));
        revalidate();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, this);
    }

}
