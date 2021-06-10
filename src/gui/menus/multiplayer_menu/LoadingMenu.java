package gui.menus.multiplayer_menu;

import util.res.Fonts;
import util.res.Images;

import javax.swing.*;
import java.awt.*;

public class LoadingMenu extends JPanel {

    private GridBagConstraints gbc;

    private final int SIZE = 500;

    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    public LoadingMenu() {

        setPreferredSize(new Dimension(SIZE,SIZE));
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(85,0,-70,0);

        JLabel loading = new JLabel("Loading...");
        loading.setFont(LABEL_FONT.deriveFont(35f));
        loading.setForeground(Color.WHITE);
        add(loading, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Images.MAIN_MENU_BG, 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(Images.MENU_PANEL, SIZE/6, SIZE/3 + SIZE/40, 3*SIZE/4 - SIZE / 12, SIZE/2+SIZE/20+SIZE/40, null);
    }
}


