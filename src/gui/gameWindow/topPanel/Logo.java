package gui.gameWindow.topPanel;

import gui.gameWindow.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
// TODO: add our logo to match the size of this panel
public class Logo extends JPanel {
    private final int WIDTH = (int) (GameWindow.WIDTH*0.25);
    private final int HEIGHT = (int) (GameWindow.HEIGHT*0.1);

    private final Image bgImg = new ImageIcon("res" + File.separator + "logo-top-panel.jpeg").getImage().getScaledInstance(WIDTH, HEIGHT,  Image.SCALE_SMOOTH);

    public Logo() {
        setOpaque(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, this);
    }
}
