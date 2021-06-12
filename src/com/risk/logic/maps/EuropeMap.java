package com.risk.logic.maps;

import com.risk.logic.Continent;
import com.risk.logic.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;

class EuropeMap extends Map{

    EuropeMap() {
        type = MapType.EUROPE;

        String[] namesOfTerritories = new String[] {
                // west europe
                "Ireland",
                "Scotland",
                "Wales",
                "England",
                "Brittany",
                "Ile-de-France",
                "Occitania",
                "Benelux",
                "Alsace-Lorraine",
                // spain
                "Castile",
                "Valencia",
                "Andalusia",
                "Portugal",
                // central europe
                "Westphalia",
                "Denmark",
                "Bayern",
                "Brandenburg",
                "West Poland",
                "Bohemia",
                "Austria",
                // italy
                "Switzerland",
                "Piedmont",
                "Tuscany",
                "Basilicata",
                "Sicily",
                "Sardinia",
                "Corsica",
                // balkans
                "Hungary",
                "Yugoslavia",
                "Bulgaria",
                "Greece",
                // east europe
                "Romania",
                "East Poland",
                "Baltics",
                "Ukraine",
                "Belarus",
                "Central Russia",
                "Karelia",
                "Volga basin",
                "Kuban",
                "Kazakhstan",
                "Siberia",
                "Ural"
        };

        Coordinates[] coordinates = new Coordinates[] {
                // west europe
                new Coordinates(130,205),
                new Coordinates(210,175),
                new Coordinates(175,275),
                new Coordinates(230,235),
                new Coordinates(175,345),
                new Coordinates(233,360),
                new Coordinates(225,425),
                new Coordinates(290,285),
                new Coordinates(290,360),
                // spain
                new Coordinates(110,425),
                new Coordinates(135,510),
                new Coordinates(60,515),
                new Coordinates(35,460),
                // central europe
                new Coordinates(330,310),
                new Coordinates(355,225),
                new Coordinates(350,360),
                new Coordinates(400,295),
                new Coordinates(465,305),
                new Coordinates(405,345),
                new Coordinates(400,390),
                // italy
                new Coordinates(313,390),
                new Coordinates(327,425),
                new Coordinates(365,480),
                new Coordinates(415,530),
                new Coordinates(370,580),
                new Coordinates(290,525),
                new Coordinates(295,480),
                // balkans
                new Coordinates(470,385),
                new Coordinates(450,460),
                new Coordinates(545,480),
                new Coordinates(500,540),
                // east europe
                new Coordinates(555,415),
                new Coordinates(530,310),
                new Coordinates(525,200),
                new Coordinates(655,345),
                new Coordinates(587,260),
                new Coordinates(650,180),
                new Coordinates(680,90),
                new Coordinates(740,250),
                new Coordinates(800,390),
                new Coordinates(878,260),
                new Coordinates(800,40),
                new Coordinates(865,160)
        };

        initializeTerritories(namesOfTerritories, coordinates);
        createGraphFromTerritories();
        initContinents();
    }

    @Override
    protected void createGraphFromTerritories() {
        gameGraph.addEdge(findTerritory("Ireland"), findTerritory("Scotland"));
        gameGraph.addEdge(findTerritory("Ireland"), findTerritory("Wales"));
        gameGraph.addEdge(findTerritory("Wales"), findTerritory("England"));
        gameGraph.addEdge(findTerritory("Scotland"), findTerritory("England"));
        gameGraph.addEdge(findTerritory("Brittany"), findTerritory("Wales"));
        gameGraph.addEdge(findTerritory("Brittany"), findTerritory("Ile-de-France"));
        gameGraph.addEdge(findTerritory("England"), findTerritory("Ile-de-France"));
        gameGraph.addEdge(findTerritory("Occitania"), findTerritory("Ile-de-France"));
        gameGraph.addEdge(findTerritory("Occitania"), findTerritory("Brittany"));
        gameGraph.addEdge(findTerritory("Ile-de-France"), findTerritory("Benelux"));
        gameGraph.addEdge(findTerritory("Ile-de-France"), findTerritory("Alsace-Lorraine"));
        gameGraph.addEdge(findTerritory("Ile-de-France"), findTerritory("Switzerland"));
        gameGraph.addEdge(findTerritory("Piedmont"), findTerritory("Switzerland"));
        gameGraph.addEdge(findTerritory("Bayern"), findTerritory("Alsace-Lorraine"));
        gameGraph.addEdge(findTerritory("Bayern"), findTerritory("Switzerland"));
        gameGraph.addEdge(findTerritory("Switzerland"), findTerritory("Alsace-Lorraine"));
        gameGraph.addEdge(findTerritory("Alsace-Lorraine"), findTerritory("Benelux"));
        gameGraph.addEdge(findTerritory("Castile"), findTerritory("Occitania"));
        gameGraph.addEdge(findTerritory("Valencia"), findTerritory("Occitania"));
        gameGraph.addEdge(findTerritory("Switzerland"), findTerritory("Occitania"));
        gameGraph.addEdge(findTerritory("Piedmont"), findTerritory("Occitania"));
        gameGraph.addEdge(findTerritory("Valencia"), findTerritory("Andalusia"));
        gameGraph.addEdge(findTerritory("Castile"), findTerritory("Valencia"));
        gameGraph.addEdge(findTerritory("Castile"), findTerritory("Andalusia"));
        gameGraph.addEdge(findTerritory("Castile"), findTerritory("Portugal"));
        gameGraph.addEdge(findTerritory("Portugal"), findTerritory("Andalusia"));
        gameGraph.addEdge(findTerritory("Westphalia"), findTerritory("Benelux"));
        gameGraph.addEdge(findTerritory("Westphalia"), findTerritory("Alsace-Lorraine"));
        gameGraph.addEdge(findTerritory("Westphalia"), findTerritory("Denmark"));
        gameGraph.addEdge(findTerritory("Brandenburg"), findTerritory("Denmark"));
        gameGraph.addEdge(findTerritory("Westphalia"), findTerritory("Bayern"));
        gameGraph.addEdge(findTerritory("Brandenburg"), findTerritory("Bayern"));
        gameGraph.addEdge(findTerritory("Brandenburg"), findTerritory("Bohemia"));
        gameGraph.addEdge(findTerritory("Bohemia"), findTerritory("Bayern"));
        gameGraph.addEdge(findTerritory("Bohemia"), findTerritory("Austria"));
        gameGraph.addEdge(findTerritory("Bohemia"), findTerritory("Hungary"));
        gameGraph.addEdge(findTerritory("Switzerland"), findTerritory("Austria"));
        gameGraph.addEdge(findTerritory("Piedmont"), findTerritory("Austria"));
        gameGraph.addEdge(findTerritory("Piedmont"), findTerritory("Tuscany"));
        gameGraph.addEdge(findTerritory("Piedmont"), findTerritory("Yugoslavia"));
        gameGraph.addEdge(findTerritory("Corsica"), findTerritory("Tuscany"));
        gameGraph.addEdge(findTerritory("Basilicata"), findTerritory("Tuscany"));
        gameGraph.addEdge(findTerritory("Basilicata"), findTerritory("Sicily"));
        gameGraph.addEdge(findTerritory("Basilicata"), findTerritory("Yugoslavia"));
        gameGraph.addEdge(findTerritory("Sardinia"), findTerritory("Sicily"));
        gameGraph.addEdge(findTerritory("Sardinia"), findTerritory("Corsica"));
        gameGraph.addEdge(findTerritory("Austria"), findTerritory("Bayern"));
        gameGraph.addEdge(findTerritory("Austria"), findTerritory("Hungary"));
        gameGraph.addEdge(findTerritory("Yugoslavia"), findTerritory("Hungary"));
        gameGraph.addEdge(findTerritory("Romania"), findTerritory("Hungary"));
        gameGraph.addEdge(findTerritory("Romania"), findTerritory("East Poland"));
        gameGraph.addEdge(findTerritory("Yugoslavia"), findTerritory("Bulgaria"));
        gameGraph.addEdge(findTerritory("Yugoslavia"), findTerritory("Greece"));
        gameGraph.addEdge(findTerritory("Yugoslavia"), findTerritory("Romania"));
        gameGraph.addEdge(findTerritory("Greece"), findTerritory("Bulgaria"));
        gameGraph.addEdge(findTerritory("Romania"), findTerritory("Bulgaria"));
        gameGraph.addEdge(findTerritory("Ukraine"), findTerritory("Bulgaria"));
        gameGraph.addEdge(findTerritory("Ukraine"), findTerritory("Kuban"));
        gameGraph.addEdge(findTerritory("Kuban"), findTerritory("Bulgaria"));
        gameGraph.addEdge(findTerritory("Romania"), findTerritory("Ukraine"));
        gameGraph.addEdge(findTerritory("Austria"), findTerritory("Yugoslavia"));
        gameGraph.addEdge(findTerritory("Brandenburg"), findTerritory("West Poland"));
        gameGraph.addEdge(findTerritory("Hungary"), findTerritory("West Poland"));
        gameGraph.addEdge(findTerritory("Hungary"), findTerritory("East Poland"));
        gameGraph.addEdge(findTerritory("East Poland"), findTerritory("West Poland"));
        gameGraph.addEdge(findTerritory("East Poland"), findTerritory("Ukraine"));
        gameGraph.addEdge(findTerritory("Belarus"), findTerritory("Ukraine"));
        gameGraph.addEdge(findTerritory("Volga basin"), findTerritory("Ukraine"));
        gameGraph.addEdge(findTerritory("Volga basin"), findTerritory("Kuban"));
        gameGraph.addEdge(findTerritory("Kazakhstan"), findTerritory("Kuban"));
        gameGraph.addEdge(findTerritory("Volga basin"), findTerritory("Kazakhstan"));
        gameGraph.addEdge(findTerritory("Ural"), findTerritory("Kazakhstan"));
        gameGraph.addEdge(findTerritory("Volga basin"), findTerritory("Siberia"));
        gameGraph.addEdge(findTerritory("Volga basin"), findTerritory("Ural"));
        gameGraph.addEdge(findTerritory("Ural"), findTerritory("Siberia"));
        gameGraph.addEdge(findTerritory("Belarus"), findTerritory("Volga basin"));
        gameGraph.addEdge(findTerritory("Baltics"), findTerritory("West Poland"));
        gameGraph.addEdge(findTerritory("Baltics"), findTerritory("Belarus"));
        gameGraph.addEdge(findTerritory("Baltics"), findTerritory("Karelia"));
        gameGraph.addEdge(findTerritory("East Poland"), findTerritory("Baltics"));
        gameGraph.addEdge(findTerritory("Central Russia"), findTerritory("Baltics"));
        gameGraph.addEdge(findTerritory("Central Russia"), findTerritory("Karelia"));
        gameGraph.addEdge(findTerritory("Siberia"), findTerritory("Karelia"));
        gameGraph.addEdge(findTerritory("Central Russia"), findTerritory("Siberia"));
        gameGraph.addEdge(findTerritory("East Poland"), findTerritory("Belarus"));
        gameGraph.addEdge(findTerritory("Central Russia"), findTerritory("Belarus"));
        gameGraph.addEdge(findTerritory("Central Russia"), findTerritory("Volga basin"));
        gameGraph.addEdge(findTerritory("Westphalia"), findTerritory("Brandenburg"));
    }

    @Override
    protected void initContinents() {
        westEurope.addTerritory(gameGraph.getVertex("Ireland"));
        westEurope.addTerritory(gameGraph.getVertex("Scotland"));
        westEurope.addTerritory(gameGraph.getVertex("Wales"));
        westEurope.addTerritory(gameGraph.getVertex("England"));
        westEurope.addTerritory(gameGraph.getVertex("Brittany"));
        westEurope.addTerritory(gameGraph.getVertex("Ile-de-France"));
        westEurope.addTerritory(gameGraph.getVertex("Occitania"));
        westEurope.addTerritory(gameGraph.getVertex("Benelux"));
        westEurope.addTerritory(gameGraph.getVertex("Alsace-Lorraine"));

        spain.addTerritory(gameGraph.getVertex("Castile"));
        spain.addTerritory(gameGraph.getVertex("Valencia"));
        spain.addTerritory(gameGraph.getVertex("Andalusia"));
        spain.addTerritory(gameGraph.getVertex("Portugal"));

        centralEurope.addTerritory(gameGraph.getVertex("Westphalia"));
        centralEurope.addTerritory(gameGraph.getVertex("Denmark"));
        centralEurope.addTerritory(gameGraph.getVertex("Bayern"));
        centralEurope.addTerritory(gameGraph.getVertex("Brandenburg"));
        centralEurope.addTerritory(gameGraph.getVertex("West Poland"));
        centralEurope.addTerritory(gameGraph.getVertex("Bohemia"));
        centralEurope.addTerritory(gameGraph.getVertex("Austria"));

        italy.addTerritory(gameGraph.getVertex("Switzerland"));
        italy.addTerritory(gameGraph.getVertex("Piedmont"));
        italy.addTerritory(gameGraph.getVertex("Tuscany"));
        italy.addTerritory(gameGraph.getVertex("Basilicata"));
        italy.addTerritory(gameGraph.getVertex("Sicily"));
        italy.addTerritory(gameGraph.getVertex("Sardinia"));
        italy.addTerritory(gameGraph.getVertex("Corsica"));

        balkans.addTerritory(gameGraph.getVertex("Yugoslavia"));
        balkans.addTerritory(gameGraph.getVertex("Hungary"));
        balkans.addTerritory(gameGraph.getVertex("Bulgaria"));
        balkans.addTerritory(gameGraph.getVertex("Greece"));

        eastEurope.addTerritory(gameGraph.getVertex("Romania"));
        eastEurope.addTerritory(gameGraph.getVertex("East Poland"));
        eastEurope.addTerritory(gameGraph.getVertex("Baltics"));
        eastEurope.addTerritory(gameGraph.getVertex("Ukraine"));
        eastEurope.addTerritory(gameGraph.getVertex("Belarus"));
        eastEurope.addTerritory(gameGraph.getVertex("Central Russia"));
        eastEurope.addTerritory(gameGraph.getVertex("Karelia"));
        eastEurope.addTerritory(gameGraph.getVertex("Volga basin"));
        eastEurope.addTerritory(gameGraph.getVertex("Kazakhstan"));
        eastEurope.addTerritory(gameGraph.getVertex("Kuban"));
        eastEurope.addTerritory(gameGraph.getVertex("Siberia"));
        eastEurope.addTerritory(gameGraph.getVertex("Ural"));

        continents = new ArrayList<>(Arrays.asList(centralEurope,italy,westEurope,eastEurope,balkans,spain));
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

    private final Continent centralEurope = new Continent("Germany", 5);
    private final Continent italy = new Continent("Italy", 4);
    private final Continent balkans = new Continent("Balkans", 2);
    private final Continent eastEurope = new Continent("East Europe", 7);
    private final Continent westEurope = new Continent("West Europe", 5);
    private final Continent spain = new Continent("Spain", 2);
}
