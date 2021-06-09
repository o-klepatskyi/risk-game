package logic.bot;

import logic.GamePhase;
import logic.Graph;
import logic.Player;
import logic.Territory;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class Bot {

    public static BotMove makeMove(GamePhase moveType, Graph gameGraph, Player player) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (player == null || gameGraph == null)
            throw new InvalidParameterException("null values");
        if (!player.isBot())
            throw new InvalidParameterException("Player is not bot");
        switch (moveType) {
            case REINFORCEMENT:
                if (player.getBonus() == 0) {
                    return new BotMove(BotMoveType.END_REINFORCEMENT);
                }
                return new BotMove(BotMoveType.REINFORCEMENT, getRandomTerritory(gameGraph,player), player.getBonus());
            case ATTACK:
                return new BotMove(BotMoveType.END_ATTACK);
            case FORTIFY:
                return new BotMove(BotMoveType.END_FORTIFY);
        }
        return null;
    }

    private static Territory getRandomTerritory(Graph gameGraph, Player player) {
        List<Territory> playerTerritories = gameGraph
                .getTerritories().stream()
                .filter(x -> x.getOwner().equals(player))
                .collect(Collectors.toList());
        return playerTerritories.get(new Random().nextInt(playerTerritories.size()));
    }
}
