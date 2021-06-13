package com.risk.gui.game_window.top_panel;

import com.risk.gui.game_window.side_panels.ValueJLabel;
import com.risk.logic.Player;
import com.risk.util.resources.Fonts;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class PlayerBox extends JPanel {
    private final Player player;
    private ValueJLabel playerLabel;

    private final int WIDTH;
    private final int HEIGHT;

    PlayerBox(Player p, int width, int height) {
        player = p;
        WIDTH = width;
        HEIGHT = height;
        initPanel();
        initLabel();
    }

    private void initPanel() {
        setBorder(new LineBorder(Color.black));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }

    private void initLabel() {
        playerLabel = new ValueJLabel(player.getName(), 99);
        float fontSize = 25f;
        playerLabel.setFont(Fonts.BUTTON_FONT.deriveFont(fontSize));
        while(playerLabel.getPreferredSize().width > WIDTH - 5) {
            playerLabel.setFont(Fonts.BUTTON_FONT.deriveFont(--fontSize));
        }
        playerLabel.setVerticalAlignment(SwingConstants.CENTER);
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(playerLabel);
    }

    public void update(int territories, boolean isActive) {
        playerLabel.setValue(territories);
        if (isActive) {
            setBackground(player.getColor());
            playerLabel.setForeground(Color.WHITE);
        } else if (territories == 0) {
            setBackground(null);
            playerLabel.setForeground(player.getColor());
            playerLabel.setText("<html><body><span style='text-decoration: line-through;'>" + player.getName() + "</span></body></html>");
        } else {
            setBackground(null);
            playerLabel.setForeground(player.getColor());
        }
    }

    public Player getPlayer() {
        return player;
    }
}
