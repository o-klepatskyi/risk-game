package com.risk.logic.maps;

import com.risk.logic.Continent;
import com.risk.logic.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;

class GoTMap extends Map{
    GoTMap() {
        type = MapType.GOT;

        String[] namesOfTerritories = new String[] {
                // dorne
                "Sunspear",
                "Greenblood",
                "Sandstone",
                "Red Mountains",
                // the reach
                "The Arbor",
                "Three towers",
                "Oldtown",
                "Highgarden",
                "Searoad Marshis",
                "The Mander",
                "Blackwater Rush",
                // the stormlands
                "Dornish Marches",
                "Rainwood",
                "Storm's End",
                "Tarth",
                // the crownlands
                "Kingswood",
                "King's Landing",
                "Cracklaw Point",
                "Dragonstone",
                // the westerlands
                "Crakehall",
                "Silverhill",
                "Casterly Rock",
                "Golden Tooth",
                "The Crao",
                // the iron islands
                "Pyke",
                "Harlaw",
                // the riverlands
                "Stoney Sept",
                "Marrenhal",
                "Riverrun",
                "The Trident",
                "The Twins",
                // the vale of arryn
                "Mountains of the moon",
                "The Eyrie",
                "Gulltown",
                "The Fingers",
                // the north
                "Cape Kraken",
                "The Neck",
                "Stoney Shore",
                "Barrowlands",
                "White Harbor",
                "Widow's Watch",
                "Wolfswood",
                "Winterfell",
                "Bear Island",
                "The Dreadfort",
                "The Gift",
                "Karhold",
                "Skagos"
        };

        Coordinates[] coordinates = new Coordinates[] {
                // dorne
                new Coordinates(63, 496),
                new Coordinates(60, 386),
                new Coordinates(54, 273),
                new Coordinates(132, 292),
                // the reach
                new Coordinates(31, 98),
                new Coordinates(79, 154),
                new Coordinates(139, 137),
                new Coordinates(177, 220),
                new Coordinates(256, 192),
                new Coordinates(233, 322),
                new Coordinates(299, 297),
                // the stormlands
                new Coordinates(187, 367),
                new Coordinates(164, 465),
                new Coordinates(239, 463),
                new Coordinates(229, 533),
                // the crownlands
                new Coordinates(263, 416),
                new Coordinates(339, 395),
                new Coordinates(377, 455),
                new Coordinates(343, 498),
                // the westerlands
                new Coordinates(285, 142),
                new Coordinates(313, 224),
                new Coordinates(352, 176),
                new Coordinates(383, 224),
                new Coordinates(419, 187),
                // the iron islands
                new Coordinates(478, 120),
                new Coordinates(478, 167),
                // the riverlands
                new Coordinates(347, 268),
                new Coordinates(382, 353),
                new Coordinates(419, 283),
                new Coordinates(466, 317),
                new Coordinates(520, 275),
                // the vale of arryn
                new Coordinates(494, 375),
                new Coordinates(465, 448),
                new Coordinates(447, 520),
                new Coordinates(555, 456),
                // the north
                new Coordinates(572, 164),
                new Coordinates(579, 270),
                new Coordinates(707, 124),
                new Coordinates(683, 233),
                new Coordinates(652, 381),
                new Coordinates(715, 432),
                new Coordinates(767, 240),
                new Coordinates(777, 334),
                new Coordinates(855, 207),
                new Coordinates(779, 413),
                new Coordinates(887, 386),
                new Coordinates(816, 474),
                new Coordinates(907, 500)
        };

        initializeTerritories(namesOfTerritories, coordinates);
        createGraphFromTerritories();
        initContinents();
    }


    @Override
    protected void createGraphFromTerritories() {
        gameGraph.addEdge(findTerritory("Sunspear"), findTerritory("Greenblood"));
        gameGraph.addEdge(findTerritory("Greenblood"), findTerritory("Sandstone"));
        gameGraph.addEdge(findTerritory("Greenblood"), findTerritory("Red Mountains"));
        gameGraph.addEdge(findTerritory("Sandstone"), findTerritory("Red Mountains"));
        gameGraph.addEdge(findTerritory("Red Mountains"), findTerritory("Three towers"));
        gameGraph.addEdge(findTerritory("Red Mountains"), findTerritory("Highgarden"));
        gameGraph.addEdge(findTerritory("The Arbor"), findTerritory("Three towers"));
        gameGraph.addEdge(findTerritory("The Arbor"), findTerritory("Oldtown"));
        gameGraph.addEdge(findTerritory("Oldtown"), findTerritory("Highgarden"));
        gameGraph.addEdge(findTerritory("Oldtown"), findTerritory("Three towers"));
        gameGraph.addEdge(findTerritory("Highgarden"), findTerritory("Three towers"));
        gameGraph.addEdge(findTerritory("Highgarden"), findTerritory("Searoad Marshis"));
        gameGraph.addEdge(findTerritory("Highgarden"), findTerritory("Blackwater Rush"));
        gameGraph.addEdge(findTerritory("Highgarden"), findTerritory("The Mander"));
        gameGraph.addEdge(findTerritory("Searoad Marshis"), findTerritory("Blackwater Rush"));
        gameGraph.addEdge(findTerritory("The Mander"), findTerritory("Blackwater Rush"));
        gameGraph.addEdge(findTerritory( "Dornish Marches"), findTerritory("Red Mountains"));
        gameGraph.addEdge(findTerritory( "Dornish Marches"), findTerritory("Highgarden"));
        gameGraph.addEdge(findTerritory( "Dornish Marches"), findTerritory("The Mander"));
        gameGraph.addEdge(findTerritory( "Dornish Marches"), findTerritory("Storm's End"));
        gameGraph.addEdge(findTerritory( "Dornish Marches"), findTerritory("Rainwood"));
        gameGraph.addEdge(findTerritory( "Rainwood"), findTerritory("Storm's End"));
        gameGraph.addEdge(findTerritory( "Rainwood"), findTerritory("Tarth"));
        gameGraph.addEdge(findTerritory( "Storm's End"), findTerritory("Tarth"));
        gameGraph.addEdge(findTerritory( "Kingswood"), findTerritory("Storm's End"));
        gameGraph.addEdge(findTerritory( "Kingswood"), findTerritory("The Mander"));
        gameGraph.addEdge(findTerritory( "Kingswood"), findTerritory("Blackwater Rush"));
        gameGraph.addEdge(findTerritory( "Kingswood"), findTerritory("King's Landing"));
        gameGraph.addEdge(findTerritory( "Kingswood"), findTerritory("Dragonstone"));
        gameGraph.addEdge(findTerritory( "King's Landing"), findTerritory("Blackwater Rush"));
        gameGraph.addEdge(findTerritory( "King's Landing"), findTerritory("Cracklaw Point"));
        gameGraph.addEdge(findTerritory( "Dragonstone"), findTerritory("Cracklaw Point"));
        gameGraph.addEdge(findTerritory( "Crakehall"), findTerritory("Searoad Marshis"));
        gameGraph.addEdge(findTerritory( "Crakehall"), findTerritory("Silverhill"));
        gameGraph.addEdge(findTerritory( "Crakehall"), findTerritory("Casterly Rock"));
        gameGraph.addEdge(findTerritory( "Silverhill"), findTerritory("Searoad Marshis"));
        gameGraph.addEdge(findTerritory( "Silverhill"), findTerritory("Casterly Rock"));
        gameGraph.addEdge(findTerritory( "Silverhill"), findTerritory("Blackwater Rush"));
        gameGraph.addEdge(findTerritory( "Casterly Rock"), findTerritory("Golden Tooth"));
        gameGraph.addEdge(findTerritory( "Casterly Rock"), findTerritory("The Crao"));
        gameGraph.addEdge(findTerritory( "Golden Tooth"), findTerritory("The Crao"));
        gameGraph.addEdge(findTerritory( "Pyke"), findTerritory("The Crao"));
        gameGraph.addEdge(findTerritory( "Pyke"), findTerritory("Harlaw"));
        gameGraph.addEdge(findTerritory( "Stoney Sept"), findTerritory("Blackwater Rush"));
        gameGraph.addEdge(findTerritory( "Stoney Sept"), findTerritory("Silverhill"));
        gameGraph.addEdge(findTerritory( "Stoney Sept"), findTerritory("Casterly Rock"));
        gameGraph.addEdge(findTerritory( "Stoney Sept"), findTerritory("Golden Tooth"));
        gameGraph.addEdge(findTerritory( "Stoney Sept"), findTerritory("Riverrun"));
        gameGraph.addEdge(findTerritory( "Stoney Sept"), findTerritory("Marrenhal"));
        gameGraph.addEdge(findTerritory( "Riverrun"), findTerritory("Golden Tooth"));
        gameGraph.addEdge(findTerritory( "Riverrun"), findTerritory("The Crao"));
        gameGraph.addEdge(findTerritory( "Riverrun"), findTerritory("Pyke"));
        gameGraph.addEdge(findTerritory( "Riverrun"), findTerritory("The Trident"));
        gameGraph.addEdge(findTerritory( "Riverrun"), findTerritory("Marrenhal"));
        gameGraph.addEdge(findTerritory( "Marrenhal"), findTerritory("Blackwater Rush"));
        gameGraph.addEdge(findTerritory( "Marrenhal"), findTerritory("King's Landing"));
        gameGraph.addEdge(findTerritory( "Marrenhal"), findTerritory("The Trident"));
        gameGraph.addEdge(findTerritory( "The Trident"), findTerritory("The Twins"));
        gameGraph.addEdge(findTerritory( "The Twins"), findTerritory("Harlaw"));
        gameGraph.addEdge(findTerritory( "Mountains of the moon"), findTerritory("Marrenhal"));
        gameGraph.addEdge(findTerritory( "Mountains of the moon"), findTerritory("The Trident"));
        gameGraph.addEdge(findTerritory( "Mountains of the moon"), findTerritory("The Twins"));
        gameGraph.addEdge(findTerritory( "Mountains of the moon"), findTerritory("The Fingers"));
        gameGraph.addEdge(findTerritory( "Mountains of the moon"), findTerritory("The Eyrie"));
        gameGraph.addEdge(findTerritory( "The Eyrie"), findTerritory("Gulltown"));
        gameGraph.addEdge(findTerritory( "The Neck"), findTerritory("Cape Kraken"));
        gameGraph.addEdge(findTerritory( "The Neck"), findTerritory("Harlaw"));
        gameGraph.addEdge(findTerritory( "The Neck"), findTerritory("The Twins"));
        gameGraph.addEdge(findTerritory( "The Neck"), findTerritory("Mountains of the moon"));
        gameGraph.addEdge(findTerritory( "The Neck"), findTerritory("Barrowlands"));
        gameGraph.addEdge(findTerritory( "The Neck"), findTerritory("White Harbor"));
        gameGraph.addEdge(findTerritory( "Barrowlands"), findTerritory("Stoney Shore"));
        gameGraph.addEdge(findTerritory( "Barrowlands"), findTerritory("Wolfswood"));
        gameGraph.addEdge(findTerritory( "Barrowlands"), findTerritory("Winterfell"));
        gameGraph.addEdge(findTerritory( "Barrowlands"), findTerritory("White Harbor"));
        gameGraph.addEdge(findTerritory( "Stoney Shore"), findTerritory("Wolfswood"));
        gameGraph.addEdge(findTerritory( "Stoney Shore"), findTerritory("Bear Island"));
        gameGraph.addEdge(findTerritory( "White Harbor"), findTerritory("Winterfell"));
        gameGraph.addEdge(findTerritory( "White Harbor"), findTerritory("Widow's Watch"));
        gameGraph.addEdge(findTerritory( "Winterfell"), findTerritory("Widow's Watch"));
        gameGraph.addEdge(findTerritory( "Winterfell"), findTerritory("The Dreadfort"));
        gameGraph.addEdge(findTerritory( "Winterfell"), findTerritory("The Gift"));
        gameGraph.addEdge(findTerritory( "Winterfell"), findTerritory("Wolfswood"));
        gameGraph.addEdge(findTerritory( "Winterfell"), findTerritory("Bear Island"));
        gameGraph.addEdge(findTerritory( "Wolfswood"), findTerritory("Bear Island"));
        gameGraph.addEdge(findTerritory( "Widow's Watch"), findTerritory("The Dreadfort"));
        gameGraph.addEdge(findTerritory( "Karhold"), findTerritory("The Dreadfort"));
        gameGraph.addEdge(findTerritory( "Karhold"), findTerritory("The Gift"));
        gameGraph.addEdge(findTerritory( "Karhold"), findTerritory("Skagos"));
        gameGraph.addEdge(findTerritory( "Skagos"), findTerritory("The Gift"));
        gameGraph.addEdge(findTerritory( "The Gift"), findTerritory("The Dreadfort"));
    }

    @Override
    protected void initContinents() {
        dorne.addTerritory(gameGraph.getVertex("Sunspear"));
        dorne.addTerritory(gameGraph.getVertex("Greenblood"));
        dorne.addTerritory(gameGraph.getVertex("Sandstone"));
        dorne.addTerritory(gameGraph.getVertex("Red Mountains"));

        theReach.addTerritory(gameGraph.getVertex("The Arbor"));
        theReach.addTerritory(gameGraph.getVertex("Three towers"));
        theReach.addTerritory(gameGraph.getVertex("Oldtown"));
        theReach.addTerritory(gameGraph.getVertex("Highgarden"));
        theReach.addTerritory(gameGraph.getVertex("Searoad Marshis"));
        theReach.addTerritory(gameGraph.getVertex("The Mander"));
        theReach.addTerritory(gameGraph.getVertex("Blackwater Rush"));

        theStormlands.addTerritory(gameGraph.getVertex("Dornish Marches"));
        theStormlands.addTerritory(gameGraph.getVertex("Rainwood"));
        theStormlands.addTerritory(gameGraph.getVertex("Storm's End"));
        theStormlands.addTerritory(gameGraph.getVertex("Tarth"));

        theCrownlands.addTerritory(gameGraph.getVertex("Kingswood"));
        theCrownlands.addTerritory(gameGraph.getVertex("King's Landing"));
        theCrownlands.addTerritory(gameGraph.getVertex("Cracklaw Point"));
        theCrownlands.addTerritory(gameGraph.getVertex("Dragonstone"));


        theWesterlands.addTerritory(gameGraph.getVertex("Crakehall"));
        theWesterlands.addTerritory(gameGraph.getVertex("Silverhill"));
        theWesterlands.addTerritory(gameGraph.getVertex("Casterly Rock"));
        theWesterlands.addTerritory(gameGraph.getVertex("Golden Tooth"));
        theWesterlands.addTerritory(gameGraph.getVertex("The Crao"));

        theIronIslands.addTerritory(gameGraph.getVertex("Pyke"));
        theIronIslands.addTerritory(gameGraph.getVertex("Harlaw"));

        theRiverlands.addTerritory(gameGraph.getVertex("Stoney Sept"));
        theRiverlands.addTerritory(gameGraph.getVertex( "Marrenhal"));
        theRiverlands.addTerritory(gameGraph.getVertex("Riverrun"));
        theRiverlands.addTerritory(gameGraph.getVertex("The Trident"));
        theRiverlands.addTerritory(gameGraph.getVertex("The Twins"));

        theValeOfArryn.addTerritory(gameGraph.getVertex("Mountains of the moon"));
        theValeOfArryn.addTerritory(gameGraph.getVertex("The Eyrie"));
        theValeOfArryn.addTerritory(gameGraph.getVertex("Gulltown"));
        theValeOfArryn.addTerritory(gameGraph.getVertex("The Fingers"));

        theNorth.addTerritory(gameGraph.getVertex("Cape Kraken"));
        theNorth.addTerritory(gameGraph.getVertex( "The Neck"));
        theNorth.addTerritory(gameGraph.getVertex("Stoney Shore"));
        theNorth.addTerritory(gameGraph.getVertex("Barrowlands"));
        theNorth.addTerritory(gameGraph.getVertex("White Harbor"));
        theNorth.addTerritory(gameGraph.getVertex("Widow's Watch"));
        theNorth.addTerritory(gameGraph.getVertex("Wolfswood"));
        theNorth.addTerritory(gameGraph.getVertex("Winterfell"));
        theNorth.addTerritory(gameGraph.getVertex("Bear Island"));
        theNorth.addTerritory(gameGraph.getVertex("The Dreadfort"));
        theNorth.addTerritory(gameGraph.getVertex("The Gift"));
        theNorth.addTerritory(gameGraph.getVertex("Karhold"));
        theNorth.addTerritory(gameGraph.getVertex("Skagos"));

        continents = new ArrayList<>(Arrays.asList(dorne, theRiverlands,
                theReach, theStormlands, theCrownlands, theWesterlands, theIronIslands,
                theValeOfArryn, theNorth));
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

    private final Continent dorne = new Continent("Dorne", 2);
    private final Continent theReach = new Continent("The Reach", 4);
    private final Continent theStormlands = new Continent("The Stormlands", 1);
    private final Continent theCrownlands = new Continent("The Crownlands", 2);
    private final Continent theWesterlands = new Continent("The Westerlands", 2);
    private final Continent theIronIslands = new Continent("The Iron Islands", 1);
    private final Continent theRiverlands = new Continent("The Riverlands", 2);
    private final Continent theValeOfArryn = new Continent("The Vale of Arryn", 1);
    private final Continent theNorth = new Continent("The North", 5);
}
