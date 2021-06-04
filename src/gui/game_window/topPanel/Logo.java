package gui.game_window.topPanel;

import gui.game_window.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Logo extends JPanel {
    private final int WIDTH = (int) (GameWindow.WIDTH*0.25);
    private final int HEIGHT = (int) (GameWindow.HEIGHT*0.1);
    
    //todo: change the logo

    private final Image bgImg = new ImageIcon(getClass().getResource("logo-small.png")).getImage();//.getScaledInstance(WIDTH, HEIGHT,  Image.SCALE_SMOOTH);

    public Logo() {
        setOpaque(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // 320, 72
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, this);
    }
}
