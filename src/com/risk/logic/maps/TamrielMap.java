package com.risk.logic.maps;

import com.risk.logic.Continent;
import com.risk.logic.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;

public class TamrielMap extends Map {
    TamrielMap() {
        type = MapType.TAMRIEL;

        String[] namesOfTerritories = new String[] {
                // summerset isles
                "Lillandril",
                "Cloudrest",
                "Alinor",
                "Shimmerene",
                "Dusk",
                "Firsthold",
                "Skywatch",
                // high rock
                "Daggerfall",
                "Camlorn",
                "Wayrest",
                "Northpoint",
                "Farrun",
                "Evermor"
        };

        Coordinates[] coordinates = new Coordinates[] {
                // summerset isles
                new Coordinates(61, 495),
                new Coordinates(114, 496),
                new Coordinates(74, 561),
                new Coordinates(135, 546),
                new Coordinates(154, 581),
                new Coordinates(155, 457),
                new Coordinates(175, 519),
                // high rock
                new Coordinates(51, 266),
                new Coordinates(80, 211),
                new Coordinates(167, 187),
                new Coordinates(142, 126),
                new Coordinates(234, 122),
                new Coordinates(255, 160),
        };

        initializeTerritories(namesOfTerritories, coordinates);
        createGraphFromTerritories();
        initContinents();
    }


    @Override
    protected void createGraphFromTerritories() {
        gameGraph.addEdge(findTerritory("Lillandril"), findTerritory("Cloudrest"));
        gameGraph.addEdge(findTerritory("Cloudrest"), findTerritory("Alinor"));
        gameGraph.addEdge(findTerritory("Cloudrest"), findTerritory("Shimmerene"));
        gameGraph.addEdge(findTerritory("Cloudrest"), findTerritory("Firsthold"));
        gameGraph.addEdge(findTerritory("Cloudrest"), findTerritory("Skywatch"));
        gameGraph.addEdge(findTerritory("Alinor"), findTerritory("Shimmerene"));
        gameGraph.addEdge(findTerritory("Alinor"), findTerritory("Dusk"));
        gameGraph.addEdge(findTerritory("Shimmerene"), findTerritory("Dusk"));
        gameGraph.addEdge(findTerritory("Firsthold"), findTerritory("Skywatch"));

        gameGraph.addEdge(findTerritory("Daggerfall"), findTerritory("Camlorn"));
        gameGraph.addEdge(findTerritory("Daggerfall"), findTerritory("Lillandril"));
        gameGraph.addEdge(findTerritory("Camlorn"), findTerritory("Wayrest"));
        gameGraph.addEdge(findTerritory("Wayrest"), findTerritory("Northpoint"));
        gameGraph.addEdge(findTerritory("Wayrest"), findTerritory("Farrun"));
        gameGraph.addEdge(findTerritory("Wayrest"), findTerritory("Evermor"));
        gameGraph.addEdge(findTerritory("Farrun"), findTerritory("Evermor"));
    }

    @Override
    protected void initContinents() {
        summersetIsles.addTerritory(gameGraph.getVertex("Skywatch"));
        summersetIsles.addTerritory(gameGraph.getVertex("Firsthold"));
        summersetIsles.addTerritory(gameGraph.getVertex("Shimmerene"));
        summersetIsles.addTerritory(gameGraph.getVertex("Dusk"));
        summersetIsles.addTerritory(gameGraph.getVertex("Alinor"));
        summersetIsles.addTerritory(gameGraph.getVertex("Cloudrest"));
        summersetIsles.addTerritory(gameGraph.getVertex("Lillandril"));

        highRock.addTerritory(gameGraph.getVertex("Farrun"));
        highRock.addTerritory(gameGraph.getVertex("Evermor"));
        highRock.addTerritory(gameGraph.getVertex("Northpoint"));
        highRock.addTerritory(gameGraph.getVertex("Camlorn"));
        highRock.addTerritory(gameGraph.getVertex("Daggerfall"));
        highRock.addTerritory(gameGraph.getVertex("Wayrest"));

        continents = new ArrayList<>(Arrays.asList(summersetIsles, highRock));
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

    private final Continent summersetIsles = new Continent("Summerset Isles", 3);
    private final Continent highRock = new Continent("High rock", 2);
}
