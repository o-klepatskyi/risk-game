package com.risk.logic;

import com.risk.gui.Main;
import com.risk.gui.game_window.side_panels.ReinforcementsPanel;
import com.risk.gui.game_window.GameMap;
import com.risk.gui.game_window.GameWindow;
import com.risk.logic.bot.Bot;
import com.risk.logic.bot.BotMove;
import com.risk.logic.maps.Map;
import com.risk.logic.network.Message;
import com.risk.logic.network.MessageType;
import com.risk.logic.network.MultiplayerManager;
import com.risk.logic.network.NetworkMode;
import com.risk.util.exceptions.*;
import com.risk.util.resources.SoundPlayer;

import javax.swing.*;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final ArrayList<Player> players;

    public final GameWindow gameWindow;
    public final GameMap gameMap;
    public final Graph gameGraph;
    public final Map map;

    private Player currentPlayer;
    private GamePhase gamePhase;

    public final boolean isMultiplayer;
    public final MultiplayerManager manager;

    private boolean isStarted = false;
    private boolean isServer;

    public Game(Collection<Player> players, Map map) {
        if (players.size() < 2) throw new IllegalArgumentException();
        this.manager = null;
        isMultiplayer = false;
        isServer = false;
        this.players = new ArrayList<>(players);
        this.map = map;

        gameGraph = map.initGraph(this.players);

        gameMap = new GameMap(this);
        gameWindow = new GameWindow(this);
    }

    /**
     * FOR MULTIPLAYER
     */
    public Game(Collection<Player> players, Map map, MultiplayerManager manager) {
        if (players.size() < 2) throw new IllegalArgumentException();
        this.manager = manager;
        isMultiplayer = true;
        isServer = manager.networkMode == NetworkMode.SERVER;
        this.players = new ArrayList<>(players);
        this.map = map;
        gameGraph = map.getGameGraph();

        gameMap = new GameMap(this);
        gameWindow = new GameWindow(this);
    }

    public void start() {
        if (!isStarted) {
            isStarted = true;
            Bot.setGame(this);


            Log.initLog();
            Log.write("Game started");

            pickFirstPlayer(); // starting point of the game
        }
    }

    public void reinforce(int numberOfTroops, Territory src) throws SrcNotStatedException, IllegalNumberOfReinforceTroopsException {
        if (gamePhase != GamePhase.REINFORCEMENT)
            throw new IllegalStateException("Wrong game phase: " + gamePhase);

        Territory srcTerritory = findTerritory(src);

        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory is invalid!");

        if(numberOfTroops > currentPlayer.getBonus())
            throw new IllegalNumberOfReinforceTroopsException("Number of troops for reinforcement exceeds bonus!");

        if (isMultiplayer && (isCurrentPlayerActive() || (isServer && currentPlayer.isBot())))
            manager.sendMessage(new Message(MessageType.REINFORCE, srcTerritory, numberOfTroops));

        srcTerritory.setTroops(srcTerritory.getTroops() + numberOfTroops);
        currentPlayer.setBonus(currentPlayer.getBonus() - numberOfTroops);

        SoundPlayer.reinforceSound();
        Log.write(srcTerritory.getName() + " reinforced (" + numberOfTroops + ")");

        if (currentPlayer.getBonus() == 0) {
            gameWindow.game.nextPhase();
        } else {
            gameWindow.update();

            if (isMultiplayer) {
                if (isServer && (currentPlayer.isBot() || !isCurrentPlayerOnline())) Bot.makeMove();
            } else if (currentPlayer.isBot()) Bot.makeMove();
        }
    }

    public void attack(Territory srcTerritory, Territory dstTerritory) throws SrcNotStatedException, DstNotStatedException, WrongTerritoriesPairException, IllegalNumberOfAttackTroopsException {
        if (gamePhase != GamePhase.ATTACK)
            throw new IllegalStateException("Wrong game phase: " + gamePhase);

        Territory src = Territory.getIdentical(srcTerritory), dst = Territory.getIdentical(dstTerritory);
        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory was not stated!");

        if(dstTerritory == null)
            throw new DstNotStatedException("Destination territory was not stated!");

        if(!gameGraph.hasEdge(srcTerritory, dstTerritory))
            throw new WrongTerritoriesPairException("These territories are not adjacent!");

        int attackTroops = srcTerritory.getTroops();
        int defendTroops = dstTerritory.getTroops();

        if(attackTroops == 1)
            throw new IllegalNumberOfAttackTroopsException("Not enough troops to attack (1)!");

        int[] troopsLeft = Dice.dice_rolls(attackTroops, defendTroops);

        Log.write(srcTerritory.getOwner().getName()
                + "(" + srcTerritory.getOwner().getColor() + ")" +
                " vs " + dstTerritory.getOwner().getName());

        Log.write(srcTerritory.getName() + " vs " + dstTerritory.getName());

        if(Dice.attackerWins(troopsLeft)) {
            srcTerritory.setTroops(1);
            dstTerritory.setTroops(troopsLeft[0]);
            dstTerritory.setOwner(srcTerritory.getOwner());
            gameWindow.update();
            gameMap.explosionEffect(dstTerritory.getCoordinates());
            Log.write("Attacker wins!");
            Log.write(srcTerritory.getName() + "(" + srcTerritory.getTroops() +  ") "
                    + dstTerritory.getName() + "(" + dstTerritory.getTroops() + ")");
            checkForGameOver();
        }
        else {
            srcTerritory.setTroops(1);
            dstTerritory.setTroops(troopsLeft[1]);
            gameWindow.update();
            gameMap.explosionEffect(dstTerritory.getCoordinates());
            Log.write("Defender wins!");
            Log.write(srcTerritory.getName() + "(" + srcTerritory.getTroops() +  ")"
                    + dstTerritory.getName() + "(" + dstTerritory.getTroops() + ")");
        }

        if (isMultiplayer && (isCurrentPlayerActive() || (isServer && currentPlayer.isBot()))) {
            Territory newSrc = gameWindow.game.findTerritoryInGraph(src.getName()),
                    newDst = gameWindow.game.findTerritoryInGraph(dst.getName());

            gameWindow.game.manager.sendMessage(new Message(MessageType.ATTACK,
                    newSrc.getName(),
                    newSrc.getTroops(),
                    newSrc.getOwner(),
                    newDst.getName(),
                    newDst.getTroops(),
                    newDst.getOwner()));
        }

        if (isMultiplayer) {
            if (isServer && (currentPlayer.isBot() || !isCurrentPlayerOnline())) Bot.makeMove();
        } else if (currentPlayer.isBot()) Bot.makeMove();
    }

    /**
     * for client only
     */
    public void attack(String srcName, int srcTroops, Player srcOwner, String dstName, int dstTroops, Player dstOwner) {
        if (gamePhase != GamePhase.ATTACK)
            throw new IllegalStateException("Wrong game phase: " + gamePhase);

        Territory src = findTerritoryInGraph(srcName);
        Territory dst = findTerritoryInGraph(dstName);

        src.setTroops(srcTroops);
        src.setOwner(srcOwner);
        dst.setTroops(dstTroops);
        dst.setOwner(dstOwner);

        gameWindow.update();
        gameMap.explosionEffect(dst.getCoordinates());
        checkForGameOver();
    }

    /**
     * method for client
     */
    public void fortify(Territory src, Territory dst, int numberOfTroops) throws IllegalNumberOfFortifyTroopsException, WrongTerritoriesPairException, SrcNotStatedException, DstNotStatedException {
        if (gamePhase != GamePhase.FORTIFY)
            throw new IllegalStateException("Wrong game phase: " + gamePhase);

        Territory srcTerritory = findTerritory(src);
        Territory dstTerritory = findTerritory(dst);

        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory is invalid!");

        if(dstTerritory == null)
            throw new DstNotStatedException("Destination territory is invalid!");

        if (isMultiplayer && (isCurrentPlayerActive() || (isServer && currentPlayer.isBot()))) {
            manager.sendMessage(
                    new Message(MessageType.FORTIFY,
                            Territory.getIdentical(srcTerritory),
                            Territory.getIdentical(dstTerritory),
                            numberOfTroops));
        }

        ArrayList<Territory> connectedTerritories = gameGraph.getConnectedTerritories(srcTerritory);
        if(connectedTerritories.contains(dstTerritory)) {
            if(numberOfTroops <= srcTerritory.getTroops()-1) {
                dstTerritory.setTroops(dstTerritory.getTroops() + numberOfTroops);
                srcTerritory.setTroops(srcTerritory.getTroops() - numberOfTroops);
            }
            else {
                throw new IllegalNumberOfFortifyTroopsException("Number of troops to transfer exceeds number of available troops. Cannot be 0 troops on territory, must be at least 1");
            }
        }
        else {
            throw new WrongTerritoriesPairException("These territories are not connected!");
        }

        Log.write(srcTerritory.getOwner().getName() + "(" + srcTerritory.getOwner().getColor() + ")" + " fortifies territory");
        Log.write(srcTerritory.getName() + " -> " + dstTerritory.getName() + "(" + numberOfTroops + ")");

        nextPhase();
    }

    public void skipFortify() {
        if (isMultiplayer && (isCurrentPlayerActive() || (isServer && currentPlayer.isBot()))) {
            gameWindow.game.manager.sendMessage(new Message(MessageType.END_FORTIFY));
        }
        nextPhase();
    }

    public void nextPhase() {
        switch (gamePhase) {
            case REINFORCEMENT -> attackPhase();

            case ATTACK -> {
                if (isMultiplayer && (isCurrentPlayerActive() || (isServer && currentPlayer.isBot()))) {
                    gameWindow.game.manager.sendMessage(new Message(MessageType.END_ATTACK));
                }
                fortifyPhase();
            }
            case FORTIFY -> {
                nextPlayerTurn();
            }
        }
    }

    /**
     * must be applied in nextPlayerTurn() method
     */
    private void reinforcePhase() {
        gamePhase = GamePhase.REINFORCEMENT;
        gameWindow.update();

        if (isMultiplayer) {
            if (isServer && (currentPlayer.isBot() || !isCurrentPlayerOnline())) {
                Bot.makeMove();
            }
        } else if (currentPlayer.isBot()) {
            Bot.makeMove();
        }
    }

    /**
     * must be applied after reinforcePhase() call
     */
    private void attackPhase() {
        gamePhase = GamePhase.ATTACK;
        gameWindow.update();
        if (isMultiplayer) {
            if (isServer && (currentPlayer.isBot() || !isCurrentPlayerOnline())) {
                Bot.makeMove();
            }
        } else if (currentPlayer.isBot()) {
            Bot.makeMove();
        }
    }

    /**
     * must be applied after attackPhase() call
     */
    private void fortifyPhase() {
        gamePhase = GamePhase.FORTIFY;
        gameWindow.update();
        if (isMultiplayer) {
            if (isServer && (currentPlayer.isBot() || !isCurrentPlayerOnline())) {
                Bot.makeMove();
            }
        } else if (currentPlayer.isBot()) {
            Bot.makeMove();
        }
    }

    public void botMove(BotMove botMove) {
        //System.out.println(botMove);
        if (botMove != null) {
            try {
                switch (botMove.type) {
                    case REINFORCEMENT:
                        if (gamePhase != GamePhase.REINFORCEMENT) {
                            throw new InvalidParameterException("Wrong type move " + botMove.type + " for game phase " + gamePhase);
                        }
                        reinforce(botMove.troops, botMove.src);
                        break;
                    case ATTACK:
                        if (gamePhase != GamePhase.ATTACK) {
                            throw new InvalidParameterException("Wrong type move " + botMove.type + " for game phase " + gamePhase);
                        }
                        attack(botMove.src, botMove.dst);
                        break;
                    case END_ATTACK:
                        if (gamePhase != GamePhase.ATTACK) {
                            throw new InvalidParameterException("Wrong type move " + botMove.type + " for game phase " + gamePhase);
                        }
                        nextPhase();
                        break;
                    case END_FORTIFY:
                        if (gamePhase != GamePhase.FORTIFY) {
                            throw new InvalidParameterException("Wrong type move " + botMove.type + " for game phase " + gamePhase);
                        }
                        skipFortify();
                        break;
                    case FORTIFY:
                        if (gamePhase != GamePhase.FORTIFY) {
                            throw new InvalidParameterException("Wrong type move " + botMove.type + " for game phase " + gamePhase);
                        }
                        fortify(botMove.src, botMove.dst, botMove.troops);
                        break;
                    default:
                        throw new InvalidParameterException("Wrong type move " + botMove.type + " for game phase " + gamePhase);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else throw new InvalidParameterException("null value");
    }

    /**
     * removes dead player and gives turn to next player
     */
    public void nextPlayerTurn() {
        removeDeadPlayers();

        int index = players.indexOf(currentPlayer);
        index++;
        if(index == players.size())
            index = 0;

        currentPlayer = players.get(index);

        checkForGameOver();

        Log.write(currentPlayer.getName() + "(" + currentPlayer.getColor() + ")" + " turn");

        setBonus();
        gameWindow.update();
        reinforcePhase();
    }

    public boolean isCurrentPlayerOnline() {
        if (!isMultiplayer)
            throw new InvalidParameterException("Only for multiplayer");
        if (manager.networkMode != NetworkMode.SERVER)
            throw new InvalidParameterException("Called not from server");

        return manager.server.userNames.contains(currentPlayer.getName());
    }

    private void checkForGameOver() {
        removeDeadPlayers();

        if(players.size() == 1) {
            Log.write("GAME OVER!!!");
            Log.write(currentPlayer.getName() + "(" + currentPlayer.getColor() + ")" + " IS A WINNER");

            openGameOverMenu();
        }
    }

    public void openGameOverMenu() {
        Main.openGameOverMenu(currentPlayer);
    }

    private ArrayList<String> continentLabels;

    private void setBonus() {
        int bonus = gameGraph.getTerritories(currentPlayer).size() / 3;
        bonus = Math.max(bonus, 3);

        continentLabels = new ArrayList<>();
        continentLabels.add("From territories - " + bonus);

        ArrayList<Territory> territories = gameGraph.getTerritories(currentPlayer);

        for (Continent continent : map.getContinents()) {
            if (territories.containsAll(continent.getTerritories())) {
                continentLabels.add(continent.name + " - " + continent.bonus);
                bonus += continent.bonus;
            }
        }
        currentPlayer.setBonus(bonus);
        ReinforcementsPanel.setBonus(bonus);
        Log.write(currentPlayer.getName() +
                "(" + currentPlayer.getColor() + ")" +
                " receives bonus: " + "(" + currentPlayer.getBonus() + ")");
    }

    public ArrayList<String> getContinentsLabels() {
        return continentLabels;
    }

    public Territory findTerritoryInGraph(String name) {
        for(Territory territory : gameGraph.getTerritories()) {
            if(territory.getName().equals(name))
                return territory;
        }
        return null;
    }

    private void removeDeadPlayers() {
        ArrayList<Player> playersRemoved = new ArrayList<>();
        for (Player player : players) {
            if (playerIsDead(player)) {
                playersRemoved.add(player);
            }
        }
        players.removeAll(playersRemoved);
        for (Player p : playersRemoved) {
            Log.write("Player eliminated: " + p.getName() + "(" + p.getColor() + ")");
        }
        if (isMultiplayer &&
            !players.stream().map(Player::getName).collect(Collectors.toList()).contains(manager.client.username)) {
            showEliminatedMessage();
        }
    }

    private boolean shownEliminatedMessage = false;

    private void showEliminatedMessage() {
        if (shownEliminatedMessage) return;
        if (players.size() == 1) {
            return;
        }
        Object[] options = {"Stay in game",
                "Leave"};
        int n = JOptionPane.showOptionDialog(null,
                "You have been eliminated from game.\nDo you like to stay here?",
                "GAME OVER",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (n != 0) {
            manager.closeClient();
        }
        shownEliminatedMessage = true;
    }

    private boolean playerIsDead(Player player) {
        for(Territory territory : gameGraph.getTerritories()) {
            if(territory.getOwner().equals(player))
                return false;
        }
        return true;
    }

    /**
     * @return probability of a battle between GameMap src and dst territories
     */
    public int calculateProbability() throws SrcNotStatedException, DstNotStatedException, WrongTerritoriesPairException {
        Territory srcTerritory = gameMap.getSrcTerritory();
        Territory dstTerritory = gameMap.getDstTerritory();

        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory was not stated!");

        if(dstTerritory == null)
            throw new DstNotStatedException("Destination territory was not stated!");

        if(!gameGraph.hasEdge(srcTerritory, dstTerritory))
            throw new WrongTerritoriesPairException("These territories are not adjacent");

        int attackTroops = srcTerritory.getTroops();
        int defendTroops = dstTerritory.getTroops();

        int attackerWinsCount = 0;
        final double COUNT_OF_DICE_ROLLS = 10000;
        for(int i = 0; i < COUNT_OF_DICE_ROLLS; i++) {
            if(Dice.attackerWins(Dice.dice_rolls(attackTroops, defendTroops)))
                attackerWinsCount++;
        }
        double probability = attackerWinsCount / COUNT_OF_DICE_ROLLS;
        probability *= 100;
        return (int) Math.round(probability);
    }


    private void pickFirstPlayer() {
        gamePhase = GamePhase.FORTIFY;
        currentPlayer = players.get(players.size()-1);
        nextPlayerTurn();
    }

    public boolean isCurrentPlayerActive() {
        if (!isMultiplayer)
            throw new InvalidParameterException("Only for multiplayer");

        return getCurrentPlayer().getName().equals(manager.client.username) && !getCurrentPlayer().isBot();
    }

    private Territory findTerritory(Territory territory) {
        for (Territory t : gameGraph.getTerritories()) {
            if (territory.getName().equals(t.getName())) return t;
        }
        return null;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {return new ArrayList<>(players);}
}
