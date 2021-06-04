package gui.player_menu;

import gui.main_menu.MainMenu;
import logic.network.Message;
import logic.network.MessageType;
import util.Fonts;

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
    private JFrame frame;
    private JPanel menu = this;

    FooterPanel(PlayerMenu parent, JFrame frame) {
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        setBackground(new Color(255, 255, 255, 123));

        this.parent = parent;
        this.frame = frame;

        add(getAddPlayerButton());
        add(getStartButton());
        add(getBackButton());

        setVisible(true);
    }

    public JButton getAddPlayerButton() {
        if (addPlayerButton == null) {
            addPlayerButton = new JButton("Add player");
            addPlayerButton.setPreferredSize(new Dimension(WIDTH/4, HEIGHT-10));
            addPlayerButton.setFont(BUTTON_FONT);
            if (parent.isMultiplayer) {
                addPlayerButton.setText("Add bot");
                if (parent.isServer) {
                    addPlayerButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (addPlayerButton.isEnabled()) {
                                parent.addBot();
                            }
                        }
                    });
                } else {
                    addPlayerButton.setEnabled(false);
                    addPlayerButton.setToolTipText("Only room owner can add bots");
                }
            } else {
                addPlayerButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (addPlayerButton.isEnabled()) {
                            parent.addPlayer();
                        }
                    }
                });
            }
        }
        return addPlayerButton;
    }

    public JButton getStartButton() {
        if (startButton == null) {
            startButton = new JButton("Start game");
            startButton.setPreferredSize(new Dimension(WIDTH/4, HEIGHT-10));
            startButton.setFont(BUTTON_FONT);
            startButton.setEnabled(false);
            if (parent.isMultiplayer && !parent.isServer) {
                startButton.setToolTipText("Only room owner can start the game");
            } else {
                startButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (startButton.isEnabled()) {
                            parent.startGame();
                        }
                    }
                });
            }
        }
        return startButton;
    }

    private JButton getBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(WIDTH/4, HEIGHT-10));
        backButton.setFont(BUTTON_FONT);
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closeAction();
            }
        });
        return backButton;
    }

    private void closeAction() {
        if (parent.isMultiplayer) {
            parent.multiplayerManager.closeClient();
        } else {
            frame.remove(parent);
            frame.add(new MainMenu(frame));
            frame.revalidate();
            frame.repaint();
            frame.pack();
        }
    }
}
