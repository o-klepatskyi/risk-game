package gui.player_menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import logic.Game;
import logic.Player;
import logic.network.MultiplayerManager;

public class PlayerMenu extends JPanel {
    private final Image bgImg = new ImageIcon("res/player-menu-bg.jpg").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);

    public static final int WIDTH = 688;
    public static final int HEIGHT = 450;
    private final int MAX_PLAYER_NUMBER = 6; // can not be bigger than ColorModel availableColors length
    private final int MIN_PLAYER_NUMBER = 1;
    private int currentPlayerNumber = 0;
    private final ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
    private final FooterPanel fp;
    private final HeaderPanel hp = new HeaderPanel();
    private final JFrame frame;
    private final ColorModel colorModel = new ColorModel();
    private final boolean isMultiplayer;
    private final MultiplayerManager multiplayerManager;

    public PlayerMenu(final JFrame frame) {
        this(frame, null);
    }


    public PlayerMenu(final JFrame frame, final MultiplayerManager multiplayerManager) {
        this.multiplayerManager = multiplayerManager;
        isMultiplayer = (multiplayerManager != null);
        this.frame = frame;
        fp = new FooterPanel(this, frame);
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(new FlowLayout());
        setOpaque(true);

        updatePanels();
    }

    private void updatePanels() {
        if (playerPanels.size() == 0) {
            for (int i = 0; i < MIN_PLAYER_NUMBER; i++) {
                addPlayerPanel();
            }

            colorModel.updateAll();
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

        if (currentPlayerNumber == MIN_PLAYER_NUMBER) {
            fp.getStartButton().setEnabled(false);
        } else {
            fp.getStartButton().setEnabled(true);
        }

        add(fp);

        repaint();
    }


    public void addPlayer() {
        if (currentPlayerNumber < MAX_PLAYER_NUMBER) {
            addPlayerPanel();
            updatePanels();
            //System.out.println("Current player: " + currentPlayerNumber + ", playerPanels.size() = " + playerPanels.size());
        }
    }

    private void addPlayerPanel() {
        PlayerPanel p = new PlayerPanel(this, colorModel);
        playerPanels.add(p);
        currentPlayerNumber++;
    }

    public void removePlayer(PlayerPanel playerPanel) {
        if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
            removePlayerPanel(playerPanel);
            updatePanels();
            //System.out.println("Current player: " + currentPlayerNumber + ", playerPanels.size() = " + playerPanels.size());
        }
    }

    private void removePlayerPanel(PlayerPanel playerPanel) {
        currentPlayerNumber--;
        playerPanels.remove(playerPanel);
        playerPanel.setVisible(false);
        colorModel.removeComboBox(playerPanel.getColorComboBox());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, this);
    }

    public void startGame() {
        // gather player info
        ArrayList<Player> players = new ArrayList<>();

        for (PlayerPanel pp : playerPanels) {
            players.add(pp.getPlayerInfo());
        }

        frame.remove(this);
        Game game = new Game(players);
        frame.add(game.getGameWindow());
        frame.pack();

        System.out.println(players);
    }
}
