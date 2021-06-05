package gui.game_window.topPanel;

import gui.game_window.GameWindow;
import util.Images;

import javax.swing.*;
import java.awt.*;

public class Logo extends JPanel {
    private final int WIDTH = (int) (GameWindow.WIDTH*0.25);
    private final int HEIGHT = (int) (GameWindow.HEIGHT*0.1);

    public Logo() {
        setOpaque(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Images.SMALL_LOGO, 0, 0,WIDTH,HEIGHT, this);
    }
}
