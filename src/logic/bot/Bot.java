package logic.bot;

import logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class Bot {

    private static Game game;

    public static void makeMove() {
        if (!game.getCurrentPlayer().isBot()) return;
        if (game.manager == null) return;
        Player player = game.getCurrentPlayer();
        Graph gameGraph = game.getGameGraph();
        GamePhase phase = game.getGamePhase();
        BotMove move = null;
        if (player == null || gameGraph == null)
            throw new InvalidParameterException("null values");
        if (!player.isBot())
            throw new InvalidParameterException("Player is not bot");
        if (game == null)
            throw new InvalidParameterException("Game is not set");

        switch (phase) {
            case REINFORCEMENT:
                if (player.getBonus() != 0) {
                    move =  new BotMove(BotMoveType.REINFORCEMENT, getRandomTerritory(gameGraph,player), player.getBonus());
                }
                break;
            case ATTACK:
                move = new BotMove(BotMoveType.END_ATTACK);
                break;
            case FORTIFY:
                move = new BotMove(BotMoveType.END_FORTIFY);
                break;
        }
        final BotMove botMove = move;
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.botMove(botMove);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private static Territory getRandomTerritory(Graph gameGraph, Player player) {
        List<Territory> playerTerritories = gameGraph.getTerritories(player);
        return playerTerritories.get(new Random().nextInt(playerTerritories.size()));
    }

    public static void setGame(Game g) {
        game = g;
    }
}
