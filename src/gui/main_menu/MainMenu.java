package gui.main_menu;

import gui.multiplayer_menu.MultiplayerMenu;
import gui.player_menu.PlayerMenu;
import util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainMenu extends JPanel {
    private final JPanel panel;
    private final JFrame frame;
    private JButton play, multiplayer, rules;
    private GridBagConstraints gbc;
    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    ImageIcon backgroundImage = new ImageIcon(getClass().getResource("logo.png"));
    ImageIcon menuPanel = new ImageIcon(getClass().getResource("woodSign.jpg"));

    private final int SIZE = 500;
    private int menuOptionChosen;

    public MainMenu(JFrame frame) {
        this.frame = frame;
        panel = this;

        initWindow();
        initButtons();
        setKeyListener();

        menuOptionChosen = 1;
        highlightOption(menuOptionChosen);
    }

    private void initWindow() {
        setFocusable(true);
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(85, 0, -70, 0);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(menuPanel.getImage(), SIZE/4, SIZE/2 - SIZE/20, SIZE/2, SIZE/2 - SIZE/10, null);
    }

    private void resetButtons() {
        play.setForeground(Color.WHITE);
        play.setText("Singleplayer");

        multiplayer.setForeground(Color.WHITE);
        multiplayer.setText("Multiplayer");

        rules.setForeground(Color.WHITE);
        rules.setText("Rules");
    }

    private void highlightOption(int option) {
        resetButtons();
        switch (option) {
            case 1:
                play.setForeground(Color.YELLOW);
                play.setText("< Singleplayer >");
                break;
            case 2:
                multiplayer.setForeground(Color.YELLOW);
                multiplayer.setText("< Multiplayer >");
                break;
            case 3:
                rules.setForeground(Color.YELLOW);
                rules.setText("< Rules >");
        }
    }

    private void initButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        play = new JButton("Singleplayer");
        play.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                openPlayerMenu();
            }

            public void mouseEntered(MouseEvent e) {
                menuOptionChosen = 1;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(play);

        multiplayer = new JButton("Multiplayer");
        multiplayer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
               openMultiplayerMenu();
            }

            public void mouseEntered(MouseEvent e) {
                menuOptionChosen = 2;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(multiplayer);

        rules = new JButton("Rules");
        rules.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // TODO
            }

            public void mouseEntered(MouseEvent e) {
                menuOptionChosen = 3;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(rules);

        for (JButton button: buttons) {
            button.setFont(LABEL_FONT);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            add(button,gbc);
        }
    }

    private void setKeyListener() {
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_UP) {
                    if(menuOptionChosen == 1)
                        menuOptionChosen = 3;
                    else
                        menuOptionChosen--;
                    highlightOption(menuOptionChosen);
                }
                else if(key == KeyEvent.VK_DOWN) {
                    if(menuOptionChosen == 3)
                        menuOptionChosen = 1;
                    else
                        menuOptionChosen++;
                    highlightOption(menuOptionChosen);
                }
                else if(key == KeyEvent.VK_ENTER) {
                    switch(menuOptionChosen){
                        case 1:
                            openPlayerMenu();
                            break;
                        case 2:
                            openMultiplayerMenu();
                            break;
                        case 3:
                            // TODO
                    }
                }

            }
        });
    }

    private void openPlayerMenu() {
        frame.remove(panel);
        frame.add(new PlayerMenu(frame));
        frame.pack();
    }

    private void openMultiplayerMenu() {
        frame.remove(panel);
        frame.add(new MultiplayerMenu(frame));
        frame.pack();
    }
}
