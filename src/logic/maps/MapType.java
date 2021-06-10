package logic.maps;

import java.io.Serializable;

public enum MapType implements Serializable {
    EARTH("Earth"),
    EUROPE("Europe"),
    USA("USA"),
    TEST("Test");


    public final String name;


    MapType(String name) {
        this.name = name;
    }

    public static MapType getByName(String name) {
        for (MapType value : MapType.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    public static Map getMap(MapType type) {
        switch(type) {
            case EARTH: return new WorldMap();
            case TEST: return new TestMap();
            case EUROPE: return new EuropeMap();
            case USA: return new USAMap();
            default: return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
