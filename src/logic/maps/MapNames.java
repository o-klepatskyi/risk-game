package logic.maps;

public enum MapNames {
    WORLD_MAP("World");

    public final String name;

    MapNames(String name) {
        this.name = name;
    }

    public static MapNames getByName(String name) {
        for (MapNames value : MapNames.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
