package gui.main_menu;

import gui.multiplayer_menu.MultiplayerMenu;
import gui.player_menu.PlayerMenu;
import gui.rules_menu.RulesMenu;
import util.res.Fonts;
import util.res.Images;
import util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
// todo: Call menu.setFocusable(true) AFTER calling frame.pack()
public class MainMenu extends JPanel {
    private final JPanel panel;
    private final JFrame frame;
    private JButton play, multiplayer, rules, exit;
    private GridBagConstraints gbc;
    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

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
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(85, 0, -70, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Images.MAIN_MENU_BG, 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(Images.MENU_PANEL, SIZE/6, SIZE/3 + SIZE/40, 3*SIZE/4 - SIZE / 12, SIZE/2+SIZE/20+SIZE/40, null);
    }

    private void resetButtons() {
        play.setForeground(Color.WHITE);
        play.setText("Play");

        multiplayer.setForeground(Color.WHITE);
        multiplayer.setText("Multiplayer");

        rules.setForeground(Color.WHITE);
        rules.setText("Rules");

        exit.setForeground(Color.WHITE);
        exit.setText("Exit");
    }

    private void highlightOption(int option) {
        resetButtons();
        switch (option) {
            case 1:
                play.setForeground(Color.YELLOW);
                play.setText("< Play >");
                SoundPlayer.optionChosenSound();
                break;
            case 2:
                multiplayer.setForeground(Color.YELLOW);
                multiplayer.setText("< Multiplayer >");
                SoundPlayer.optionChosenSound();
                break;
            case 3:
                rules.setForeground(Color.YELLOW);
                rules.setText("< Rules >");
                SoundPlayer.optionChosenSound();
                break;

            case 4:
                exit.setForeground(Color.YELLOW);
                exit.setText("< Exit >");
                SoundPlayer.optionChosenSound();
                break;
        }
    }

    private void initButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        play = new JButton("Play");
        play.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                openPlayerMenu();
                SoundPlayer.buttonClickedSound();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 1;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(play);

        multiplayer = new JButton("Multiplayer");
        multiplayer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                openMultiplayerMenu();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 2;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(multiplayer);

        rules = new JButton("Rules");
        rules.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                openRulesMenu();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 3;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(rules);

        exit = new JButton("Exit");
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                System.exit(0);
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 4;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(exit);

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
                        menuOptionChosen = 4;
                    else
                        menuOptionChosen--;
                    highlightOption(menuOptionChosen);
                }
                else if(key == KeyEvent.VK_DOWN) {
                    if(menuOptionChosen == 4)
                        menuOptionChosen = 1;
                    else
                        menuOptionChosen++;
                    highlightOption(menuOptionChosen);
                }
                else if(key == KeyEvent.VK_ENTER) {
                    switch(menuOptionChosen){
                        case 1:
                            SoundPlayer.buttonClickedSound();
                            openPlayerMenu();
                            break;
                        case 2:
                            SoundPlayer.buttonClickedSound();
                            openMultiplayerMenu();
                            break;
                        case 3:
                            SoundPlayer.buttonClickedSound();
                            openRulesMenu();
                            break;
                        case 4:
                            SoundPlayer.buttonClickedSound();
                            System.exit(0);
                            break;
                    }
                }

            }
        });
    }

    private void openPlayerMenu() {
        panel.setVisible(false);
        frame.remove(panel);
        frame.add(new PlayerMenu(frame));
        frame.pack();
    }

    private void openMultiplayerMenu() {
        panel.setVisible(false);
        frame.remove(panel);
        frame.add(new MultiplayerMenu(frame));
        frame.pack();
    }

    private void openRulesMenu() {
        panel.setVisible(false);
        frame.remove(panel);
        frame.add(new RulesMenu(frame));
        frame.pack();
    }

}
