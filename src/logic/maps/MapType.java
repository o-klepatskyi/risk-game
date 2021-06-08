package logic.maps;

public enum MapType {
    WORLD_MAP("World"),
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
        if (type == WORLD_MAP) {
            return new WorldMap();
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
