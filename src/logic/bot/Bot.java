package logic.bot;

import logic.*;

import javax.swing.*;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Random;

public abstract class Bot {
    private static Game game;

    /**
     * DO NOT call from this class. Will definitely cause problems.
     *
     * If bot can do several moves in one phase, method is called in
     * Game class after it have finished computing current move.
     *
     */
    public static void makeMove() {
        if (game == null)
            throw new InvalidParameterException("Game is not set");
        if (game.isMultiplayer && game.isCurrentPlayerOnline())
            throw new InvalidParameterException("Current player is neither bot nor offline");

        Player    player      = game.getCurrentPlayer();
        Graph     gameGraph   = game.getGameGraph();
        GamePhase phase       = game.getGamePhase();

        if (player == null || gameGraph == null)
            throw new InvalidParameterException("null values");
        if (!game.isMultiplayer && !player.isBot())
            throw new InvalidParameterException("Player is not bot.");

        BotMove move = null;

        switch (phase) {
            case REINFORCEMENT:
                // todo algorithm
                if (player.getBonus() != 0) {
                    move =  new BotMove(BotMoveType.REINFORCEMENT, getRandomTerritoryOfPlayer(gameGraph,player), 1);
                }
                break;
            case ATTACK:
                // todo algorithm
                move = new BotMove(BotMoveType.END_ATTACK);
                break;
            case FORTIFY:
                // todo algorithm
                move = new BotMove(BotMoveType.END_FORTIFY);
                break;
        }
        final BotMove botMove = move;
        int delay;
        if (move.type == BotMoveType.REINFORCEMENT) {
            delay = 50;
        } else {
            delay = 1000;
        }
        Timer timer = new Timer(delay, e -> {
            game.botMove(botMove);
        });
        timer.setRepeats(false);
        timer.start();
    }

    // todo: maybe move this to Graph class?
    private static Territory getRandomTerritoryOfPlayer(Graph gameGraph, Player player) {
        List<Territory> playerTerritories = gameGraph.getTerritories(player);
        return playerTerritories.get(new Random().nextInt(playerTerritories.size()));
    }

    public static void setGame(Game g) {
        game = g;
    }
}
