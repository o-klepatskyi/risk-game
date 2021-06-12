package com.risk.logic.maps;

import com.risk.logic.Continent;
import com.risk.logic.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;

class USAMap extends Map {

    USAMap() {
        type = MapType.USA;

        String[] namesOfTerritories = new String[] {
                // the west
                "Washington",
                "Oregon",
                "Idaho",
                "Nevada",
                "California",
                // four corners
                "Utah",
                "Arizona",
                "Colorado",
                "New Mexico",
                // upper midwest
                "Montana",
                "Wyoming",
                "North Dakota",
                "South Dakota",
                "Nebraska",
                "Minnesota",
                "Iowa",
                // sunbelt
                "Kansas",
                "Oklahoma",
                "Texas",
                "Missouri",
                "Arkansas",
                "Louisiana",
                // great lakes
                "Wisconsin",
                "Illinois",
                "Michigan",
                "Indiana",
                "Ohio",
                // dixieland
                "Kentucky",
                "Virginia",
                "Tennessee",
                "North Carolina",
                "Mississippi",
                "Alabama",
                "Georgia",
                "South Carolina",
                "Florida",
                // north east
                "New York",
                "Pennsylvania",
                "West Virginia",
                "Maryland",
                // new england
                "Maine",
                "Vermont",
                "Massachusetts"
        };

        Coordinates[] coordinates = new Coordinates[] {
                // the west
                new Coordinates(120,55),
                new Coordinates(100,145),
                new Coordinates(190,180),
                new Coordinates(145,265),
                new Coordinates(72,325),
                // four corners
                new Coordinates(228,297),
                new Coordinates(215,423),
                new Coordinates(327,321),
                new Coordinates(309,433),
                // upper midwest
                new Coordinates(291,110),
                new Coordinates(300,210),
                new Coordinates(420,112),
                new Coordinates(420,186),
                new Coordinates(423,252),
                new Coordinates(503,136),
                new Coordinates(524,241),
                // sunbelt
                new Coordinates(444,336),
                new Coordinates(467,408),
                new Coordinates(435,517),
                new Coordinates(545,341),
                new Coordinates(551,424),
                new Coordinates(571,540),
                // great lakes
                new Coordinates(579,174),
                new Coordinates(590,280),
                new Coordinates(667,191),
                new Coordinates(645,280),
                new Coordinates(703,264),
                // dixieland
                new Coordinates(678, 342),
                new Coordinates(787, 318),
                new Coordinates(659, 382),
                new Coordinates(810, 368),
                new Coordinates(595, 465),
                new Coordinates(657, 463),
                new Coordinates(726, 462),
                new Coordinates(779, 428),
                new Coordinates(770, 576),
                // north east
                new Coordinates(806, 174),
                new Coordinates(782, 232),
                new Coordinates(734, 307),
                new Coordinates(832, 268),
                // new england
                new Coordinates(907, 84),
                new Coordinates(845, 119),
                new Coordinates(884, 175)

        };

        initializeTerritories(namesOfTerritories, coordinates);
        createGraphFromTerritories();
        initContinents();
    }


    @Override
    protected void createGraphFromTerritories() {
        gameGraph.addEdge(findTerritory("Washington"), findTerritory("Oregon"));
        gameGraph.addEdge(findTerritory("Idaho"), findTerritory("Oregon"));
        gameGraph.addEdge(findTerritory("Idaho"), findTerritory("Nevada"));
        gameGraph.addEdge(findTerritory("Arizona"), findTerritory("Nevada"));
        gameGraph.addEdge(findTerritory("Idaho"), findTerritory("Utah"));
        gameGraph.addEdge(findTerritory("Arizona"), findTerritory("Utah"));
        gameGraph.addEdge(findTerritory("Arizona"), findTerritory("New Mexico"));
        gameGraph.addEdge(findTerritory("Utah"), findTerritory("Nevada"));
        gameGraph.addEdge(findTerritory("Utah"), findTerritory("Colorado"));
        gameGraph.addEdge(findTerritory("New Mexico"), findTerritory("Colorado"));
        gameGraph.addEdge(findTerritory("California"), findTerritory("Oregon"));
        gameGraph.addEdge(findTerritory("Nevada"), findTerritory("Oregon"));
        gameGraph.addEdge(findTerritory("Nevada"), findTerritory("California"));
        gameGraph.addEdge(findTerritory("Washington"), findTerritory("Idaho"));
        gameGraph.addEdge(findTerritory("Montana"), findTerritory("Wyoming"));
        gameGraph.addEdge(findTerritory("Montana"), findTerritory("North Dakota"));
        gameGraph.addEdge(findTerritory("Montana"), findTerritory("South Dakota"));
        gameGraph.addEdge(findTerritory("Wyoming"), findTerritory("Idaho"));
        gameGraph.addEdge(findTerritory("Wyoming"), findTerritory("Utah"));
        gameGraph.addEdge(findTerritory("Wyoming"), findTerritory("Colorado"));
        gameGraph.addEdge(findTerritory("Wyoming"), findTerritory("Nebraska"));
        gameGraph.addEdge(findTerritory("Wyoming"), findTerritory("South Dakota"));
        gameGraph.addEdge(findTerritory("North Dakota"), findTerritory("South Dakota"));
        gameGraph.addEdge(findTerritory("North Dakota"), findTerritory("Minnesota"));
        gameGraph.addEdge(findTerritory("South Dakota"), findTerritory("Nebraska"));
        gameGraph.addEdge(findTerritory("South Dakota"), findTerritory("Iowa"));
        gameGraph.addEdge(findTerritory("South Dakota"), findTerritory("Minnesota"));
        gameGraph.addEdge(findTerritory("Nebraska"), findTerritory("Colorado"));
        gameGraph.addEdge(findTerritory("Nebraska"), findTerritory("Iowa"));
        gameGraph.addEdge(findTerritory("Minnesota"), findTerritory("Iowa"));
        gameGraph.addEdge(findTerritory("Kansas"), findTerritory("Nebraska"));
        gameGraph.addEdge(findTerritory("Kansas"), findTerritory("Colorado"));
        gameGraph.addEdge(findTerritory("Kansas"), findTerritory("Oklahoma"));
        gameGraph.addEdge(findTerritory("Oklahoma"), findTerritory("New Mexico"));
        gameGraph.addEdge(findTerritory("Oklahoma"), findTerritory("Texas"));
        gameGraph.addEdge(findTerritory("Oklahoma"), findTerritory("Arkansas"));
        gameGraph.addEdge(findTerritory("Texas"), findTerritory("New Mexico"));
        gameGraph.addEdge(findTerritory("Texas"), findTerritory("Louisiana"));
        gameGraph.addEdge(findTerritory("Louisiana"), findTerritory("Arkansas"));
        gameGraph.addEdge(findTerritory("Missouri"), findTerritory("Arkansas"));
        gameGraph.addEdge(findTerritory("Missouri"), findTerritory("Kansas"));
        gameGraph.addEdge(findTerritory("Missouri"), findTerritory("Iowa"));
        gameGraph.addEdge(findTerritory("Missouri"), findTerritory("Nebraska"));
        gameGraph.addEdge(findTerritory("Missouri"), findTerritory("Oklahoma"));
        gameGraph.addEdge(findTerritory("Wisconsin"), findTerritory("Minnesota"));
        gameGraph.addEdge(findTerritory("Wisconsin"), findTerritory("Illinois"));
        gameGraph.addEdge(findTerritory("Wisconsin"), findTerritory("Michigan"));
        gameGraph.addEdge(findTerritory("Illinois"), findTerritory("Missouri"));
        gameGraph.addEdge(findTerritory("Illinois"), findTerritory("Indiana"));
        gameGraph.addEdge(findTerritory("Michigan"), findTerritory("Indiana"));
        gameGraph.addEdge(findTerritory("Michigan"), findTerritory("Ohio"));
        gameGraph.addEdge(findTerritory("Indiana"), findTerritory("Ohio"));
        gameGraph.addEdge(findTerritory("Kentucky"), findTerritory("Illinois"));
        gameGraph.addEdge(findTerritory("Kentucky"), findTerritory("Tennessee"));
        gameGraph.addEdge(findTerritory("Kentucky"), findTerritory("Virginia"));
        gameGraph.addEdge(findTerritory("Tennessee"), findTerritory("Virginia"));
        gameGraph.addEdge(findTerritory("Tennessee"), findTerritory("North Carolina"));
        gameGraph.addEdge(findTerritory("Tennessee"), findTerritory("Georgia"));
        gameGraph.addEdge(findTerritory("Tennessee"), findTerritory("Alabama"));
        gameGraph.addEdge(findTerritory("Tennessee"), findTerritory("Mississippi"));
        gameGraph.addEdge(findTerritory("Mississippi"), findTerritory("Louisiana"));
        gameGraph.addEdge(findTerritory("Mississippi"), findTerritory("Alabama"));
        gameGraph.addEdge(findTerritory("Alabama"), findTerritory("Georgia"));
        gameGraph.addEdge(findTerritory("Alabama"), findTerritory("Florida"));
        gameGraph.addEdge(findTerritory("Georgia"), findTerritory("South Carolina"));
        gameGraph.addEdge(findTerritory("North Carolina"), findTerritory("South Carolina"));
        gameGraph.addEdge(findTerritory("Georgia"), findTerritory("Florida"));
        gameGraph.addEdge(findTerritory("Georgia"), findTerritory("North Carolina"));
        gameGraph.addEdge(findTerritory("Virginia"), findTerritory("North Carolina"));
        gameGraph.addEdge(findTerritory("New York"), findTerritory("Pennsylvania"));
        gameGraph.addEdge(findTerritory("New York"), findTerritory("Maryland"));
        gameGraph.addEdge(findTerritory("Pennsylvania"), findTerritory("Maryland"));
        gameGraph.addEdge(findTerritory("Pennsylvania"), findTerritory("West Virginia"));
        gameGraph.addEdge(findTerritory("Pennsylvania"), findTerritory("Ohio"));
        gameGraph.addEdge(findTerritory("West Virginia"), findTerritory("Ohio"));
        gameGraph.addEdge(findTerritory("West Virginia"), findTerritory("Kentucky"));
        gameGraph.addEdge(findTerritory("Maryland"), findTerritory("Virginia"));
        gameGraph.addEdge(findTerritory("Maine"), findTerritory("Vermont"));
        gameGraph.addEdge(findTerritory("Maine"), findTerritory("Massachusetts"));
        gameGraph.addEdge(findTerritory("Vermont"), findTerritory("Massachusetts"));
        gameGraph.addEdge(findTerritory("New York"), findTerritory("Massachusetts"));
    }

    @Override
    protected void initContinents() {
        System.out.println(gameGraph.getTerritories());
        theWest.addTerritory(gameGraph.getVertex("Washington"));
        theWest.addTerritory(gameGraph.getVertex( "Oregon"));
        theWest.addTerritory(gameGraph.getVertex("Idaho"));
        theWest.addTerritory(gameGraph.getVertex("Nevada"));
        theWest.addTerritory(gameGraph.getVertex("California"));

        fourCorners.addTerritory(gameGraph.getVertex("Utah"));
        fourCorners.addTerritory(gameGraph.getVertex("Arizona"));
        fourCorners.addTerritory(gameGraph.getVertex("Colorado"));
        fourCorners.addTerritory(gameGraph.getVertex("New Mexico"));

        upperMidwest.addTerritory(gameGraph.getVertex("Montana"));
        upperMidwest.addTerritory(gameGraph.getVertex("Wyoming"));
        upperMidwest.addTerritory(gameGraph.getVertex("North Dakota"));
        upperMidwest.addTerritory(gameGraph.getVertex("South Dakota"));
        upperMidwest.addTerritory(gameGraph.getVertex("Nebraska"));
        upperMidwest.addTerritory(gameGraph.getVertex("Minnesota"));
        upperMidwest.addTerritory(gameGraph.getVertex("Iowa"));

        sunbelt.addTerritory(gameGraph.getVertex("Kansas"));
        sunbelt.addTerritory(gameGraph.getVertex("Oklahoma"));
        sunbelt.addTerritory(gameGraph.getVertex("Texas"));
        sunbelt.addTerritory(gameGraph.getVertex("Missouri"));
        sunbelt.addTerritory(gameGraph.getVertex("Arkansas"));
        sunbelt.addTerritory(gameGraph.getVertex("Louisiana"));

        greatLakes.addTerritory(gameGraph.getVertex("Wisconsin"));
        greatLakes.addTerritory(gameGraph.getVertex("Illinois"));
        greatLakes.addTerritory(gameGraph.getVertex("Michigan"));
        greatLakes.addTerritory(gameGraph.getVertex("Indiana"));
        greatLakes.addTerritory(gameGraph.getVertex("Ohio"));

        dixieland.addTerritory(gameGraph.getVertex("Kentucky"));
        dixieland.addTerritory(gameGraph.getVertex("Virginia"));
        dixieland.addTerritory(gameGraph.getVertex("Tennessee"));
        dixieland.addTerritory(gameGraph.getVertex("North Carolina"));
        dixieland.addTerritory(gameGraph.getVertex("Mississippi"));
        dixieland.addTerritory(gameGraph.getVertex("Alabama"));
        dixieland.addTerritory(gameGraph.getVertex("Georgia"));
        dixieland.addTerritory(gameGraph.getVertex("South Carolina"));
        dixieland.addTerritory(gameGraph.getVertex("Florida"));

        northEast.addTerritory(gameGraph.getVertex("New York"));
        northEast.addTerritory(gameGraph.getVertex("Pennsylvania"));
        northEast.addTerritory(gameGraph.getVertex("West Virginia"));
        northEast.addTerritory(gameGraph.getVertex("Maryland"));

        newEngland.addTerritory(gameGraph.getVertex("Maine"));
        newEngland.addTerritory(gameGraph.getVertex("Vermont"));
        newEngland.addTerritory(gameGraph.getVertex("Massachusetts"));

        continents = new ArrayList<>(Arrays.asList(theWest, fourCorners, upperMidwest, sunbelt, greatLakes, dixieland, northEast, newEngland));
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

    private final Continent theWest = new Continent("The West", 2);
    private final Continent fourCorners = new Continent("Four Corners", 2);
    private final Continent upperMidwest = new Continent("Upper Midwest", 4);
    private final Continent sunbelt = new Continent("Sunbelt", 5);
    private final Continent greatLakes = new Continent("Great lakes", 3);
    private final Continent dixieland = new Continent("Dixieland", 6);
    private final Continent northEast = new Continent("North East", 4);
    private final Continent newEngland = new Continent("New England", 2);
}
