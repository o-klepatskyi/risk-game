package logic;

import gui.gameWindow.GameMap;
import gui.gameWindow.GameWindow;
import util.*;

import java.awt.*;
import java.util.*;
import java.util.stream.IntStream;

import static gui.gameWindow.GameWindow.HEIGHT;
import static gui.gameWindow.GameWindow.WIDTH;

public class Game {
    private final int numberOfTerritories = 42;

    private ArrayList<Player> players;
    private int numberOfPlayers;

    private ArrayList<Territory> territories;
    private final String[] namesOfTerritories = {
            "Afghanistan", "Alaska", "Alberta", "Argentina", "Brazil", "Central America",
            "China", "Congo", "East Africa", "Eastern Australia", "Eastern United States", "Egypt",
            "Great Britain", "Greenland", "Iceland", "India", "Indonesia", "Irkutsk",
            "Japan", "Kamchatka", "Madagascar", "Middle East", "Mongolia", "New Guinea",
            "North Africa", "Northern Europe", "Northwest Territory", "Ontario", "Peru", "Quebec",
            "Scandinavia", "Siam", "Siberia", "South Africa", "Southern Europe", "Ukraine",
            "Ural", "Venezuela", "Western Australia", "Western Europe", "Western United States", "Yakutsk"
    };

    private final Coordinates[] coordinates = {
            new Coordinates(640, 240), new Coordinates(60, 105), new Coordinates(150, 155), new Coordinates(245, 510), new Coordinates(300, 430), new Coordinates(165, 315),
            new Coordinates(750, 290), new Coordinates(515, 480), new Coordinates(550, 430), new Coordinates(880, 535), new Coordinates(220, 250), new Coordinates(515, 370),
            new Coordinates(385, 210), new Coordinates(330, 70), new Coordinates(405, 135), new Coordinates(680, 330), new Coordinates(780, 475), new Coordinates(750, 175),
            new Coordinates(870, 235), new Coordinates(850, 90), new Coordinates(600, 570), new Coordinates(580, 345), new Coordinates(770, 230), new Coordinates(865, 450),
            new Coordinates(440, 400), new Coordinates(475, 220), new Coordinates(150, 100), new Coordinates(210, 165), new Coordinates(220, 445), new Coordinates(275, 165),
            new Coordinates(475, 140), new Coordinates(765, 365), new Coordinates(700, 115), new Coordinates(520, 570), new Coordinates(480, 275), new Coordinates(550, 195),
            new Coordinates(650, 150), new Coordinates(220, 365), new Coordinates(820, 565), new Coordinates(400, 305), new Coordinates(145, 225), new Coordinates(770, 85)
    };

    private GameWindow gameWindow;
    private Graph gameGraph;

    private Player currentPlayer;
    private GameOption gameOption;
    private GameMap gameMap;

    public Game(ArrayList<Player> players) {
        this.players = players;
        numberOfPlayers = players.size();

        initializeTerritories();
        gameGraph = new Graph();
        createGraphFromTerritories();
        distributeTerritories();

        pickFirstPlayer();

        gameMap = new GameMap(this);
        gameMap.setPreferredSize(new Dimension((int) (WIDTH*0.75), (int) (HEIGHT*0.9)));

        gameWindow = new GameWindow(this);
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

    public GameOption getGameOption() {
        return gameOption;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *
     * @return probability of a battle between GameMap src and dst territories
     * @throws SrcNotStatedException
     * @throws DstNotStatedException
     * @throws WrongTerritoriesPairException
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
     *
     * @return true if attacker wins, else - false
     * @throws SrcNotStatedException
     * @throws DstNotStatedException
     * @throws WrongTerritoriesPairException
     * @throws IllegalNumberOfAttackTroopsException
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

        if(attackerWins(troopsLeft)) {
            srcTerritory.setTroops(1);
            dstTerritory.setTroops(troopsLeft[0]);
            dstTerritory.setOwner(srcTerritory.getOwner());
            gameMap.drawField();
            return true;
        }
        else {
            srcTerritory.setTroops(1);
            dstTerritory.setTroops(troopsLeft[1]);
            gameMap.drawField();
            return false;
        }
    }

    /**
     *
     * @param numberOfTroops
     * @throws SrcNotStatedException
     * @throws DstNotStatedException
     * @throws WrongTerritoriesPairException
     * @throws IllegalNumberOfFortifyTroopsException
     */
    public void fortify(int numberOfTroops) throws SrcNotStatedException, DstNotStatedException, WrongTerritoriesPairException, IllegalNumberOfFortifyTroopsException {
        Territory srcTerritory = gameMap.getSrcTerritory();
        Territory dstTerritory = gameMap.getDstTerritory();

        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory was not stated!");

        if(dstTerritory == null)
            throw new DstNotStatedException("Destination territory was not stated!");


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
        nextPlayerTurn(); // Only one fortification can be made
    }

    /**
     * for GUI
     * @return maximum amount of troops that are transferable
     */
    public int getMaximumFortificationTroops() {
        Territory srcTerritory = gameMap.getSrcTerritory();
        return srcTerritory.getTroops()-1;
    }

    /**
     *
     * @param numberOfTroops
     * @throws SrcNotStatedException
     * @throws IllegalNumberOfReinforceTroopsException
     */
    public void reinforce(int numberOfTroops) throws SrcNotStatedException, IllegalNumberOfReinforceTroopsException {
        Territory srcTerritory = gameMap.getSrcTerritory();
        if(srcTerritory == null)
            throw new SrcNotStatedException("Source territory was not stated!");

        if(numberOfTroops > currentPlayer.getBonus())
            throw new IllegalNumberOfReinforceTroopsException("Number of troops for reinforcement exceeds bonus!");

        srcTerritory.setTroops(srcTerritory.getTroops() + numberOfTroops);
        currentPlayer.setBonus(currentPlayer.getBonus() - numberOfTroops);
        gameMap.drawField();
    }

    /**
     * must be applied after nextPlayerTurn() call
     */
    public void reinforcePhase() {
        gameOption = GameOption.REINFORCEMENT;
    }

    /**
     * must be applied after reinforcePhase() call
     */
    public void attackPhase() {
        gameOption = GameOption.ATTACK;
    }

    /**
     * must be applied after attackPhase() call
     */
    public void fortifyPhase() {
        gameOption = GameOption.FORTIFY;
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
        currentPlayer.setBonus(gameGraph.getTerritories(currentPlayer).size() / 3);
    }



    private void initializeTerritories() {
        territories = new ArrayList<>();
        for(int i = 0; i < numberOfTerritories; i++) {
            territories.add(new Territory(namesOfTerritories[i], coordinates[i]));
        }
    }

    private void createGraphFromTerritories() {
        gameGraph.addEdge(findTerritory("Afghanistan"), findTerritory("China"));
        gameGraph.addEdge(findTerritory("Afghanistan"), findTerritory("India"));
        gameGraph.addEdge(findTerritory("Afghanistan"), findTerritory("Middle East"));
        gameGraph.addEdge(findTerritory("Afghanistan"), findTerritory("Ukraine"));
        gameGraph.addEdge(findTerritory("Afghanistan"), findTerritory("Ural"));
        gameGraph.addEdge(findTerritory("Alaska"), findTerritory("Alberta"));
        gameGraph.addEdge(findTerritory("Alaska"), findTerritory("Kamchatka"));
        gameGraph.addEdge(findTerritory("Alaska"), findTerritory("Northwest Territory"));
        gameGraph.addEdge(findTerritory("Alberta"), findTerritory("Northwest Territory"));
        gameGraph.addEdge(findTerritory("Alberta"), findTerritory("Ontario"));
        gameGraph.addEdge(findTerritory("Alberta"), findTerritory("Western United States"));
        gameGraph.addEdge(findTerritory("Argentina"), findTerritory("Brazil"));
        gameGraph.addEdge(findTerritory("Argentina"), findTerritory("Peru"));
        gameGraph.addEdge(findTerritory("Brazil"), findTerritory("North Africa"));
        gameGraph.addEdge(findTerritory("Brazil"), findTerritory("Peru"));
        gameGraph.addEdge(findTerritory("Brazil"), findTerritory("Venezuela"));
        gameGraph.addEdge(findTerritory("Central America"), findTerritory("Eastern United States"));
        gameGraph.addEdge(findTerritory("Central America"), findTerritory("Venezuela"));
        gameGraph.addEdge(findTerritory("Central America"), findTerritory("Western United States"));
        gameGraph.addEdge(findTerritory("China"), findTerritory("India"));
        gameGraph.addEdge(findTerritory("China"), findTerritory("Mongolia"));
        gameGraph.addEdge(findTerritory("China"), findTerritory("Siam"));
        gameGraph.addEdge(findTerritory("China"), findTerritory("Siberia"));
        gameGraph.addEdge(findTerritory("China"), findTerritory("Ural"));
        gameGraph.addEdge(findTerritory("Congo"), findTerritory("East Africa"));
        gameGraph.addEdge(findTerritory("Congo"), findTerritory("North Africa"));
        gameGraph.addEdge(findTerritory("Congo"), findTerritory("South Africa"));
        gameGraph.addEdge(findTerritory("East Africa"), findTerritory("Egypt"));
        gameGraph.addEdge(findTerritory("East Africa"), findTerritory("Madagascar"));
        gameGraph.addEdge(findTerritory("East Africa"), findTerritory("Middle East"));
        gameGraph.addEdge(findTerritory("East Africa"), findTerritory("North Africa"));
        gameGraph.addEdge(findTerritory("East Africa"), findTerritory("South Africa"));
        gameGraph.addEdge(findTerritory("Eastern Australia"), findTerritory("New Guinea"));
        gameGraph.addEdge(findTerritory("Eastern Australia"), findTerritory("Western Australia"));
        gameGraph.addEdge(findTerritory("Eastern United States"), findTerritory("Ontario"));
        gameGraph.addEdge(findTerritory("Eastern United States"), findTerritory("Quebec"));
        gameGraph.addEdge(findTerritory("Eastern United States"), findTerritory("Western United States"));
        gameGraph.addEdge(findTerritory("Egypt"), findTerritory("Middle East"));
        gameGraph.addEdge(findTerritory("Egypt"), findTerritory("North Africa"));
        gameGraph.addEdge(findTerritory("Egypt"), findTerritory("Southern Europe"));
        gameGraph.addEdge(findTerritory("Great Britain"), findTerritory("Iceland"));
        gameGraph.addEdge(findTerritory("Great Britain"), findTerritory("Northern Europe"));
        gameGraph.addEdge(findTerritory("Great Britain"), findTerritory("Scandinavia"));
        gameGraph.addEdge(findTerritory("Great Britain"), findTerritory("Western Europe"));
        gameGraph.addEdge(findTerritory("Greenland"), findTerritory("Iceland"));
        gameGraph.addEdge(findTerritory("Greenland"), findTerritory("Northwest Territory"));
        gameGraph.addEdge(findTerritory("Greenland"), findTerritory("Ontario"));
        gameGraph.addEdge(findTerritory("Greenland"), findTerritory("Quebec"));
        gameGraph.addEdge(findTerritory("Iceland"), findTerritory("Scandinavia"));
        gameGraph.addEdge(findTerritory("India"), findTerritory("Middle East"));
        gameGraph.addEdge(findTerritory("India"), findTerritory("Siam"));
        gameGraph.addEdge(findTerritory("Indonesia"), findTerritory("New Guinea"));
        gameGraph.addEdge(findTerritory("Indonesia"), findTerritory("Siam"));
        gameGraph.addEdge(findTerritory("Indonesia"), findTerritory("Western Australia"));
        gameGraph.addEdge(findTerritory("Irkutsk"), findTerritory("Kamchatka"));
        gameGraph.addEdge(findTerritory("Irkutsk"), findTerritory("Mongolia"));
        gameGraph.addEdge(findTerritory("Irkutsk"), findTerritory("Siberia"));
        gameGraph.addEdge(findTerritory("Irkutsk"), findTerritory("Yakutsk"));
        gameGraph.addEdge(findTerritory("Japan"), findTerritory("Kamchatka"));
        gameGraph.addEdge(findTerritory("Japan"), findTerritory("Mongolia"));
        gameGraph.addEdge(findTerritory("Kamchatka"), findTerritory("Mongolia"));
        gameGraph.addEdge(findTerritory("Kamchatka"), findTerritory("Yakutsk"));
        gameGraph.addEdge(findTerritory("Madagascar"), findTerritory("South Africa"));
        gameGraph.addEdge(findTerritory("Middle East"), findTerritory("Southern Europe"));
        gameGraph.addEdge(findTerritory("Middle East"), findTerritory("Ukraine"));
        gameGraph.addEdge(findTerritory("Mongolia"), findTerritory("Siberia"));
        gameGraph.addEdge(findTerritory("New Guinea"), findTerritory("Western Australia"));
        gameGraph.addEdge(findTerritory("North Africa"), findTerritory("Southern Europe"));
        gameGraph.addEdge(findTerritory("North Africa"), findTerritory("Western Europe"));
        gameGraph.addEdge(findTerritory("Northern Europe"), findTerritory("Scandinavia"));
        gameGraph.addEdge(findTerritory("Northern Europe"), findTerritory("Southern Europe"));
        gameGraph.addEdge(findTerritory("Northern Europe"), findTerritory("Ukraine"));
        gameGraph.addEdge(findTerritory("Northern Europe"), findTerritory("Western Europe"));
        gameGraph.addEdge(findTerritory("Northwest Territory"), findTerritory("Ontario"));
        gameGraph.addEdge(findTerritory("Ontario"), findTerritory("Quebec"));
        gameGraph.addEdge(findTerritory("Ontario"), findTerritory("Western United States"));
        gameGraph.addEdge(findTerritory("Peru"), findTerritory("Venezuela"));
        gameGraph.addEdge(findTerritory("Scandinavia"), findTerritory("Ukraine"));
        gameGraph.addEdge(findTerritory("Siberia"), findTerritory("Ural"));
        gameGraph.addEdge(findTerritory("Siberia"), findTerritory("Yakutsk"));
        gameGraph.addEdge(findTerritory("Southern Europe"), findTerritory("Ukraine"));
        gameGraph.addEdge(findTerritory("Southern Europe"), findTerritory("Western Europe"));
        gameGraph.addEdge(findTerritory("Ukraine"), findTerritory("Ural"));
    }

    private Territory findTerritory(String name) {
        for(Territory territory : territories) {
            if(territory.getName().equals(name))
                return territory;
        }
        return null;
    }


    private void distributeTerritories() {
        int territoriesLeft = numberOfTerritories;
        for(int i = 0; i < numberOfPlayers; i++) {
            int playerTerritoriesNumber = numberOfTerritories / numberOfPlayers;
            int playerTroopsNumber;
            ArrayList<Territory> playerTerritories = new ArrayList<>();
            switch (numberOfPlayers) {
                case 2:
                    playerTroopsNumber = 40;
                    break;
                case 3:
                    playerTroopsNumber = 35;
                    break;
                case 4:
                    playerTroopsNumber = 30;
                    break;
                case 5:
                    playerTroopsNumber = 25;
                    break;
                case 6:
                    playerTroopsNumber = 20;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + numberOfPlayers);
            }

            if(i == numberOfPlayers-1) {
                playerTerritoriesNumber = territoriesLeft;
            }
            int troopsLeft = playerTroopsNumber - playerTerritoriesNumber;
            for(int j = 0; j < playerTerritoriesNumber; j++){
                Territory territory = getRandomTerritory(territories);
                territory.setOwner(players.get(i));
                territory.setTroops(1);
                playerTerritories.add(territory);
                territories.remove(territory);
            }

            while(troopsLeft != 0) {
                Territory territoryToIncrement = getRandomTerritory(playerTerritories);
                territoryToIncrement.setTroops(territoryToIncrement.getTroops()+1);
                troopsLeft--;
            }

            territoriesLeft -= playerTerritoriesNumber;
        }
    }

    private Territory getRandomTerritory(ArrayList<Territory> t) {
        Random rand = new Random();
        return t.get(rand.nextInt(t.size()));
    }

    private void removeDeadPlayers() {
        players.removeIf(this::playerIsDead);
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
        gameOption = GameOption.REINFORCEMENT;
        currentPlayer = players.get(0);
    }

}
