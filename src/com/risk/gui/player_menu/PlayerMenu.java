package com.risk.gui.player_menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.risk.gui.Main;
import com.risk.logic.Game;
import com.risk.logic.Player;
import com.risk.logic.maps.Map;
import com.risk.logic.maps.MapType;
import com.risk.logic.network.*;
import com.risk.util.resources.Images;

public class PlayerMenu extends JPanel {

    public static final int WIDTH = 688;
    public static final int HEIGHT = 450;
    private final int MAX_PLAYER_NUMBER = ColorModel.colors.size();
    private final int MIN_PLAYER_NUMBER = 1;
    private int currentPlayerNumber = 0, totalNumberOfPlayers = 0;
    private ArrayList<PlayerPanel> playerPanels = new ArrayList<>();
    private final FooterPanel fp;
    private final HeaderPanel hp = new HeaderPanel();
    private ColorModel colorModel;


    public PlayerMenu() {
        colorModel = new ColorModel();

        fp = new FooterPanel(this);
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(new FlowLayout());
        setOpaque(true);
    }

    public void updatePanels() {
        if (!Main.isMultiplayer() && playerPanels.size() == 0) {
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
            if (!Main.isMultiplayer() || Main.isServer()) {
                if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
                    panel.getRemovePlayerButton().setEnabled(true);
                }
                if (currentPlayerNumber <= MIN_PLAYER_NUMBER) {
                    panel.getRemovePlayerButton().setEnabled(false);
                }

                if (Main.isMultiplayer() && panel.getPlayerNameField().getText().equals(Main.manager.client.username)) {
                    panel.getRemovePlayerButton().setEnabled(false);
                }
            }

            if (Main.isMultiplayer()) {
                if (Main.isServer() && panel.getBotCheckBox().isSelected()) {
                    // they are already enabled
                } else if (!Main.manager.client.username.equals(panel.getPlayerNameField().getText())) {
                    panel.getColorComboBox().setEnabled(false);
                }
            }

            add(panel);
        }

        if (Main.isMultiplayer()) {
            if (Main.isServer()) {
                if (currentPlayerNumber == MAX_PLAYER_NUMBER) {
                    fp.getAddPlayerButton().setEnabled(false);
                } else {
                    fp.getAddPlayerButton().setEnabled(true);
                }

                if (    Main.isServer() &&
                        Main.manager.server.userNames.size() < 2) {
                    fp.getStartButton().setEnabled(false);
                    fp.getStartButton().setToolTipText("Wait for players to connect");
                } else {
                    fp.getStartButton().setEnabled(true);
                    fp.getStartButton().setToolTipText(null);
                }

                fp.getMapComboBox().setEnabled(true);
            }
        } else {
            if (currentPlayerNumber == MAX_PLAYER_NUMBER) {
                fp.getAddPlayerButton().setEnabled(false);
            } else {
                fp.getAddPlayerButton().setEnabled(true);
            }
            if (currentPlayerNumber < 2) {
                fp.getStartButton().setEnabled(false);
                fp.getStartButton().setToolTipText("Wait for players to connect");
            } else {
                fp.getStartButton().setEnabled(true);
                fp.getStartButton().setToolTipText("");
            }
            fp.getMapComboBox().setEnabled(true);
        }

        add(fp);

        Main.frame.revalidate();
        Main.frame.repaint();
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

        if (playerName.length() > 0 && Main.isMultiplayer()) {
            p.getPlayerNameField().setEditable(false);
            p.getPlayerNameField().setText(playerName);
            p.getBotCheckBox().setEnabled(false);
        }

        playerPanels.add(p);
        currentPlayerNumber++;
        totalNumberOfPlayers++;
    }

    public void addBot() {
        PlayerPanel p = new PlayerPanel(this, colorModel);

        p.getPlayerNameField().setText(MultiplayerManager.BOT_NAME);
        p.getPlayerNameField().setEditable(false);

        p.getBotCheckBox().setEnabled(false);
        p.getBotCheckBox().setSelected(true);

        playerPanels.add(p);
        currentPlayerNumber++;

        try {
            Main.manager.sendMessage(new Message(MessageType.BOT_ADDED, getPlayers()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPlayerPanel(Player player) {
        if (player != null && Main.isMultiplayer()) {
            PlayerPanel p = new PlayerPanel(this, colorModel, player);
            playerPanels.add(p);
            currentPlayerNumber++;
        }
    }

    public void removePlayer(String username) {
        if (Main.isMultiplayer()) {
            for (PlayerPanel p : playerPanels) {
                if (p.getPlayerNameField().getText().equals(username)) {
                    removePlayerPanel(p);
                    break;
                }
            }
        }
    }

    public void removePlayer(PlayerPanel playerPanel) {
        if (Main.isMultiplayer()) {
            if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
                if (!playerPanel.getBotCheckBox().isSelected()) { // when it is not bot, close connection
                    String username = playerPanel.getPlayer().getName();
                    Main.manager.sendMessage(new Message(MessageType.CONNECTION_CLOSED_BY_ADMIN, username));
                }
                removePlayerPanel(playerPanel);
                Main.manager.sendMessage(new Message(MessageType.PLAYER_DELETED, getPlayers()));
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
        g.drawImage(Images.PLAYER_MENU_BG, 0, 0, WIDTH, HEIGHT, this);
    }

    public void startGame() {
        if (Main.isMultiplayer()) {
            if (Main.isServer()) Main.manager.initGame();
        } else {
            java.util.List<Player> players = getPlayers();
            if (Main.isShuffling()) Collections.shuffle(players);
            Game game = new Game(players, getSelectedMap());
            Main.openGameWindow(game.gameWindow);
            game.start();
        }
    }

    public List<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>(playerPanels.size());
        for (PlayerPanel pp : playerPanels) {
            players.add(pp.getPlayer());
        }
        return players;
    }

    public int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }

    public Map getSelectedMap() {
        return MapType.getMap((getSelectedMapType()));
    }

    public MapType getSelectedMapType() {
        return (MapType) fp.getMapComboBox().getSelectedItem();
    }

    public void changeMap(MapType mapType) {
        fp.getMapComboBox().setSelectedItem(mapType);
        updatePanels();
    }
}
