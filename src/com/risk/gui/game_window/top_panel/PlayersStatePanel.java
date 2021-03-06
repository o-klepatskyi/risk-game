package com.risk.gui.game_window.top_panel;

import com.risk.logic.Game;
import com.risk.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class PlayersStatePanel extends JPanel {
    private final int WIDTH = TopPanel.WIDTH;
    private final int HEIGHT = TopPanel.HEIGHT/2;

    private final Game game;
    private final ArrayList<PlayerBox> panels = new ArrayList<>();
    private ArrayList<Player> players;

    PlayersStatePanel(Game game) {
        this.game = game;

        initPanel();
    }

    private void initPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        java.util.List<Player> players = game.getPlayers();
        for (Player p : players) {
            PlayerBox box = new PlayerBox(p, WIDTH/players.size(), HEIGHT);
            panels.add(box);
            add(box);
        }
    }

    public void updatePlayers() {
        players = new ArrayList<>(game.getPlayers());
        for (PlayerBox box : panels) {
            Player p = findPlayer(box.getPlayer());
            if (p == null) {
                box.update(0, false);
            } else {
                box.update(game.gameGraph.getTerritories(p).size(),
                        p.getColor().equals(game.getCurrentPlayer().getColor()));
            }
        }
    }

    private Player findPlayer(Player player) {
        for (Player p : players) {
            if (p.getColor().equals(player.getColor())) return p;
        }
        return null;
    }
}
