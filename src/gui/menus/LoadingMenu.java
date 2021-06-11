package gui.menus;

import util.res.Fonts;

import javax.swing.*;
import java.awt.*;

public class LoadingMenu extends Menu {
    private GridBagConstraints gbc;

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
}


