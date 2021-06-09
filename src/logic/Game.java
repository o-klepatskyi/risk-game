package logic;

import gui.game_over_window.GameOverWindow;
import gui.game_window.GameMap;
import gui.game_window.GameWindow;
import logic.network.Message;
import logic.network.MessageType;
import logic.network.MultiplayerManager;
import logic.network.NetworkMode;
import util.exceptions.*;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private final ArrayList<Player> players;

    private GameWindow gameWindow;
    private Graph gameGraph;

    private Player currentPlayer;
    private GamePhase gamePhase;
    private GameMap gameMap;

    public final boolean isMultiplayer;
    public final MultiplayerManager manager;

    public final logic.maps.Map map;

    public Game(Collection<Player> players, logic.maps.Map map) {
        if (players.size() < 2) throw new IllegalArgumentException();
        this.manager = null;
        isMultiplayer = false;
        this.players = new ArrayList<>(players);
        this.map = map;

        gameGraph = map.initGraph(this.players);
        start();
    }

    /**
     * FOR MULTIPLAYER
     */
    public Game(Collection<Player> players, logic.maps.Map map, MultiplayerManager manager) {
        if (players.size() < 2) throw new IllegalArgumentException();
        this.manager = manager;
        isMultiplayer = true;
        this.players = new ArrayList<>(players);
        this.map = map;
        gameGraph = map.getGameGraph();
        start();
    }

    private void start() {
        gameMap = new GameMap(this);
        gameWindow = new GameWindow(this);

        pickFirstPlayer();

        Log.initLog();
        Log.write("Game started");
    }

    public boolean isCurrentPlayerActive() {
        return getCurrentPlayer().getName().equals(manager.client.username);
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

    public GamePhase getGameOption() {
        return gamePhase;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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

    /**
     * @return true if attacker wins, else - false
     */
    public boolean attack() throws SrcNotStatedException, DstNotStatedException, WrongTerritoriesPairException, IllegalNumberOfAttackTroopsException {
        Territory srcTerritory = gameMap.getSrcTerritory();
        Territory dstTerritory = gameMap.getDstTerritory();

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
            return true;
        }
        else {
            srcTerritory.setTroops(1);
            dstTerritory.setTroops(troopsLeft[1]);
            gameMap.drawField();
            gameMap.explosionEffect(dstTerritory.getCoordinates());
            Log.write("Defender wins!");
            Log.write(srcTerritory.getName() + "(" + srcTerritory.getTroops() +  ")"
                    + dstTerritory.getName() + "(" + dstTerritory.getTroops() + ")");
            return false;
        }
    }

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

    public void fortify(int numberOfTroops) throws SrcNotStatedException, DstNotStatedException, WrongTerritoriesPairException, IllegalNumberOfFortifyTroopsException {
        Territory srcTerritory = gameMap.getSrcTerritory();
        Territory dstTerritory = gameMap.getDstTerritory();

        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory was not stated!");

        if(dstTerritory == null)
            throw new DstNotStatedException("Destination territory was not stated!");

        fortify(srcTerritory, dstTerritory, numberOfTroops);
    }

    public void fortify(Territory src, Territory dst, int numberOfTroops) throws IllegalNumberOfFortifyTroopsException, WrongTerritoriesPairException, SrcNotStatedException, DstNotStatedException {
        Territory srcTerritory = findTerritory(src);
        Territory dstTerritory = findTerritory(dst);

        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory is invalid!");

        if(dstTerritory == null)
            throw new DstNotStatedException("Destination territory is invalid!");

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
        gameMap.drawField();
        Log.write(srcTerritory.getOwner().getName() + " fortifies territory");
        Log.write(srcTerritory.getName() + " -> " + dstTerritory.getName() + "(" + String.valueOf(numberOfTroops) + ")");
    }

    public void reinforce(int numberOfTroops) throws SrcNotStatedException, IllegalNumberOfReinforceTroopsException {
        Territory srcTerritory = gameMap.getSrcTerritory();
        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory was not stated!");

        if(numberOfTroops > currentPlayer.getBonus())
            throw new IllegalNumberOfReinforceTroopsException("Number of troops for reinforcement exceeds bonus!");

        reinforce(numberOfTroops, srcTerritory);
    }

    public void reinforce(int numberOfTroops, Territory src) throws SrcNotStatedException {
        Territory srcTerritory = findTerritory(src);
        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory is invalid!");

        srcTerritory.setTroops(srcTerritory.getTroops() + numberOfTroops);
        currentPlayer.setBonus(currentPlayer.getBonus() - numberOfTroops);
        gameMap.drawField();
        Log.write(srcTerritory.getName() + " reinforced (" + numberOfTroops + ")");
    }

    private Territory findTerritory(Territory territory) {
        for (Territory t : gameGraph.getTerritories()) {
            if (territory.equals(t)) return t;
        }
        return null;
    }

    public void nextPhase() {
        switch (gamePhase) {
            case REINFORCEMENT:
                attackPhase();
                break;
            case ATTACK:
                fortifyPhase();
                break;
            case FORTIFY:
                nextPlayerTurn();
                break;
        }
    }

    /**
     * must be applied after nextPlayerTurn() call
     *
     * so why not just include it in that method????
     */
    private void reinforcePhase() {
        gamePhase = GamePhase.REINFORCEMENT;
    }

    /**
     * must be applied after reinforcePhase() call
     */
    private void attackPhase() {
        gamePhase = GamePhase.ATTACK;
    }

    /**
     * must be applied after attackPhase() call
     */
    private void fortifyPhase() {
        gamePhase = GamePhase.FORTIFY;
    }

    /**
     * removes dead player and gives turn to next player
     */
    public void nextPlayerTurn() {
//        System.out.println("Old player: " + currentPlayer);
        removeDeadPlayers();

        int index = players.indexOf(currentPlayer);
        index++;
        if(index == players.size())
            index = 0;

        currentPlayer = players.get(index);

//        System.out.println("Current player: " + currentPlayer);
        if (currentPlayer.isBot()) {
            nextPlayerTurn(); // todo: bot integration
            return;
        }

        if (!checkIfPlayerOnline()) return;
        System.out.println("Current player is online OR you are not the server.");
        checkForGameOver();

        Log.write(currentPlayer.getName() + " turn");
        setBonus();

        reinforcePhase();
        gameWindow.updatePhase();
    }

    private boolean checkIfPlayerOnline() {
        if (    isMultiplayer &&
                manager.networkMode == NetworkMode.SERVER &&
                !currentPlayer.isBot() && // todo: check if this is necessary
                !manager.server.userNames.contains(currentPlayer.getName())) {
            try {
                manager.server.broadcast(new Message(MessageType.SKIP_MOVE), manager.server.getThreadByName(manager.client.username));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String username = currentPlayer.getName();
            nextPlayerTurn();
            JOptionPane.showMessageDialog(null,
                    "Player " + username  + " has lost connection. Skipping his move.",
                    "Player disconnected.",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void checkForGameOver() {
        removeDeadPlayers();
        if(players.size() == 1) {
            Log.write("GAME OVER!!!");
            Log.write(currentPlayer.getName() + " IS A WINNER");

            openGameOverMenu();
        }
    }

    private boolean anyBotsLeft() {
        for (Player p : players) {
            if (p.isBot()) return true;
        }
        return false;
    }

    public void openGameOverMenu() {
        System.out.println("opening game over window");
        JFrame frame = gameWindow.getFrame();
        gameWindow.setVisible(false);
        frame.remove(gameWindow);
        frame.add(new GameOverWindow(frame, currentPlayer, manager));
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
}
