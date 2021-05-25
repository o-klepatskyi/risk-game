package gui.playerMenu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerMenu extends JPanel {
    private final Image bgImg = new ImageIcon("res/player-menu-bg-800-450.jpg").getImage();

    public static final int WIDTH = 800;
    public static final int HEIGHT = 450;
    private final int MAX_PLAYER_NUMBER = 6; // can not be bigger than ColorModel availableColors length
    private final int MIN_PLAYER_NUMBER = 2;
    private int currentPlayerNumber = 0;
    private final ArrayList<PlayerPanel> playerPanels = new ArrayList<>();

    public PlayerMenu() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(new FlowLayout());
        setOpaque(true);

        updatePanels();

        validate();
        setVisible(true);
    }

    private void updatePanels() {
        if (playerPanels.size() == 0) {
            for (int i = 0; i < MIN_PLAYER_NUMBER; i++) {
                addPlayerPanel();
            }

            ColorModel.updateAll();
            updatePanels();
            return;
        }

        removeAll();

        add(new HeaderPanel());

        for (JPanel panel : playerPanels) {
            add(panel);
        }

        add(new FooterPanel(this));

        repaint();
    }

    private void addPlayerPanel() {
        PlayerPanel p = new PlayerPanel(this);
        playerPanels.add(p);
        currentPlayerNumber++;
        System.out.println("Current player: " + currentPlayerNumber + ", playerPanels.size() = " + playerPanels.size());
    }

    public void addPlayer() {
        if (currentPlayerNumber < MAX_PLAYER_NUMBER) {
            addPlayerPanel();
            updatePanels();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Risk - Game settings");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new PlayerMenu());
        frame.validate();
    }

    public void removePlayer(Container playerPanel) {
        if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
            playerPanels.remove(playerPanel);
            playerPanel.setVisible(false);
            updatePanels();
            currentPlayerNumber--;
            System.out.println("Current player: " + currentPlayerNumber + ", playerPanels.size() = " + playerPanels.size());
        }
    }
}
