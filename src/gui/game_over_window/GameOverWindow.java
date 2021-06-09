package gui.game_over_window;

import gui.main_menu.MainMenu;
import logic.Player;
import logic.network.MultiplayerManager;
import util.res.Fonts;
import util.res.Images;
import util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameOverWindow extends JPanel {
    private final JPanel panel;
    private final JFrame frame;
    private final Player player;
    private JButton menu, exit;
    private GridBagConstraints gbc;
    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    private final int SIZE = 500;
    private int menuOptionChosen;
    private final MultiplayerManager manager;

    public GameOverWindow(JFrame frame, Player player, MultiplayerManager manager) {
        SoundPlayer.gameOverSound();
        this.frame = frame;
        this.player = player;
        this.manager = manager;
        panel = this;

        initWindow();
        addLabels();
        initButtons();
        setKeyListener();

        menuOptionChosen = 1;
        highlightOption(menuOptionChosen);
    }

    private void addLabels() {
        JLabel winnerLabel = new JLabel("Winner: " + player.getName());
        winnerLabel.setFont(LABEL_FONT);
        winnerLabel.setForeground(player.getColor());
        add(winnerLabel, gbc);

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
        menu.setForeground(Color.WHITE);
        menu.setText("Menu");

        exit.setForeground(Color.WHITE);
        exit.setText("Exit");
    }

    private void highlightOption(int option) {
        resetButtons();
        switch (option) {
            case 1:
                menu.setForeground(Color.YELLOW);
                menu.setText("< Menu >");
                SoundPlayer.optionChosenSound();
                break;

            case 2:
                exit.setForeground(Color.YELLOW);
                exit.setText("< Exit >");
                SoundPlayer.optionChosenSound();
                break;
        }
    }

    private void initButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        menu = new JButton("Menu");
        menu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                openMenu();
                SoundPlayer.buttonClickedSound();
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 1;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(menu);

        exit = new JButton("Exit");
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                System.exit(0);
            }

            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 2;
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
                        menuOptionChosen = 2;
                    else
                        menuOptionChosen--;
                    highlightOption(menuOptionChosen);
                }
                else if(key == KeyEvent.VK_DOWN) {
                    if(menuOptionChosen == 2)
                        menuOptionChosen = 1;
                    else
                        menuOptionChosen++;
                    highlightOption(menuOptionChosen);
                }
                else if(key == KeyEvent.VK_ENTER) {
                    switch(menuOptionChosen){
                        case 1:
                            SoundPlayer.buttonClickedSound();
                            openMenu();
                            break;
                        case 2:
                            SoundPlayer.buttonClickedSound();
                            System.exit(0);
                            break;
                    }
                }

            }
        });
    }

    private void openMenu() {
        if (manager != null) manager.gameOver();
        panel.setVisible(false);
        frame.remove(panel);
        frame.add(new MainMenu(frame));
        frame.pack();
    }
}
