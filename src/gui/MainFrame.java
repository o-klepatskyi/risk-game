package gui;

import gui.game_window.GameWindow;
import gui.menus.GameOverMenu;
import gui.menus.RulesMenu;
import gui.menus.main_menu.MainMenu;
import gui.menus.multiplayer_menu.*;
import gui.player_menu.PlayerMenu;
import logic.Player;
import logic.network.MultiplayerManager;
import logic.network.NetworkMode;
import util.res.SoundPlayer;
import javax.swing.*;

public class MainFrame {
    public static JFrame frame;
    public static MultiplayerManager manager;

    private static void initFrame() {
        if (frame == null) {
            frame = new JFrame();
            frame.setTitle("Risk - The Ultimate Battle");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initFrame();
                openMainMenu();
                SoundPlayer.menuBackgroundMusic();
                frame.setVisible(true);
            }
        });
    }

    public static void openMainMenu() {
        frame.getContentPane().removeAll();
        JPanel menu = new MainMenu();
        frame.add(menu);
        frame.pack();
        menu.setFocusable(true);
        manager = null;
    }

    public static void openPlayerMenu() {
        frame.getContentPane().removeAll();
        if (isMultiplayer()) {
            frame.add(manager.playerMenu);
        } else {
            PlayerMenu menu = new PlayerMenu();
            menu.updatePanels();
            frame.add(menu);
        }
        frame.pack();
    }

    public static void openMultiplayerMenu() {
        frame.getContentPane().removeAll();
        frame.add(new MultiplayerMenu());
        frame.pack();
    }

    public static void openRulesMenu() {
        frame.getContentPane().removeAll();
        frame.add(new RulesMenu(frame));
        frame.pack();
    }

    public static void openServerMenu() {
        frame.getContentPane().removeAll();
        frame.add(new ServerMenu());
        frame.pack();
    }

    public static void openClientMenu() {
        frame.getContentPane().removeAll();
        frame.add(new ClientMenu());
        frame.pack();
    }

    public static void openLoadingMenu() {
        frame.getContentPane().removeAll();
        frame.add(new LoadingMenu());
        frame.pack();
    }

    public static void openGameWindow(GameWindow gameWindow) {
        frame.getContentPane().removeAll();
        frame.add(gameWindow);
        frame.pack();
    }

    public static void openGameOverMenu(Player player) {
        frame.getContentPane().removeAll();
        JPanel menu = new GameOverMenu(player);
        frame.add(menu);
        frame.pack();
        menu.setFocusable(true);
    }

    public static boolean isMultiplayer() {
        return manager != null;
    }

    public static boolean isServer() {
        return isMultiplayer() && manager.networkMode == NetworkMode.SERVER;
    }

    public static void createMultiplayerManager(int portNumber, String username) {
        manager = new MultiplayerManager(NetworkMode.SERVER);
        PlayerMenu pm = new PlayerMenu();
        manager.setPlayerMenu(pm);
        manager.startServer(portNumber, username);
        pm.updatePanels();
    }

    public static void createMultiplayerManager(String ipAddress, int portNumber, String username) {
        manager = new MultiplayerManager(NetworkMode.CLIENT);
        PlayerMenu pm = new PlayerMenu();
        manager.setPlayerMenu(pm);
        MainFrame.manager.startClient(ipAddress, portNumber, username);
        pm.updatePanels();
    }
}
