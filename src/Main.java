import gui.main_menu.MainMenu;
import gui.player_menu.PlayerMenu;
import logic.Game;
import logic.Player;
import util.res.Fonts;
import util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Fonts.initFonts();
        openMainMenu();
        SoundPlayer.menuBackgroundMusic();
    }

    private static void openMainMenu() {
        JFrame frame = new JFrame();
        frame.add(new MainMenu(frame));
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static void openGameMenu() {
        JFrame frame = new JFrame();
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Ivan", Color.RED, false);
        Player p2 = new Player("Oleh", Color.BLUE, false);
        //Player p3 = new Player("Bot", Color.YELLOW, true);
        players.add(p1);
        players.add(p2);
        //players.add(p3);
        Game game = new Game(players); // todo
        frame.add(game.getGameWindow());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static void openPlayerSettingsMenu() {
        JFrame frame = new JFrame("Risk - Game settings");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(PlayerMenu.WIDTH, PlayerMenu.HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new PlayerMenu(frame));
        frame.validate();
    }

}
