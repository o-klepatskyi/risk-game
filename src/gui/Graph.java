package gui;

import java.util.*;

public class Graph {

    private HashMap<Territory, ArrayList<Territory>> map = new HashMap<>();

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

    public ArrayList<Territory> getTerritories() {
        return new ArrayList<>(map.keySet());
    }

}
