package gui.player_menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import logic.Game;
import logic.Player;
import logic.network.MultiplayerManager;
import logic.network.NetworkMode;

public class PlayerMenu extends JPanel {
    private final Image bgImg = new ImageIcon(getClass().getResource("player-menu-bg.jpg")).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);

    public static final int WIDTH = 688;
    public static final int HEIGHT = 450;
    private final int MAX_PLAYER_NUMBER = ColorModel.colors.size();
    private final int MIN_PLAYER_NUMBER = 1;
    private int currentPlayerNumber = 0;
    private final ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
    private final FooterPanel fp;
    private final HeaderPanel hp = new HeaderPanel();
    private final JFrame frame;

    public final boolean isMultiplayer;
    public final boolean isServer;
    public final MultiplayerManager multiplayerManager;
    private final ColorModel colorModel;

    public PlayerMenu(final JFrame frame) {
        this(frame, null);
    }


    public PlayerMenu(final JFrame frame, final MultiplayerManager multiplayerManager) {
        this.multiplayerManager = multiplayerManager;
        if (multiplayerManager != null) {
            isMultiplayer = true;
            isServer = multiplayerManager.networkMode == NetworkMode.SERVER;
            colorModel = multiplayerManager.game.colorModel;
            multiplayerManager.setPlayerMenu(this);
        } else {
            isMultiplayer = false;
            isServer = false;
            colorModel = new ColorModel();
        }

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
            if (!isMultiplayer) {
                for (int i = 0; i < MIN_PLAYER_NUMBER; i++) {
                    addPlayerPanel();
                }
            } else {
                // todo
                addPlayerPanel(new ArrayList<>(multiplayerManager.game.getPlayers()).get(0).getName());
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
        addPlayer(null);
    }

    public void addPlayer(String name) {
        if (currentPlayerNumber < MAX_PLAYER_NUMBER) {
            addPlayerPanel(name);
            updatePanels();
            //System.out.println("Current player: " + currentPlayerNumber + ", playerPanels.size() = " + playerPanels.size());
        }
    }

    private void addPlayerPanel() {
        addPlayerPanel(null);
    }

    private void addPlayerPanel(String playerName) {
        PlayerPanel p = new PlayerPanel(this, colorModel);

        if (playerName != null && isMultiplayer) {
            p.getPlayerNameField().setEditable(false);
            p.getPlayerNameField().setText(playerName);
            p.getBotCheckBox().setEnabled(false);
        }

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
        Game game = new Game();

        for (Player player : getPlayers()) {
            game.addPlayer(player);
        }
        game.startGame();

        frame.remove(this);
        frame.add(game.getGameWindow());
        frame.pack();
    }

    public Collection<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>(playerPanels.size());
        for (PlayerPanel pp : playerPanels) {
            players.add(pp.getPlayer());
        }
        return players;
    }
}
