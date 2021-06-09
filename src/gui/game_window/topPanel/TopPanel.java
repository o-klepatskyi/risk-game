package gui.game_window.topPanel;

import gui.game_window.GameWindow;
import logic.GamePhase;
import logic.Player;
import util.res.Fonts;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    private final int WIDTH = (int) (GameWindow.WIDTH*0.75);
    private final int HEIGHT = (int) (GameWindow.HEIGHT*0.1);

    private JLabel currentState;
    private JLabel currentPlayer;

    public TopPanel() {
        setOpaque(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // to make labels aligned left
        //setLayout(new BoxLayout(this,0));
        initLabels();
    }

    private void initLabels() {
        if (currentPlayer == null && currentState == null) {
            currentPlayer = new JLabel();
            currentPlayer.setFont(Fonts.BUTTON_FONT.deriveFont((float) HEIGHT - 30));
            add(currentPlayer);

            currentState = new JLabel();
            currentState.setFont(Fonts.BUTTON_FONT.deriveFont((float) HEIGHT - 30));
            add(currentState);
        }
    }

    public void updatePhase(Player player, GamePhase gamePhase) {
        currentPlayer.setText(player.getName());
        currentPlayer.setForeground(player.getColor());
        currentState.setText(gamePhase.toString());
    }
}
