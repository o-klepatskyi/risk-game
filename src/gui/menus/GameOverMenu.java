package gui.menus;

import gui.Main;
import logic.Player;
import util.res.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameOverMenu extends Menu {
    private final Player player;
    private JButton menu, exit;
    private GridBagConstraints gbc;
    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    private int menuOptionChosen;

    public GameOverMenu(Player player) {
        SoundPlayer.gameOverSound();
        this.player = player;

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
        JButton winnerLabel = new JButton("Winner:");
        buttons.add(winnerLabel);
        JButton winnerName = new JButton(player.getName());
        buttons.add(winnerName);


        menu = new JButton("Menu");
        menu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.openMainMenu();
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

        float fontSize = 35f;
        winnerName.setForeground(player.getColor());
        while (winnerName.getPreferredSize().width > 3*SIZE/4 - SIZE / 12) {
            winnerName.setFont(LABEL_FONT.deriveFont(--fontSize));
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
                            Main.openMainMenu();
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
}
