package gui.game_window.topPanel;

import gui.game_window.sidePanels.ValueJLabel;
import logic.Player;
import util.res.Fonts;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class PlayerBox extends JPanel {
    private final int WIDTH = TopPanel.WIDTH / 6;
    private final int HEIGHT = TopPanel.HEIGHT / 2;
    private final Player player;
    private ValueJLabel playerLabel;

    PlayerBox(Player p) {
        player = p;

        initPanel();
        initLabel();
        setVisible(true);
    }

    private void initPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
