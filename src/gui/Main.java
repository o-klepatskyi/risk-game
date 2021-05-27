package gui;

import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Ivan", Color.RED, false);
        Player p2 = new Player("Oleh", Color.BLUE, false);
        players.add(p1);
        players.add(p2);
        Game game = new Game(players);
        frame.add(game.getGameWindow());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
