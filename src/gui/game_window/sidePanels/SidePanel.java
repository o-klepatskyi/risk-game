package gui.game_window.sidePanels;

import logic.Territory;
import util.Fonts;
import gui.game_window.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SidePanel extends JPanel {
    private final int WIDTH = (int) (GameWindow.WIDTH*0.25);
    private final int HEIGHT = (int) (GameWindow.HEIGHT*0.9);
    protected final int LABEL_WIDTH = WIDTH, LABEL_HEIGHT = 30;

    private final Image bgImg = new ImageIcon(getClass().getResource("side-panel-bg.jpg")).getImage().getScaledInstance(WIDTH, HEIGHT+50,  Image.SCALE_SMOOTH); // 320,700

    protected final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont((float) LABEL_HEIGHT);
    protected final Font BUTTON_FONT = Fonts.BUTTON_FONT.deriveFont((float) LABEL_HEIGHT-5);

    protected Territory src, dst;


    SidePanel() {
        setOpaque(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public static SidePanel getEmptyPanel() {
        return new SidePanel();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, this);
    }

    public void updateTerritories(Territory src, Territory dst) {
        this.src = src;
        this.dst = dst;
        System.out.println("updated territories in side panel");
    }
}
