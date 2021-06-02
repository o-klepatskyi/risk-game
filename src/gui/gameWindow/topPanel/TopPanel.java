package gui.gameWindow.topPanel;

import gui.gameWindow.GameWindow;
import util.Fonts;

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
            currentPlayer = new JLabel("Player 1 - ");
            currentPlayer.setFont(Fonts.BUTTON_FONT.deriveFont((float) HEIGHT - 15));
            add(currentPlayer);

            currentState = new JLabel("Fortify");
            currentState.setFont(Fonts.BUTTON_FONT.deriveFont((float) HEIGHT - 15));
            add(currentState);
        }
    }
}
