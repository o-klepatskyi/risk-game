package logic;

import gui.game_window.sidePanels.ReinforcementsPanel;
import gui.menus.GameOverMenu;
import gui.game_window.GameMap;
import gui.game_window.GameWindow;
import logic.bot.Bot;
import logic.bot.BotMove;
import logic.maps.Map;
import logic.network.Message;
import logic.network.MessageType;
import logic.network.MultiplayerManager;
import logic.network.NetworkMode;
import util.exceptions.*;

import javax.swing.*;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

            pickFirstPlayer(); // starting point of the game

            Log.initLog();
            Log.write("Game started");
        }
    }

    public void reinforce(int numberOfTroops, Territory src) throws SrcNotStatedException, IllegalNumberOfReinforceTroopsException {
        Territory srcTerritory = findTerritory(src);

        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory is invalid!");

        if(numberOfTroops > currentPlayer.getBonus())
            throw new IllegalNumberOfReinforceTroopsException("Number of troops for reinforcement exceeds bonus!");

        if (isMultiplayer && isCurrentPlayerActive())
            manager.sendMessage(new Message(MessageType.REINFORCE, srcTerritory, numberOfTroops));

        srcTerritory.setTroops(srcTerritory.getTroops() + numberOfTroops);
        currentPlayer.setBonus(currentPlayer.getBonus() - numberOfTroops);

        Log.write(srcTerritory.getName() + " reinforced (" + numberOfTroops + ")");

        if (currentPlayer.getBonus() == 0) {
            gameWindow.game.nextPhase();
        } else {
            gameWindow.update();
            if (currentPlayer.isBot() || (isServer && !isCurrentPlayerOnline())) Bot.makeMove();
        }
    }

    public void attack(Territory srcTerritory, Territory dstTerritory) throws SrcNotStatedException, DstNotStatedException, WrongTerritoriesPairException, IllegalNumberOfAttackTroopsException {
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

        int[] troopsLeft = dice_rolls(attackTroops, defendTroops);

        Log.write(srcTerritory.getOwner().getName() + " vs " + dstTerritory.getOwner().getName());
        Log.write(srcTerritory.getName() + " vs " + dstTerritory.getName());

        if(attackerWins(troopsLeft)) {
            srcTerritory.setTroops(1);
            dstTerritory.setTroops(troopsLeft[0]);
            dstTerritory.setOwner(srcTerritory.getOwner());
            gameMap.drawField();
            gameMap.explosionEffect(dstTerritory.getCoordinates());
            Log.write("Attacker wins!");
            Log.write(srcTerritory.getName() + "(" + srcTerritory.getTroops() +  ") "
                    + dstTerritory.getName() + "(" + dstTerritory.getTroops() + ")");
            checkForGameOver();
        }
        else {
            srcTerritory.setTroops(1);
            dstTerritory.setTroops(troopsLeft[1]);
            gameMap.drawField();
            gameMap.explosionEffect(dstTerritory.getCoordinates());
            Log.write("Defender wins!");
            Log.write(srcTerritory.getName() + "(" + srcTerritory.getTroops() +  ")"
                    + dstTerritory.getName() + "(" + dstTerritory.getTroops() + ")");
        }

        if (gameWindow.game.isMultiplayer) {
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

        if (currentPlayer.isBot() || (isServer && !isCurrentPlayerOnline())) Bot.makeMove();
    }

    /**
     * for client only
     */
    public void attack(String srcName, int srcTroops, Player srcOwner, String dstName, int dstTroops, Player dstOwner) {
        Territory src = findTerritoryInGraph(srcName);
        Territory dst = findTerritoryInGraph(dstName);

        src.setTroops(srcTroops);
        src.setOwner(srcOwner);
        dst.setTroops(dstTroops);
        dst.setOwner(dstOwner);

        gameMap.drawField();
        gameMap.explosionEffect(dst.getCoordinates());
        checkForGameOver();
    }

    /**
     * method for client
     */
    public void fortify(Territory src, Territory dst, int numberOfTroops) throws IllegalNumberOfFortifyTroopsException, WrongTerritoriesPairException, SrcNotStatedException, DstNotStatedException {
        Territory srcTerritory = findTerritory(src);
        Territory dstTerritory = findTerritory(dst);

        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory is invalid!");

        if(dstTerritory == null)
            throw new DstNotStatedException("Destination territory is invalid!");

        if (isMultiplayer && isCurrentPlayerActive()) {
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

        Log.write(srcTerritory.getOwner().getName() + " fortifies territory");
        Log.write(srcTerritory.getName() + " -> " + dstTerritory.getName() + "(" + numberOfTroops + ")");

        nextPhase();
    }

    public void nextPhase() {
        switch (gamePhase) {
            case REINFORCEMENT -> attackPhase();
            case ATTACK -> fortifyPhase();
            case FORTIFY -> nextPlayerTurn();
        }
    }

    /**
     * must be applied in nextPlayerTurn() method
     */
    private void reinforcePhase() {
        gamePhase = GamePhase.REINFORCEMENT;
        gameWindow.update();
        if (isServer) {
            System.out.println("current player");
            System.out.println("is server?: " + isServer);
            System.out.println("is bot?: " + currentPlayer.isBot());
            System.out.println("is online?: " + isCurrentPlayerOnline());
        }

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
        System.out.println(botMove);
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
                        nextPhase();
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

        Log.write(currentPlayer.getName() + " turn");
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
        System.out.println(players);
        removeDeadPlayers();
        System.out.println(players);
        if(players.size() == 1) {
            Log.write("GAME OVER!!!");
            Log.write(currentPlayer.getName() + " IS A WINNER");

            openGameOverMenu();
        }
    }

    public void openGameOverMenu() {
        JFrame frame = gameWindow.getFrame();
        gameWindow.setVisible(false);
        frame.remove(gameWindow);
        frame.add(new GameOverMenu(frame, currentPlayer, manager));
        frame.pack();
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
        Log.write(currentPlayer.getName() + " receives bonus: " + "(" + currentPlayer.getBonus() + ")");
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
        players.removeIf(this::playerIsDead);
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
            if(attackerWins(dice_rolls(attackTroops, defendTroops)))
                attackerWinsCount++;
        }
        double probability = attackerWinsCount / COUNT_OF_DICE_ROLLS;
        probability *= 100;
        return (int) Math.round(probability);
    }

    private int[] dice_rolls(int attackTroops, int defendTroops) {
        attackTroops--;
        while (attackTroops > 0 && defendTroops > 0) {
            int[] attackCast = attackDice(attackTroops);
            int[] defendCast = defendDice(defendTroops);
            int[] totalTroopsLost = compareCasts(attackCast, defendCast);
            attackTroops -= totalTroopsLost[0];
            defendTroops -= totalTroopsLost[1];
        }
        return new int[]{attackTroops, defendTroops};
    }

    private int[] attackDice(int units) {
        int[] cast;
        if(units >= 3) {
            cast = new int[]{Dice.roll(), Dice.roll(), Dice.roll()};
        }
        else if(units == 2) {
            cast = new int[]{Dice.roll(), Dice.roll()};
        }
        else {
            cast = new int[]{Dice.roll()};
        }
        Arrays.sort(cast);
        cast = reversed(cast);
        return cast;
    }

    private int[] defendDice(int units) {
        int[] cast;
        if(units >= 2) {
            cast = new int[]{Dice.roll(), Dice.roll()};
        }
        else {
            cast = new int[]{Dice.roll()};
        }
        Arrays.sort(cast);
        cast = reversed(cast);
        return cast;
    }

    private int[] compareCasts(int[] attack_cast, int[] defend_cast) {
        int[] totalLost = {0, 0};
        for(int i = 0; i < Math.min(attack_cast.length, defend_cast.length); i++) {
            if(attack_cast[i] > defend_cast[i]) {
                totalLost[1]++;
            }
            else {
                totalLost[0]++;
            }
        }
        return totalLost;
    }

    private int[] reversed(int[] array) {
        return IntStream.range(0, array.length).map(i -> array[array.length-i-1]).toArray();
    }

    private boolean attackerWins(int[] troopsLeft) {
        return troopsLeft[0] > 0;
    }

    private void pickFirstPlayer() {
        gamePhase = GamePhase.REINFORCEMENT;
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

    public boolean removePlayer(Player p) {
        return players.remove(p);
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Graph getGameGraph() {
        return gameGraph;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
