package gui.sidePanels;

import gui.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class SidePanel extends JPanel {
    private final int WIDTH = (int) (GameWindow.WIDTH*0.25);
    private final int HEIGHT = (int) (GameWindow.HEIGHT*0.9);
    protected final int LABEL_WIDTH = WIDTH, LABEL_HEIGHT = 30;

    private final Image bgImg = new ImageIcon("res" + File.separator + "side-panel-bg.jpg").getImage().getScaledInstance(WIDTH, HEIGHT+50,  Image.SCALE_SMOOTH); // 320,700

    protected final Font LABEL_FONT = new Font("Blackadder ITC", Font.BOLD, LABEL_HEIGHT);


    public SidePanel() {
        setOpaque(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        revalidate();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, this);
    }
}
