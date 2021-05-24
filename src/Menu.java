import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends JPanel {
    private final JPanel panel;
    private final JFrame frame;
    private JButton play, multiplayer, rules;
    private GridBagConstraints gbc;

    private final int SIZE = 500;
    private int menuOptionChosen;

    public Menu(JFrame frame) {
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
        ImageIcon backgroundImage = new ImageIcon("res/menu_bg.jpg");
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        ImageIcon menuPanel = new ImageIcon("res/woodSign.jpg");
        g.drawImage(menuPanel.getImage(), SIZE/4, SIZE/2 - SIZE/20, SIZE/2, SIZE/2 - SIZE/10, null);
    }

    private void resetButtons() {
        play.setForeground(Color.WHITE);
        play.setText("  Play  ");

        multiplayer.setForeground(Color.WHITE);
        multiplayer.setText("  Multiplayer  ");

        rules.setForeground(Color.WHITE);
        rules.setText("  Rules  ");
    }

    private void highlightOption(int option) {
        resetButtons();
        switch (option) {
            case 1:
                play.setForeground(Color.YELLOW);
                play.setText("< Play >");
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
        play = new JButton("  Play  ");
        play.setFont(new Font("Arial", Font.PLAIN, 30));
        play.setContentAreaFilled(false);
        play.setMargin(new Insets(0, 50, 0, 50));
        play.setBorderPainted(false);
        play.setFocusPainted(false);
        play.setForeground(Color.WHITE);
        play.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                gameWindowOpen();
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                menuOptionChosen = 1;
                highlightOption(menuOptionChosen);
            }

            public void mouseExited(MouseEvent e) {

            }
        });

        multiplayer = new JButton("Multiplayer");
        multiplayer.setFont(new Font("Arial", Font.PLAIN, 30));
        multiplayer.setContentAreaFilled(false);
        multiplayer.setBorderPainted(false);
        multiplayer.setFocusPainted(false);
        multiplayer.setForeground(Color.WHITE);
        multiplayer.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                // TODO
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                menuOptionChosen = 2;
                highlightOption(menuOptionChosen);
            }

            public void mouseExited(MouseEvent e) {

            }
        });

        rules = new JButton("Rules");
        rules.setFont(new Font("Arial", Font.PLAIN, 30));
        rules.setContentAreaFilled(false);
        rules.setBorderPainted(false);
        rules.setFocusPainted(false);
        rules.setForeground(Color.WHITE);
        rules.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                // TODO
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                menuOptionChosen = 3;
                highlightOption(menuOptionChosen);
            }

            public void mouseExited(MouseEvent e) {

            }
        });

        add(play, gbc);
        add(multiplayer, gbc);
        add(rules, gbc);
    }


    private void setKeyListener() {
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }

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
                            gameWindowOpen();
                            break;
                        case 2:
                            // TODO
                        case 3:
                            // TODO
                    }
                }

            }

            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void gameWindowOpen() {
        frame.remove(panel);
        Game game = new Game(null);
        frame.add(game.getGameWindow());
        frame.pack();
    }
}
