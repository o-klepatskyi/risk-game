package gui.game_window.sidePanels;

import logic.Territory;
import util.res.Fonts;
import gui.game_window.GameWindow;
import util.res.Images;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {
    private final int WIDTH = (int) (GameWindow.WIDTH*0.25);
    private final int HEIGHT = (int) (GameWindow.HEIGHT*0.9);
    protected final int LABEL_WIDTH = WIDTH, LABEL_HEIGHT = 30;

    protected final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont((float) LABEL_HEIGHT);
    protected final Font BUTTON_FONT = Fonts.BUTTON_FONT.deriveFont((float) LABEL_HEIGHT-5);

    protected Territory src, dst;

    protected final Color FONT_COLOR = Color.white;


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
        g.drawImage(Images.SIDE_PANEL_BG, 0, 0,WIDTH, HEIGHT+100, this);
    }

    public void updateTerritories(Territory src, Territory dst) {
        this.src = src;
        this.dst = dst;
    }
}
