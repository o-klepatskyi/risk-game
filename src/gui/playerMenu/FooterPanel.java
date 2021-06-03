package gui.playerMenu;

import util.Fonts;
import util.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class FooterPanel extends JPanel {

    private static final int WIDTH = PlayerMenu.WIDTH-50;
    private static final int HEIGHT = PlayerMenu.HEIGHT/10;
    private static final Font BUTTON_FONT = Fonts.BUTTON_FONT.deriveFont((float) HEIGHT-24);

    private JButton addPlayerButton;
    private JButton startButton;
    private final PlayerMenu parent;

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

    public JButton getAddPlayerButton() {
        if (addPlayerButton == null) {
            addPlayerButton = new JButton("Add player");
            addPlayerButton.setPreferredSize(new Dimension(WIDTH/4, HEIGHT-10));
            addPlayerButton.setFont(BUTTON_FONT);
            addPlayerButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(addPlayerButton.isEnabled())
                        SoundPlayer.buttonClickedSound();
                    parent.addPlayer();
                }
            });
        }
        return addPlayerButton;
    }

    public JButton getStartButton() {
        if (startButton == null) {
            startButton = new JButton("Start game");
            startButton.setPreferredSize(new Dimension(WIDTH/4, HEIGHT-10));
            startButton.setFont(BUTTON_FONT);
            startButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(startButton.isEnabled())
                        SoundPlayer.buttonClickedSound();
                    parent.startGame();
                }
            });
        }
        return startButton;
    }
}
