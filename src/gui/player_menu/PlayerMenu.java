package gui.player_menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import gui.MainFrame;
import logic.Game;
import logic.Player;
import logic.maps.Map;
import logic.maps.MapType;
import logic.network.*;
import util.res.Images;

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
        if (!MainFrame.isMultiplayer() && playerPanels.size() == 0) {
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
            if (!MainFrame.isMultiplayer() || MainFrame.isServer()) {
                if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
                    panel.getRemovePlayerButton().setEnabled(true);
                }
                if (currentPlayerNumber <= MIN_PLAYER_NUMBER) {
                    panel.getRemovePlayerButton().setEnabled(false);
                }

                if (MainFrame.isMultiplayer() && panel.getPlayerNameField().getText().equals(MainFrame.manager.client.username)) {
                    panel.getRemovePlayerButton().setEnabled(false);
                }
            }

            if (MainFrame.isMultiplayer()) {
                if (MainFrame.isServer() && panel.getBotCheckBox().isSelected()) {
                    // they are already enabled
                } else if (!MainFrame.manager.client.username.equals(panel.getPlayerNameField().getText())) {
                    panel.getColorComboBox().setEnabled(false);
                }
            }

            add(panel);
        }

        if (MainFrame.isMultiplayer()) {
            if (MainFrame.isServer()) {
                if (currentPlayerNumber == MAX_PLAYER_NUMBER) {
                    fp.getAddPlayerButton().setEnabled(false);
                } else {
                    fp.getAddPlayerButton().setEnabled(true);
                }

                if (    MainFrame.isServer() &&
                        MainFrame.manager.server.userNames.size() < 2) {
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

        MainFrame.frame.revalidate();
        MainFrame.frame.repaint();
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

        if (playerName.length() > 0 && MainFrame.isMultiplayer()) {
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
            MainFrame.manager.sendMessage(new Message(MessageType.BOT_ADDED, getPlayers()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPlayerPanel(Player player) {
        if (player != null && MainFrame.isMultiplayer()) {
            PlayerPanel p = new PlayerPanel(this, colorModel, player);
            playerPanels.add(p);
            currentPlayerNumber++;
        }
    }

    public void removePlayer(String username) {
        if (MainFrame.isMultiplayer()) {
            for (PlayerPanel p : playerPanels) {
                if (p.getPlayerNameField().getText().equals(username)) {
                    removePlayerPanel(p);
                    break;
                }
            }
        }
    }

    public void removePlayer(PlayerPanel playerPanel) {
        if (MainFrame.isMultiplayer()) {
            if (currentPlayerNumber > MIN_PLAYER_NUMBER) {
                if (!playerPanel.getBotCheckBox().isSelected()) { // when it is not bot, close connection
                    String username = playerPanel.getPlayer().getName();
                    MainFrame.manager.sendMessage(new Message(MessageType.CONNECTION_CLOSED_BY_ADMIN, username));
                }
                removePlayerPanel(playerPanel);
                MainFrame.manager.sendMessage(new Message(MessageType.PLAYER_DELETED, getPlayers()));
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
        if (MainFrame.isMultiplayer()) {
            if (MainFrame.isServer()) MainFrame.manager.initGame();
        } else {
            Game game = new Game(getPlayers(), getSelectedMap());
            MainFrame.openGameWindow(game.getGameWindow());
            game.start();
        }
    }

    public Collection<Player> getPlayers() {
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
