package com.risk.gui;

import com.risk.gui.game_window.GameWindow;
import com.risk.gui.menus.*;
import com.risk.gui.player_menu.PlayerMenu;
import com.risk.logic.Player;
import com.risk.logic.network.MultiplayerManager;
import com.risk.logic.network.NetworkMode;
import com.risk.util.resources.SoundPlayer;
import javax.swing.*;

public class Main {
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
        if (isMultiplayer()) {
            if(isServer()) manager.closeServer();
            else manager.closeClient();
            manager = null;
        }
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
        Main.manager.startClient(ipAddress, portNumber, username);
        pm.updatePanels();
    }

    public static void openSettingsMenu() {
        frame.getContentPane().removeAll();
        frame.add(new SettingsMenu());
        frame.pack();
    }

    private static boolean isShuffling = true;

    public static void setShuffledOption(boolean b) {
        isShuffling = b;
    }

    public static boolean isShuffling() {
        return isShuffling;
    }
}
