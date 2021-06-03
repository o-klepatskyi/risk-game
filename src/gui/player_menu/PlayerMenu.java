package gui.player_menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import logic.Game;
import logic.Player;
import logic.network.Message;
import logic.network.MessageType;
import logic.network.MultiplayerManager;
import logic.network.NetworkMode;

public class PlayerMenu extends JPanel {
    private final Image bgImg = new ImageIcon(getClass().getResource("player-menu-bg.jpg")).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);

    public static final int WIDTH = 688;
    public static final int HEIGHT = 450;
    private final int MAX_PLAYER_NUMBER = ColorModel.colors.size();
    private final int MIN_PLAYER_NUMBER = 1;
    private int currentPlayerNumber = 0;
    private ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
    private final FooterPanel fp;
    private final HeaderPanel hp = new HeaderPanel();
    private final JFrame frame;

    public final boolean isMultiplayer;
    public final boolean isServer;
    public final MultiplayerManager multiplayerManager;
    private ColorModel colorModel;

    public PlayerMenu(final JFrame frame) {
        this(frame, null);
    }


    public PlayerMenu(final JFrame frame, final MultiplayerManager multiplayerManager) {
        this.multiplayerManager = multiplayerManager;
        if (multiplayerManager != null) {
            isMultiplayer = true;
            isServer = multiplayerManager.networkMode == NetworkMode.SERVER;
        } else {
            isMultiplayer = false;
            isServer = false;
        }
        colorModel = new ColorModel();

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
        if (!isMultiplayer && playerPanels.size() == 0) {
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
            if (!isMultiplayer || isServer) {
                if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
                    panel.getRemovePlayerButton().setEnabled(true);
                }
                if (currentPlayerNumber <= MIN_PLAYER_NUMBER) {
                    panel.getRemovePlayerButton().setEnabled(false);
                }

                if (panel.getPlayerNameField().getText().equals(multiplayerManager.client.username) && isServer) {
                    panel.getRemovePlayerButton().setEnabled(false);
                }
            }

            if (isMultiplayer) {
                if (isServer && panel.getBotCheckBox().isSelected()) {
                    // skip
                } else if (!multiplayerManager.client.username.equals(panel.getPlayerNameField().getText())) {
                    panel.getColorComboBox().setEnabled(false);
                }
            }

            System.out.println(panel.getPlayerNameField().getText() + "'s checkbox: " + panel.getBotCheckBox().isSelected());

            add(panel);
        }


        if (!isMultiplayer || isServer) {
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
        }

        add(fp);

        repaint();
        frame.pack();
    }


    public void addPlayer() {
        addPlayer("");
    }

    public void addPlayer(String name) {
        if (currentPlayerNumber < MAX_PLAYER_NUMBER) {
            addPlayerPanel(name);
            updatePanels();
        }
    }

    /**
     * FOR MULTIPLAYER ONLY
     */
    public void updatePlayers(Collection<Player> players) {
        this.colorModel = new ColorModel();
        currentPlayerNumber = 0;
        playerPanels = new ArrayList<>();
        this.colorModel = new ColorModel();
        if (currentPlayerNumber + players.size() <= MAX_PLAYER_NUMBER) {
            for (Player p : players) {
                addPlayerPanel(p);
            }

            updatePanels();
        }

        updatePanels();
    }

    private void addPlayerPanel() {
        addPlayerPanel("");
    }

    private void addPlayerPanel(String playerName) {
        PlayerPanel p = new PlayerPanel(this, colorModel);

        if (playerName.length() > 0 && isMultiplayer) {
            p.getPlayerNameField().setEditable(false);
            p.getPlayerNameField().setText(playerName);
            p.getBotCheckBox().setEnabled(false);
        }

        playerPanels.add(p);
        currentPlayerNumber++;
    }

    public void addBot() {
        PlayerPanel p = new PlayerPanel(this, colorModel);

        p.getPlayerNameField().setEditable(false);
        p.getPlayerNameField().setText("Bot");
        p.getBotCheckBox().setEnabled(false);
        p.getBotCheckBox().setSelected(true);

        playerPanels.add(p);
        currentPlayerNumber++;

        try {
            multiplayerManager.sendMessage(new Message(MessageType.BOT_ADDED, getPlayers()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPlayerPanel(Player player) {
        if (player != null && isMultiplayer) {
            PlayerPanel p = new PlayerPanel(this, colorModel, player);
            p.getPlayerNameField().setEditable(false);
            p.getBotCheckBox().setEnabled(false);
            p.getBotCheckBox().setSelected(player.isBot());
            playerPanels.add(p);
            currentPlayerNumber++;
        }
    }

    public void removePlayer(PlayerPanel playerPanel) {
        if (isMultiplayer) {
            if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
                removePlayerPanel(playerPanel);
                if (!playerPanel.getBotCheckBox().isSelected()) {
                    String username = playerPanel.getPlayer().getName();
                    try {
                        multiplayerManager.sendMessage(new Message(MessageType.CONNECTION_CLOSED_BY_ADMIN, username));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    multiplayerManager.sendMessage(new Message(MessageType.PLAYER_DELETED, getPlayers()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
                removePlayerPanel(playerPanel);
                updatePanels();
            }
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
