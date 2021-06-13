package com.risk.logic.bot;

import com.risk.logic.*;
import com.risk.util.exceptions.DstNotStatedException;
import com.risk.util.exceptions.SrcNotStatedException;
import com.risk.util.exceptions.WrongTerritoriesPairException;

import javax.swing.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Bot {
    private static Game game;

    private static ArrayList<TerritoriesUnit> attackUnits = new ArrayList<>();
    private static int numberOfAttacks = -1;

    private static boolean fortified = true;

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
        Graph     gameGraph   = game.gameGraph;
        GamePhase phase       = game.getGamePhase();

        if (player == null || gameGraph == null)
            throw new InvalidParameterException("null values");
        if (!game.isMultiplayer && !player.isBot())
            throw new InvalidParameterException("Player is not bot.");

        BotMove move = null;

        switch (phase) {
            case REINFORCEMENT:
                if(player.getBonus() > 0) {
                    Territory territoryToIncrement = getRandomTerritoryOfPlayer(game.gameGraph, player);
                    move =  new BotMove(BotMoveType.REINFORCEMENT, territoryToIncrement, 1);
                }
                break;
            case ATTACK:
                attackUnits = possibleAttacks();

                if(numberOfAttacks == -1)
                    numberOfAttacks = getNumberOfAttacks();

                if(numberOfAttacks > 0) {
                    if(attackUnits.size() > 0) {
                        TerritoriesUnit attackUnit = attackUnits.remove(0);
                        move = new BotMove(BotMoveType.ATTACK, attackUnit.getSrc(), attackUnit.getDst());
                        numberOfAttacks--;
                    }
                    else {
                        numberOfAttacks = -1;
                        move = new BotMove(BotMoveType.END_ATTACK);
                    }
                }
                else {
                    numberOfAttacks = -1;
                    move = new BotMove(BotMoveType.END_ATTACK);
                }
                break;
            case FORTIFY:
                if(false) {
                    fortified = true;
                }
                else {
                    move = new BotMove(BotMoveType.END_FORTIFY);
                    fortified = false;
                }
                break;
        }


        final BotMove botMove = move;
        int delay;
        if (move.type == BotMoveType.REINFORCEMENT) {
            delay = 100;
        } else {
            delay = 1000;
        }
        Timer timer = new Timer(delay, e -> {
            game.botMove(botMove);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private static ArrayList<TerritoriesUnit> possibleAttacks() {
        Player player = game.getCurrentPlayer();
        ArrayList<TerritoriesUnit> attackUnits = new ArrayList<>();

        ArrayList<Territory> playerTerritories = game.gameGraph.getTerritories(player);
        for(Territory src : playerTerritories) {
            for(Territory dst : game.gameGraph.getAdjacentTerritories(src)) {
                if(src.getTroops() > 1) {
                    try {
                        TerritoriesUnit attackUnit = new TerritoriesUnit(src, dst, game.calculateProbability(src, dst));
                        attackUnits.add(attackUnit);
                    } catch (SrcNotStatedException | DstNotStatedException | WrongTerritoriesPairException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        attackUnits.sort(new TerritoriesUnitComparator());
        Collections.reverse(attackUnits);
        return attackUnits;
    }

    private static int getNumberOfAttacks() {
        int highProbabilityCount = 0, mediumProbabilityCount = 0;
        for(TerritoriesUnit attackUnit : attackUnits) {
            if(attackUnit.getProbability() >= 50)
                highProbabilityCount++;
            else if(attackUnit.getProbability() >= 25) {
                mediumProbabilityCount++;
            }
        }

        if(highProbabilityCount > 0) {
            return ThreadLocalRandom.current().nextInt(0, highProbabilityCount+1);
        }
        else {
            if(mediumProbabilityCount > 0)
                return ThreadLocalRandom.current().nextInt(0, 2);
            else {
                return 0;
            }
        }
    }

    private static void fortify() {

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
