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
                "Evermor",
                // skyrim
                "Solitude",
                "Markarth",
                "Dawnstar",
                "Whiterun",
                "Falkreath",
                "Winter Hold",
                "Windhelm",
                "Riften",
                // hammerfell
                "Dragonstar",
                "Skaven",
                "Sentinel",
                "Helgathe",
                "Gilane",
                "Elinhir",
                "Alik'r Desert",
                "Taneth",
                "Rihad",
                "Colovian Highlands",
                // cyrodiil
                "Kvatch",
                "Skingrad",
                "Chorrol",
                "Bruma",
                "Cheydinhal",
                "Imperial City",
                "Bravil",
                "Blackwood",
                "Leyawiin",
                // valenwood
                "Arenthia",
                "Falinesti",
                "Woodhearth",
                "Silvenar",
                "Elden Root",
                "South Point",
                "Haven",
                // elsweyr
                "Riverhold",
                "Dune",
                "Senchal",
                "Orcrest",
                "Corinth",
                "Rimmen",
                // black marsh
                "Stormhold",
                "Gideon",
                "Soulrest",
                "Thorn",
                "Helstrom",
                "Blackrose",
                "Archon",
                // morrowind
                "Solstheim",
                "Blacklight",
                "Narsis",
                "Tear",
                "Mournhold",
                "Necrom",
                "Northern Island",
                "Grazelands",
                "Ashlands",
                "West Gash",
                "Ald'Ruhn",
                "Balmora",
                "Molag Amur",
                "Red Mountain"
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
                // skyrim
                new Coordinates(342, 88),
                new Coordinates(335, 158),
                new Coordinates(412, 110),
                new Coordinates(439, 186),
                new Coordinates(383, 228),
                new Coordinates(463, 90),
                new Coordinates(471, 140),
                new Coordinates(499, 236),
                // hammerfell
                new Coordinates(286, 203),
                new Coordinates(216, 220),
                new Coordinates(141, 252),
                new Coordinates(116, 303),
                new Coordinates(152, 330),
                new Coordinates(315, 238),
                new Coordinates(315, 271),
                new Coordinates(208, 304),
                new Coordinates(249, 355),
                new Coordinates(296, 318),
                // cyrodiil
                new Coordinates(307, 379),
                new Coordinates(416, 386),
                new Coordinates(375, 298),
                new Coordinates(455, 267),
                new Coordinates(522, 334),
                new Coordinates(447, 336),
                new Coordinates(556, 393),
                new Coordinates(532, 469),
                new Coordinates(488, 505),
                // valenwood
                new Coordinates(340, 424),
                new Coordinates(281, 456),
                new Coordinates(261, 525),
                new Coordinates(332, 479),
                new Coordinates(342, 519),
                new Coordinates(330, 569),
                new Coordinates(388, 568),
                // elsweyr
                new Coordinates(395, 415),
                new Coordinates(387, 489),
                new Coordinates(479, 586),
                new Coordinates(441, 444),
                new Coordinates(431, 518),
                new Coordinates(456, 475),
                // black marsh
                new Coordinates(606, 444),
                new Coordinates(559, 521),
                new Coordinates(557, 596),
                new Coordinates(682, 438),
                new Coordinates(621, 514),
                new Coordinates(626, 589),
                new Coordinates(690, 541),
                // MORROWIND
                new Coordinates(548, 92),
                new Coordinates(539, 196),
                new Coordinates(623, 360),
                new Coordinates(714, 385),
                new Coordinates(720, 329),
                new Coordinates(767, 244),
                new Coordinates(781, 129),
                new Coordinates(657, 160),
                new Coordinates(609, 152),
                new Coordinates(563, 160),
                new Coordinates(595, 219),
                new Coordinates(613, 249),
                new Coordinates(675, 234),
                new Coordinates(631, 194)
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

        gameGraph.addEdge(findTerritory("Solitude"), findTerritory("Evermor"));
        gameGraph.addEdge(findTerritory("Solitude"), findTerritory("Markarth"));
        gameGraph.addEdge(findTerritory("Solitude"), findTerritory("Dawnstar"));
        gameGraph.addEdge(findTerritory("Markarth"), findTerritory("Dawnstar"));
        gameGraph.addEdge(findTerritory("Markarth"), findTerritory("Evermor"));
        gameGraph.addEdge(findTerritory("Markarth"), findTerritory("Whiterun"));
        gameGraph.addEdge(findTerritory("Markarth"), findTerritory("Falkreath"));
        gameGraph.addEdge(findTerritory("Dawnstar"), findTerritory("Whiterun"));
        gameGraph.addEdge(findTerritory("Dawnstar"), findTerritory("Windhelm"));
        gameGraph.addEdge(findTerritory("Windhelm"), findTerritory("Whiterun"));
        gameGraph.addEdge(findTerritory("Windhelm"), findTerritory("Winter Hold"));
        gameGraph.addEdge(findTerritory("Whiterun"), findTerritory("Falkreath"));
        gameGraph.addEdge(findTerritory("Whiterun"), findTerritory("Riften"));
        gameGraph.addEdge(findTerritory("Falkreath"), findTerritory("Riften"));

        gameGraph.addEdge(findTerritory("Dragonstar"), findTerritory("Evermor"));
        gameGraph.addEdge(findTerritory("Dragonstar"), findTerritory("Markarth"));
        gameGraph.addEdge(findTerritory("Dragonstar"), findTerritory("Skaven"));
        gameGraph.addEdge(findTerritory("Dragonstar"), findTerritory("Elinhir"));
        gameGraph.addEdge(findTerritory("Dragonstar"), findTerritory("Alik'r Desert"));
        gameGraph.addEdge(findTerritory("Skaven"), findTerritory("Alik'r Desert"));
        gameGraph.addEdge(findTerritory("Skaven"), findTerritory("Wayrest"));
        gameGraph.addEdge(findTerritory("Skaven"), findTerritory("Sentinel"));
        gameGraph.addEdge(findTerritory("Skaven"), findTerritory("Taneth"));
        gameGraph.addEdge(findTerritory("Sentinel"), findTerritory("Taneth"));
        gameGraph.addEdge(findTerritory("Sentinel"), findTerritory("Helgathe"));
        gameGraph.addEdge(findTerritory("Helgathe"), findTerritory("Gilane"));
        gameGraph.addEdge(findTerritory("Helgathe"), findTerritory("Taneth"));
        gameGraph.addEdge(findTerritory("Gilane"), findTerritory("Firsthold"));
        gameGraph.addEdge(findTerritory("Elinhir"), findTerritory("Alik'r Desert"));
        gameGraph.addEdge(findTerritory("Elinhir"), findTerritory("Markarth"));
        gameGraph.addEdge(findTerritory("Elinhir"), findTerritory("Falkreath"));
        gameGraph.addEdge(findTerritory("Alik'r Desert"), findTerritory("Taneth"));
        gameGraph.addEdge(findTerritory("Alik'r Desert"), findTerritory("Colovian Highlands"));
        gameGraph.addEdge(findTerritory("Taneth"), findTerritory("Colovian Highlands"));
        gameGraph.addEdge(findTerritory("Taneth"), findTerritory("Rihad"));
        gameGraph.addEdge(findTerritory("Colovian Highlands"), findTerritory("Rihad"));

        gameGraph.addEdge(findTerritory("Kvatch"), findTerritory("Firsthold"));
        gameGraph.addEdge(findTerritory("Kvatch"), findTerritory("Colovian Highlands"));
        gameGraph.addEdge(findTerritory("Kvatch"), findTerritory("Chorrol"));
        gameGraph.addEdge(findTerritory("Kvatch"), findTerritory("Skingrad"));
        gameGraph.addEdge(findTerritory("Skingrad"), findTerritory("Chorrol"));
        gameGraph.addEdge(findTerritory("Chorrol"), findTerritory("Colovian Highlands"));
        gameGraph.addEdge(findTerritory("Chorrol"), findTerritory("Alik'r Desert"));
        gameGraph.addEdge(findTerritory("Chorrol"), findTerritory("Elinhir"));
        gameGraph.addEdge(findTerritory("Chorrol"), findTerritory("Falkreath"));
        gameGraph.addEdge(findTerritory("Chorrol"), findTerritory("Bruma"));
        gameGraph.addEdge(findTerritory("Bruma"), findTerritory("Falkreath"));
        gameGraph.addEdge(findTerritory("Bruma"), findTerritory("Riften"));
        gameGraph.addEdge(findTerritory("Bruma"), findTerritory("Cheydinhal"));
        gameGraph.addEdge(findTerritory("Cheydinhal"), findTerritory("Riften"));
        gameGraph.addEdge(findTerritory("Cheydinhal"), findTerritory("Bravil"));
        gameGraph.addEdge(findTerritory("Bravil"), findTerritory("Blackwood"));

        gameGraph.addEdge(findTerritory("Arenthia"), findTerritory("Kvatch"));
        gameGraph.addEdge(findTerritory("Arenthia"), findTerritory("Skingrad"));
        gameGraph.addEdge(findTerritory("Arenthia"), findTerritory("Falinesti"));
        gameGraph.addEdge(findTerritory("Arenthia"), findTerritory("Silvenar"));
        gameGraph.addEdge(findTerritory("Falinesti"), findTerritory("Silvenar"));
        gameGraph.addEdge(findTerritory("Falinesti"), findTerritory("Woodhearth"));
        gameGraph.addEdge(findTerritory("Woodhearth"), findTerritory("Silvenar"));
        gameGraph.addEdge(findTerritory("Woodhearth"), findTerritory("Elden Root"));
        gameGraph.addEdge(findTerritory("Woodhearth"), findTerritory("Skywatch"));
        gameGraph.addEdge(findTerritory("Silvenar"), findTerritory("Elden Root"));
        gameGraph.addEdge(findTerritory("South Point"), findTerritory("Elden Root"));
        gameGraph.addEdge(findTerritory("South Point"), findTerritory("Haven"));
        gameGraph.addEdge(findTerritory("Elden Root"), findTerritory("Haven"));

        gameGraph.addEdge(findTerritory("Riverhold"), findTerritory("Skingrad"));
        gameGraph.addEdge(findTerritory("Riverhold"), findTerritory("Arenthia"));
        gameGraph.addEdge(findTerritory("Riverhold"), findTerritory("Silvenar"));
        gameGraph.addEdge(findTerritory("Riverhold"), findTerritory("Dune"));
        gameGraph.addEdge(findTerritory("Riverhold"), findTerritory("Orcrest"));
        gameGraph.addEdge(findTerritory("Dune"), findTerritory("Silvenar"));
        gameGraph.addEdge(findTerritory("Dune"), findTerritory("Elden Root"));
        gameGraph.addEdge(findTerritory("Dune"), findTerritory("Orcrest"));
        gameGraph.addEdge(findTerritory("Dune"), findTerritory("Corinth"));
        gameGraph.addEdge(findTerritory("Dune"), findTerritory("Senchal"));
        gameGraph.addEdge(findTerritory("Senchal"), findTerritory("Elden Root"));
        gameGraph.addEdge(findTerritory("Senchal"), findTerritory("Haven"));
        gameGraph.addEdge(findTerritory("Senchal"), findTerritory("Corinth"));
        gameGraph.addEdge(findTerritory("Senchal"), findTerritory("Skywatch"));
        gameGraph.addEdge(findTerritory("Senchal"), findTerritory("Imperial City"));
        gameGraph.addEdge(findTerritory("Orcrest"), findTerritory("Corinth"));
        gameGraph.addEdge(findTerritory("Orcrest"), findTerritory("Rimmen"));
        gameGraph.addEdge(findTerritory("Orcrest"), findTerritory("Leyawiin"));
        gameGraph.addEdge(findTerritory("Corinth"), findTerritory("Rimmen"));
        gameGraph.addEdge(findTerritory("Rimmen"), findTerritory("Leyawiin"));

        gameGraph.addEdge(findTerritory("Stormhold"), findTerritory("Bravil"));
        gameGraph.addEdge(findTerritory("Stormhold"), findTerritory("Blackwood"));
        gameGraph.addEdge(findTerritory("Stormhold"), findTerritory("Gideon"));
        gameGraph.addEdge(findTerritory("Stormhold"), findTerritory("Helstrom"));
        gameGraph.addEdge(findTerritory("Stormhold"), findTerritory("Thorn"));
        gameGraph.addEdge(findTerritory("Gideon"), findTerritory("Blackwood"));
        gameGraph.addEdge(findTerritory("Gideon"), findTerritory("Soulrest"));
        gameGraph.addEdge(findTerritory("Gideon"), findTerritory("Helstrom"));
        gameGraph.addEdge(findTerritory("Soulrest"), findTerritory("Helstrom"));
        gameGraph.addEdge(findTerritory("Soulrest"), findTerritory("Blackrose"));
        gameGraph.addEdge(findTerritory("Soulrest"), findTerritory("Imperial City"));
        gameGraph.addEdge(findTerritory("Thorn"), findTerritory("Helstrom"));
        gameGraph.addEdge(findTerritory("Thorn"), findTerritory("Archon"));
        gameGraph.addEdge(findTerritory("Helstrom"), findTerritory("Archon"));
        gameGraph.addEdge(findTerritory("Helstrom"), findTerritory("Blackrose"));

        gameGraph.addEdge(findTerritory("Blacklight"), findTerritory("Windhelm"));
        gameGraph.addEdge(findTerritory("Blacklight"), findTerritory("Whiterun"));
        gameGraph.addEdge(findTerritory("Blacklight"), findTerritory("Riften"));
        gameGraph.addEdge(findTerritory("Blacklight"), findTerritory("Cheydinhal"));
        gameGraph.addEdge(findTerritory("Blacklight"), findTerritory("West Gash"));
        gameGraph.addEdge(findTerritory("Blacklight"), findTerritory("Narsis"));
        gameGraph.addEdge(findTerritory("Narsis"), findTerritory("Cheydinhal"));
        gameGraph.addEdge(findTerritory("Narsis"), findTerritory("Bravil"));
        gameGraph.addEdge(findTerritory("Narsis"), findTerritory("Stormhold"));
        gameGraph.addEdge(findTerritory("Narsis"), findTerritory("Thorn"));
        gameGraph.addEdge(findTerritory("Narsis"), findTerritory("Tear"));
        gameGraph.addEdge(findTerritory("Narsis"), findTerritory("Mournhold"));
        gameGraph.addEdge(findTerritory("Tear"), findTerritory("Thorn"));
        gameGraph.addEdge(findTerritory("Tear"), findTerritory("Mournhold"));
        gameGraph.addEdge(findTerritory("Mournhold"), findTerritory("Balmora"));
        gameGraph.addEdge(findTerritory("Mournhold"), findTerritory("Molag Amur"));
        gameGraph.addEdge(findTerritory("Mournhold"), findTerritory("Necrom"));
        gameGraph.addEdge(findTerritory("Necrom"), findTerritory("Northern Island"));
        gameGraph.addEdge(findTerritory("Necrom"), findTerritory("Molag Amur"));
        gameGraph.addEdge(findTerritory("Molag Amur"), findTerritory("Balmora"));
        gameGraph.addEdge(findTerritory("Molag Amur"), findTerritory("Red Mountain"));
        gameGraph.addEdge(findTerritory("Balmora"), findTerritory("Ald'Ruhn"));
        gameGraph.addEdge(findTerritory("Ald'Ruhn"), findTerritory("Ald'Ruhn"));
        gameGraph.addEdge(findTerritory("Ald'Ruhn"), findTerritory("Red Mountain"));
        gameGraph.addEdge(findTerritory("Ald'Ruhn"), findTerritory("West Gash"));
        gameGraph.addEdge(findTerritory("West Gash"), findTerritory("Ashlands"));
        gameGraph.addEdge(findTerritory("West Gash"), findTerritory("Red Mountain"));
        gameGraph.addEdge(findTerritory("Ashlands"), findTerritory("Solstheim"));
        gameGraph.addEdge(findTerritory("Ashlands"), findTerritory("Grazelands"));
        gameGraph.addEdge(findTerritory("Red Mountain"), findTerritory("Grazelands"));
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

        skyrim.addTerritory(gameGraph.getVertex("Falkreath"));
        skyrim.addTerritory(gameGraph.getVertex("Riften"));
        skyrim.addTerritory(gameGraph.getVertex("Whiterun"));
        skyrim.addTerritory(gameGraph.getVertex("Winter Hold"));
        skyrim.addTerritory(gameGraph.getVertex("Windhelm"));
        skyrim.addTerritory(gameGraph.getVertex("Dawnstar"));
        skyrim.addTerritory(gameGraph.getVertex("Markarth"));
        skyrim.addTerritory(gameGraph.getVertex("Solitude"));

        hammerfell.addTerritory(gameGraph.getVertex("Dragonstar"));
        hammerfell.addTerritory(gameGraph.getVertex("Skaven"));
        hammerfell.addTerritory(gameGraph.getVertex("Sentinel"));
        hammerfell.addTerritory(gameGraph.getVertex("Helgathe"));
        hammerfell.addTerritory(gameGraph.getVertex("Gilane"));
        hammerfell.addTerritory(gameGraph.getVertex("Elinhir"));
        hammerfell.addTerritory(gameGraph.getVertex("Alik'r Desert"));
        hammerfell.addTerritory(gameGraph.getVertex("Taneth"));
        hammerfell.addTerritory(gameGraph.getVertex("Rihad"));
        hammerfell.addTerritory(gameGraph.getVertex("Colovian Highlands"));

        cyrodiil.addTerritory(gameGraph.getVertex("Imperial City"));
        cyrodiil.addTerritory(gameGraph.getVertex("Leyawiin"));
        cyrodiil.addTerritory(gameGraph.getVertex("Kvatch"));
        cyrodiil.addTerritory(gameGraph.getVertex("Skingrad"));
        cyrodiil.addTerritory(gameGraph.getVertex("Chorrol"));
        cyrodiil.addTerritory(gameGraph.getVertex("Bruma"));
        cyrodiil.addTerritory(gameGraph.getVertex("Cheydinhal"));
        cyrodiil.addTerritory(gameGraph.getVertex("Bravil"));
        cyrodiil.addTerritory(gameGraph.getVertex("Blackwood"));

        valenwood.addTerritory(gameGraph.getVertex("Arenthia"));
        valenwood.addTerritory(gameGraph.getVertex("Falinesti"));
        valenwood.addTerritory(gameGraph.getVertex("Woodhearth"));
        valenwood.addTerritory(gameGraph.getVertex("Silvenar"));
        valenwood.addTerritory(gameGraph.getVertex("South Point"));
        valenwood.addTerritory(gameGraph.getVertex("Haven"));
        valenwood.addTerritory(gameGraph.getVertex("Elden Root"));

        elsweyr.addTerritory(gameGraph.getVertex("Riverhold"));
        elsweyr.addTerritory(gameGraph.getVertex("Dune"));
        elsweyr.addTerritory(gameGraph.getVertex("Senchal"));
        elsweyr.addTerritory(gameGraph.getVertex("Orcrest"));
        elsweyr.addTerritory(gameGraph.getVertex("Corinth"));
        elsweyr.addTerritory(gameGraph.getVertex("Rimmen"));

        blackMarsh.addTerritory(gameGraph.getVertex("Stormhold"));
        blackMarsh.addTerritory(gameGraph.getVertex("Gideon"));
        blackMarsh.addTerritory(gameGraph.getVertex("Soulrest"));
        blackMarsh.addTerritory(gameGraph.getVertex("Thorn"));
        blackMarsh.addTerritory(gameGraph.getVertex("Helstrom"));
        blackMarsh.addTerritory(gameGraph.getVertex("Blackrose"));
        blackMarsh.addTerritory(gameGraph.getVertex("Archon"));

        morrowind.addTerritory(gameGraph.getVertex("Solstheim"));
        morrowind.addTerritory(gameGraph.getVertex("Blacklight"));
        morrowind.addTerritory(gameGraph.getVertex("Narsis"));
        morrowind.addTerritory(gameGraph.getVertex("Tear"));
        morrowind.addTerritory(gameGraph.getVertex("Mournhold"));
        morrowind.addTerritory(gameGraph.getVertex("Necrom"));
        morrowind.addTerritory(gameGraph.getVertex("Northern Island"));
        morrowind.addTerritory(gameGraph.getVertex("Grazelands"));
        morrowind.addTerritory(gameGraph.getVertex("Ashlands"));
        morrowind.addTerritory(gameGraph.getVertex("West Gash"));
        morrowind.addTerritory(gameGraph.getVertex("Ald'Ruhn"));
        morrowind.addTerritory(gameGraph.getVertex("Balmora"));
        morrowind.addTerritory(gameGraph.getVertex("Molag Amur"));
        morrowind.addTerritory(gameGraph.getVertex("Red Mountain"));

        continents = new ArrayList<>(Arrays.asList(summersetIsles, highRock, skyrim,
                hammerfell, cyrodiil, valenwood, elsweyr, blackMarsh, morrowind));
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
    private final Continent highRock = new Continent("High Rock", 2);
    private final Continent skyrim = new Continent("Skyrim", 3);
    private final Continent hammerfell = new Continent("Hammerfell", 4);
    private final Continent cyrodiil = new Continent("Cyrodiil", 5);
    private final Continent valenwood = new Continent("Valenwood", 3);
    private final Continent elsweyr = new Continent("Elsweyr", 2);
    private final Continent blackMarsh = new Continent("Black Marsh", 3);
    private final Continent morrowind = new Continent("Black Marsh", 7);
}
