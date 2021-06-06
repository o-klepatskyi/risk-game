package logic;


import java.io.Serializable;
import java.util.*;

public class Graph implements Serializable {

    private HashMap<Territory, List<Territory>> map = new HashMap<>();

    public void addVertex(Territory s) {
        map.put(s, new ArrayList<>());
    }

    public void addEdge(Territory source, Territory destination) {

        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);

        map.get(source).add(destination);
        map.get(destination).add(source);
    }

    public boolean hasVertex(Territory s) {
        return map.containsKey(s);
    }

    public boolean hasEdge(Territory s, Territory d) {
        return map.get(s).contains(d);
    }

    public boolean hasEdge(String s, String d) {
        return map.get(getVertex(s)).contains(getVertex(d));
    }

    public Territory getVertex(String territoryName) {
        for(Territory territory : map.keySet()) {
            if(territory.getName().equals(territoryName))
                return territory;
        }
        return null;
    }

    public Territory getVertex(Territory territory) {
        for(Territory t : map.keySet()) {
            if(t.equals(territory))
                return t;
        }
        return null;
    }

    public Territory getVertex(Coordinates coordinates) {
        for(Territory t : map.keySet()) {
            if(t.getCoordinates().getX() == coordinates.getX() && t.getCoordinates().getY() == coordinates.getY())
                return t;
        }
        return null;
    }

    public ArrayList<Territory> getTerritories() {
        return new ArrayList<>(map.keySet());
    }

    public ArrayList<Territory> getTerritories(Player player) {
        ArrayList<Territory> territories = new ArrayList<>();
        for(Territory t : map.keySet()){
            if(t.getOwner().equals(player))
                territories.add(t);
        }
        return territories;
    }

    public ArrayList<Territory> getAdjacentTerritories(Territory territory) {
        ArrayList<Territory> territories = new ArrayList<>();
        for(Territory t : map.keySet()) {
            if(hasEdge(t, territory) && !t.getOwner().equals(territory.getOwner()))
                territories.add(t);
        }
        return territories;
    }

    public ArrayList<Territory> getConnectedTerritories(Territory territory) {

        Map<Territory, Boolean> connectedTerritoriesMap = new HashMap<>();
        for(Territory t : map.keySet()) {
            connectedTerritoriesMap.put(t, false);
        }
        dfs(territory, connectedTerritoriesMap);
        ArrayList<Territory> connectedTerritories = new ArrayList<>();
        for(Territory t : connectedTerritoriesMap.keySet()) {
            if(connectedTerritoriesMap.get(t)) {
                connectedTerritories.add(t);
            }
        }
        connectedTerritories.remove(territory);
        return connectedTerritories;
    }

    private void dfs(Territory territory, Map<Territory, Boolean> visited) {
        visited.put(territory, true);

        for (Territory t : map.get(territory)) {
            if (!visited.get(t) && t.getOwner().equals(territory.getOwner()))
                dfs(t, visited);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Graph graph = (Graph) o;

        return map != null ? map.equals(graph.map) : graph.map == null;
    }

    @Override
    public int hashCode() {
        return map != null ? map.hashCode() : 0;
    }
}
