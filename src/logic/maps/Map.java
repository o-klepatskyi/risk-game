package logic.maps;

import logic.*;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public abstract class Map implements Serializable {
    protected ArrayList<Territory> territories;
    protected int numberOfTerritories;
    protected ArrayList<Continent> continents;
    protected Graph gameGraph = new Graph();
    protected Image image;

    public Graph initGraph(ArrayList<Player> players) {
        if (players.size() < 2) throw new IllegalArgumentException();
        distributeTerritories(players.size(), players);
        return gameGraph;
    }

    protected abstract void createGraphFromTerritories();
    protected abstract void initContinents();

    protected final void initializeTerritories(String[] namesOfTerritories, Coordinates[] coordinates) {
        if (namesOfTerritories.length != coordinates.length)
            throw new IllegalArgumentException("Wrong number of names and coordinates.");
        territories = new ArrayList<>();
        numberOfTerritories = namesOfTerritories.length;
        for(int i = 0; i < numberOfTerritories; i++) {
            territories.add(new Territory(namesOfTerritories[i], coordinates[i]));
        }
    }

    protected final Territory findTerritory(String name) {
        for(Territory territory : territories) {
            if(territory.getName().equals(name))
                return territory;
        }
        return null;
    }

    private void distributeTerritories(int numberOfPlayers, ArrayList<Player> players) {
        int territoriesLeft = numberOfTerritories;
        ArrayList<Territory> territories = new ArrayList<>(this.territories);
        for(int i = 0; i < numberOfPlayers; i++) {
            int playerTerritoriesNumber = numberOfTerritories / numberOfPlayers;
            int playerTroopsNumber = getInitialPlayerTroopsNumber(numberOfPlayers);
            ArrayList<Territory> playerTerritories = new ArrayList<>();


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

    /**
     * Gets number of players and return different number for any number of players
     * @param numberOfPlayers 2-6 players
     */
    protected abstract int getInitialPlayerTroopsNumber(int numberOfPlayers);

    private Territory getRandomTerritory(ArrayList<Territory> t) {
        Random rand = new Random();
        return t.get(rand.nextInt(t.size()));
    }

    public Graph getGameGraph() {
        return gameGraph;
    }

    public ArrayList<Continent> getContinents() {
        return continents;
    }

    public Image getImage() {
        return image;
    }
}
