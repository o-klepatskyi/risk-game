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

    private static ArrayList<AttackUnit> attackUnits = new ArrayList<>();
    private static int numberOfAttacks = -1;

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
                        AttackUnit attackUnit = attackUnits.remove(0);
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
                FortifyUnit fortifyUnit = getFortifyUnit();
                if(fortifyUnit == null) {
                    move = new BotMove(BotMoveType.END_FORTIFY);
                }
                else {
                    move = new BotMove(BotMoveType.FORTIFY,
                            fortifyUnit.getSrc(), fortifyUnit.getDst(), fortifyUnit.getTroops());
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

    private static ArrayList<AttackUnit> possibleAttacks() {
        Player player = game.getCurrentPlayer();
        ArrayList<AttackUnit> attackUnits = new ArrayList<>();

        ArrayList<Territory> playerTerritories = game.gameGraph.getTerritories(player);
        for(Territory src : playerTerritories) {
            for(Territory dst : game.gameGraph.getAdjacentTerritories(src)) {
                if(src.getTroops() > 1) {
                    try {
                        AttackUnit attackUnit = new AttackUnit(src, dst, game.calculateProbability(src, dst));
                        attackUnits.add(attackUnit);
                    } catch (SrcNotStatedException | DstNotStatedException | WrongTerritoriesPairException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        attackUnits.sort(new AttackUnitComparator());
        Collections.reverse(attackUnits);
        return attackUnits;
    }

    private static int getNumberOfAttacks() {
        int highProbabilityCount = 0, mediumProbabilityCount = 0;
        for(AttackUnit attackUnit : attackUnits) {
            if(attackUnit.getProbability() >= 50)
                highProbabilityCount++;
            else if(attackUnit.getProbability() >= 25) {
                mediumProbabilityCount++;
            }
        }

        if(highProbabilityCount > 0) {
            return ThreadLocalRandom.current().nextInt(1, highProbabilityCount+1);
        }
        else {
            if(mediumProbabilityCount > 0)
                return ThreadLocalRandom.current().nextInt(0, 2);
            else {
                return 0;
            }
        }
    }

    private static FortifyUnit getFortifyUnit() {
        Player player = game.getCurrentPlayer();
        Graph graph = game.gameGraph;
        ArrayList<Territory> territories = graph.getTerritories(player);
        territories.removeIf(territory -> territory.getTroops() == 1);
        territories.removeIf(territory -> graph.getConnectedTerritories(territory).size() == 0);

        // check for smart fortification
        // if territory is inside - fortify
        for(Territory src : territories) {
            ArrayList<Territory> connected = graph.getConnectedTerritories(src);
            if(Graph.samePlayerTerritories(connected, player)) {
                Territory dst = connected.get(new Random().nextInt(connected.size()));
                while(graph.getAdjacentTerritories(dst).size() == 0) {
                    if(connected.size() > 1) {
                        connected.remove(dst);
                        dst = connected.get(new Random().nextInt(connected.size()));
                    }
                    else {
                        return new FortifyUnit(src, dst, src.getTroops()-1);
                    }
                }
                return new FortifyUnit(src, dst, src.getTroops()-1);
            }
        }

        // if no smart fortification -> choose random territories to fortify

        // 50/50 fortify or not
        if(Math.random() >= 0.5 && territories.size() > 0) {
            Territory src = territories.get(new Random().nextInt(territories.size()));
            ArrayList<Territory> connected = graph.getConnectedTerritories(src);
            Territory dst = connected.get(new Random().nextInt(connected.size()));
            return new FortifyUnit(src, dst, src.getTroops()-1);
        }

        return null;
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
