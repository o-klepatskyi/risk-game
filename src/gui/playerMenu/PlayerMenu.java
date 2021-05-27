package gui.playerMenu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import logic.Player;

public class PlayerMenu extends JPanel {
    private final Image bgImg = new ImageIcon("res/player-menu-bg-800-450.jpg").getImage();

    public static final int WIDTH = 800;
    public static final int HEIGHT = 450;
    private final int MAX_PLAYER_NUMBER = 6; // can not be bigger than ColorModel availableColors length
    private final int MIN_PLAYER_NUMBER = 2;
    private int currentPlayerNumber = 0;
    private final ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
    private final FooterPanel fp = new FooterPanel(this);
    private final HeaderPanel hp = new HeaderPanel();

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
        // initial adding 2 player panels
        if (playerPanels.size() == 0) {
            for (int i = 0; i < MIN_PLAYER_NUMBER; i++) {
                addPlayerPanel();
            }

            ColorModel.updateAll();
            updatePanels();
            return;
        }

        removeAll();

        add(hp);

        for (PlayerPanel panel : playerPanels) {
            if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
                panel.getRemovePlayerButton().setEnabled(true);
            }
            if (currentPlayerNumber == MIN_PLAYER_NUMBER) {
                panel.getRemovePlayerButton().setEnabled(false);
            }
            add(panel);
        }

        if (currentPlayerNumber == MAX_PLAYER_NUMBER) {
            fp.getAddPlayerButton().setEnabled(false);
        } else {
            fp.getAddPlayerButton().setEnabled(true);
        }

        add(fp);

        repaint();
    }


    public void addPlayer() {
        if (currentPlayerNumber < MAX_PLAYER_NUMBER) {
            addPlayerPanel();
            updatePanels();
            System.out.println("Current player: " + currentPlayerNumber + ", playerPanels.size() = " + playerPanels.size());
        }
    }

    private void addPlayerPanel() {
        PlayerPanel p = new PlayerPanel(this);
        playerPanels.add(p);
        currentPlayerNumber++;
    }

    public void removePlayer(PlayerPanel playerPanel) {
        if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
            removePlayerPanel(playerPanel);
            updatePanels();
            System.out.println("Current player: " + currentPlayerNumber + ", playerPanels.size() = " + playerPanels.size());
        }
    }

    private void removePlayerPanel(PlayerPanel playerPanel) {
        currentPlayerNumber--;
        playerPanels.remove(playerPanel);
        playerPanel.setVisible(false);
        ColorModel.removeComboBox(playerPanel.getColorComboBox());
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

    public void startGame() {
        // gather player info
        ArrayList<Player> players = new ArrayList<>();

        for (PlayerPanel pp : playerPanels) {
            players.add(pp.getPlayerInfo());
        }

        System.out.println(players);
    }
}
