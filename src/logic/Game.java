package logic;

import gui.gameWindow.GameWindow;

import java.util.*;
import java.util.stream.IntStream;

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

    private final Random rand = new Random();

    public Game(ArrayList<Player> players) {
        this.players = players;
        numberOfPlayers = players.size();

        initializeTerritories();
        gameGraph = new Graph();
        createGraphFromTerritories();

        distributeTerritories();
        gameWindow = new GameWindow(this);
        System.out.println(calculate_probability(150, 138));
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public Graph getGameGraph() {
        return gameGraph;
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
            int playerTeritorriesNumber = numberOfTerritories / numberOfPlayers;
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
                playerTeritorriesNumber = territoriesLeft;
            }
            int troopsLeft = playerTroopsNumber - playerTeritorriesNumber;
            for(int j = 0; j < playerTeritorriesNumber; j++){
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

            territoriesLeft -= playerTeritorriesNumber;
        }
    }

    private Territory getRandomTerritory(ArrayList<Territory> t) {
        return t.get(rand.nextInt(t.size()));
    }

    private int[] dice_rolls(int troops_one, int troops_two) {
        troops_one--;
        while (troops_one > 0 && troops_two > 0) {
            int[] attack_cast = attack_dice(troops_one);
            int[] defend_cast = defend_dice(troops_two);
            int[] total_troops_lost = compare_casts(attack_cast, defend_cast);
            troops_one -= total_troops_lost[0];
            troops_two -= total_troops_lost[1];
        }
        return new int[]{troops_one, troops_two};
    }

    private int[] attack_dice(int units) {
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

    private int[] defend_dice(int units) {
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

    private int[] compare_casts(int[] attack_cast, int[] defend_cast) {
        int[] total_lost = {0, 0};
        for(int i = 0; i < Math.min(attack_cast.length, defend_cast.length); i++) {
            if(attack_cast[i] > defend_cast[i]) {
                total_lost[1]++;
            }
            else {
                total_lost[0]++;
            }
        }
        return total_lost;
    }

    private int[] reversed(int[] array) {
        return IntStream.range(0, array.length).map(i -> array[array.length-i-1]).toArray();
    }

    private boolean attackerWins(int[] troopsLeft) {
        return troopsLeft[0] > 0;
    }

    private int calculate_probability(int troops_one, int troops_two) {
        int attackerWinsCount = 0;
        final double COUNT_OF_DICE_ROLLS = 10000;
        for(int i = 0; i < COUNT_OF_DICE_ROLLS; i++) {
            if(attackerWins(dice_rolls(troops_one, troops_two)))
                attackerWinsCount++;
        }
        double probability = attackerWinsCount / COUNT_OF_DICE_ROLLS;
        probability *= 100;
        return (int) Math.round(probability);
    }
}
