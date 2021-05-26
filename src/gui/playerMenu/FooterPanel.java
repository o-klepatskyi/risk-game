package gui.playerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FooterPanel extends JPanel {

    private static final int WIDTH = PlayerMenu.WIDTH-50;
    private static final int HEIGHT = PlayerMenu.HEIGHT/10;
    //private static final Font FONT = new Font("Arial", Font.BOLD, 25);

    private JButton addPlayerButton;
    private JButton startButton;
    private PlayerMenu parent;

    FooterPanel(PlayerMenu parent) {
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        setBackground(new Color(255, 255, 255, 123));

        this.parent = parent;

        add(getAddPlayerButton());
        add(getStartButton());

        setVisible(true);
    }

    private JButton getAddPlayerButton() {
        if (addPlayerButton == null) {
            addPlayerButton = new JButton("Add player");
            addPlayerButton.setPreferredSize(new Dimension(WIDTH/4, HEIGHT-10));
            addPlayerButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    parent.addPlayer();
                }
            });
        }
        return addPlayerButton;
    }

    private JButton getStartButton() {
        if (startButton == null) {
            startButton = new JButton("Start game");
            startButton.setPreferredSize(new Dimension(WIDTH/4, HEIGHT-10));
        }
        return startButton;
    }
}
