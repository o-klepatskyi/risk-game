package logic.maps;

import logic.Continent;
import logic.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;

public class TestMap extends Map{
    public TestMap() {
        type = MapType.TEST;

        String[] namesOfTerritories = new String[] {
                "Top left",
                "Top center",
                "Top right",
                "Middle left",
                "Middle center",
                "Middle right",
                "Bottom left",
                "Bottom center",
                "Bottom right"
        };

        Coordinates[] coordinates = new Coordinates[] {
                new Coordinates(200,200),
                new Coordinates(350,200),
                new Coordinates(500,200),
                new Coordinates(200,350),
                new Coordinates(350,350),
                new Coordinates(500,350),
                new Coordinates(200,500),
                new Coordinates(350,500),
                new Coordinates(500,500),
        };

        initializeTerritories(namesOfTerritories, coordinates);
        createGraphFromTerritories();
        initContinents();
    }


    @Override
    protected void createGraphFromTerritories() {
        gameGraph.addEdge(findTerritory("Top left"), findTerritory("Top center"));
        gameGraph.addEdge(findTerritory("Top left"), findTerritory("Middle left"));
        gameGraph.addEdge(findTerritory("Middle center"), findTerritory("Middle left"));
        gameGraph.addEdge(findTerritory("Middle center"), findTerritory("Top center"));
        gameGraph.addEdge(findTerritory("Middle center"), findTerritory("Bottom center"));
        gameGraph.addEdge(findTerritory("Middle center"), findTerritory("Middle right"));
        gameGraph.addEdge(findTerritory("Top center"), findTerritory("Top right"));
        gameGraph.addEdge(findTerritory("Top right"), findTerritory("Middle right"));
        gameGraph.addEdge(findTerritory("Bottom right"), findTerritory("Middle right"));
        gameGraph.addEdge(findTerritory("Bottom center"), findTerritory("Bottom right"));
        gameGraph.addEdge(findTerritory("Bottom left"), findTerritory("Bottom center"));
        gameGraph.addEdge(findTerritory("Bottom left"), findTerritory("Middle left"));
    }

    @Override
    protected void initContinents() {
        topLeft.addTerritory(gameGraph.getVertex("Middle left"));
        topLeft.addTerritory(gameGraph.getVertex("Top left"));
        topLeft.addTerritory(gameGraph.getVertex("Top center"));

        bottomRight.addTerritory(gameGraph.getVertex("Bottom center"));
        bottomRight.addTerritory(gameGraph.getVertex("Bottom right"));
        bottomRight.addTerritory(gameGraph.getVertex("Middle right"));

        continents = new ArrayList<>(Arrays.asList(topLeft, bottomRight));
    }

    @Override
    protected int getInitialPlayerTroopsNumber(int numberOfPlayers) {
        switch (numberOfPlayers) {
            case 2:
                return 9;
            case 3:
                return 7;
            case 4:
                return 5;
            case 5:
                return 3;
            case 6:
                return 2;
            default:
                throw new IllegalStateException("Unexpected value: " + numberOfPlayers);
        }
    }

    private Continent topLeft = new Continent("Top left", 3);
    private Continent bottomRight = new Continent("Bottom Right", 3);

}
