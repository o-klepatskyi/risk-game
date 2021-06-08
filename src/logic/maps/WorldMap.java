package logic.maps;

import logic.Continent;
import logic.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;

public class WorldMap extends Map {

    public WorldMap() {
        type = MapType.WORLD_MAP;

        String[] namesOfTerritories = new String[] {
                "Afghanistan", "Alaska", "Alberta", "Argentina", "Brazil", "Central America",
                "China", "Congo", "East Africa", "Eastern Australia", "Eastern United States", "Egypt",
                "Great Britain", "Greenland", "Iceland", "India", "Indonesia", "Irkutsk",
                "Japan", "Kamchatka", "Madagascar", "Middle East", "Mongolia", "New Guinea",
                "North Africa", "Northern Europe", "Northwest Territory", "Ontario", "Peru", "Quebec",
                "Scandinavia", "Siam", "Siberia", "South Africa", "Southern Europe", "Ukraine",
                "Ural", "Venezuela", "Western Australia", "Western Europe", "Western United States", "Yakutsk"
        };

        Coordinates[] coordinates = new Coordinates[] {
                new Coordinates(640, 240), new Coordinates(60, 105), new Coordinates(150, 155), new Coordinates(245, 510), new Coordinates(300, 430), new Coordinates(165, 315),
                new Coordinates(750, 290), new Coordinates(515, 480), new Coordinates(550, 430), new Coordinates(880, 535), new Coordinates(220, 250), new Coordinates(515, 370),
                new Coordinates(385, 210), new Coordinates(330, 70), new Coordinates(405, 135), new Coordinates(680, 330), new Coordinates(780, 475), new Coordinates(750, 175),
                new Coordinates(870, 235), new Coordinates(850, 90), new Coordinates(600, 570), new Coordinates(580, 345), new Coordinates(770, 230), new Coordinates(865, 450),
                new Coordinates(440, 400), new Coordinates(475, 220), new Coordinates(150, 100), new Coordinates(210, 165), new Coordinates(220, 445), new Coordinates(275, 165),
                new Coordinates(475, 140), new Coordinates(765, 365), new Coordinates(700, 115), new Coordinates(520, 570), new Coordinates(480, 275), new Coordinates(550, 195),
                new Coordinates(650, 150), new Coordinates(220, 365), new Coordinates(820, 565), new Coordinates(400, 305), new Coordinates(145, 225), new Coordinates(770, 85)
        };

        initializeTerritories(namesOfTerritories, coordinates);
        createGraphFromTerritories();
        initContinents();
    }

    @Override
    protected void createGraphFromTerritories() {
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

    @Override
    protected void initContinents() {
        southAmerica.addTerritory(gameGraph.getVertex("Brazil"));
        southAmerica.addTerritory(gameGraph.getVertex("Peru"));
        southAmerica.addTerritory(gameGraph.getVertex("Argentina"));
        southAmerica.addTerritory(gameGraph.getVertex("Venezuela"));

        northAmerica.addTerritory(gameGraph.getVertex("Central America"));
        northAmerica.addTerritory(gameGraph.getVertex("Eastern United States"));
        northAmerica.addTerritory(gameGraph.getVertex("Western United States"));
        northAmerica.addTerritory(gameGraph.getVertex("Alberta"));
        northAmerica.addTerritory(gameGraph.getVertex("Ontario"));
        northAmerica.addTerritory(gameGraph.getVertex("Quebec"));
        northAmerica.addTerritory(gameGraph.getVertex("Ontario"));
        northAmerica.addTerritory(gameGraph.getVertex("Greenland"));
        northAmerica.addTerritory(gameGraph.getVertex("Alaska"));
        northAmerica.addTerritory(gameGraph.getVertex("Northwest Territory"));

        europe.addTerritory(gameGraph.getVertex("Western Europe"));
        europe.addTerritory(gameGraph.getVertex("Southern Europe"));
        europe.addTerritory(gameGraph.getVertex("Northern Europe"));
        europe.addTerritory(gameGraph.getVertex("Great Britain"));
        europe.addTerritory(gameGraph.getVertex("Scandinavia"));
        europe.addTerritory(gameGraph.getVertex("Ukraine"));
        europe.addTerritory(gameGraph.getVertex("Iceland"));

        africa.addTerritory(gameGraph.getVertex("North Africa"));
        africa.addTerritory(gameGraph.getVertex("Egypt"));
        africa.addTerritory(gameGraph.getVertex("East Africa"));
        africa.addTerritory(gameGraph.getVertex("Congo"));
        africa.addTerritory(gameGraph.getVertex("South Africa"));
        africa.addTerritory(gameGraph.getVertex("Madagascar"));

        asia.addTerritory(gameGraph.getVertex("Middle East"));
        asia.addTerritory(gameGraph.getVertex("Afghanistan"));
        asia.addTerritory(gameGraph.getVertex("Ural"));
        asia.addTerritory(gameGraph.getVertex("Siberia"));
        asia.addTerritory(gameGraph.getVertex("India"));
        asia.addTerritory(gameGraph.getVertex("China"));
        asia.addTerritory(gameGraph.getVertex("Siam"));
        asia.addTerritory(gameGraph.getVertex("Mongolia"));
        asia.addTerritory(gameGraph.getVertex("Irkutsk"));
        asia.addTerritory(gameGraph.getVertex("Yakutsk"));
        asia.addTerritory(gameGraph.getVertex("Kamchatka"));
        asia.addTerritory(gameGraph.getVertex("Japan"));

        australia.addTerritory(gameGraph.getVertex("Western Australia"));
        australia.addTerritory(gameGraph.getVertex("Eastern Australia"));
        australia.addTerritory(gameGraph.getVertex("New Guinea"));
        australia.addTerritory(gameGraph.getVertex("Indonesia"));

        continents = new ArrayList<>(Arrays.asList(southAmerica,northAmerica,europe,australia,asia,africa));
    }
    @Override
    protected int getInitialPlayerTroopsNumber(int numberOfPlayers) {
        switch (numberOfPlayers) {
            case 2:
                return 40;
            case 3:
                return 35;
            case 4:
                return 30;
            case 5:
                return 25;
            case 6:
                return 20;
            default:
                throw new IllegalStateException("Unexpected value: " + numberOfPlayers);
        }
    }

    private final Continent southAmerica = new Continent("South America", 2);
    private final Continent northAmerica = new Continent("North America", 5);
    private final Continent europe = new Continent("Europe", 5);
    private final Continent australia = new Continent("Australia", 2);
    private final Continent asia = new Continent("Asia", 7);
    private final Continent africa = new Continent("Africa", 3);
}
