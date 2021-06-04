package logic;

import java.io.Serializable;
import java.util.Objects;

public class Territory implements Serializable {
    private int troops;
    private Player owner;
    private String name;
    private Coordinates coordinates;


    public Territory(String name, int troops, Player owner, Coordinates coordinates) {
        this.name = name;
        this.troops = troops;
        this.owner = owner;
        this.coordinates = coordinates;
    }

    public Territory(String name, Coordinates coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public int getTroops() {
        return troops;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setTroops(int troops) {
        this.troops = troops;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Territory territory = (Territory) o;
        return troops == territory.troops &&
                Objects.equals(owner, territory.owner) &&
                Objects.equals(name, territory.name) &&
                Objects.equals(coordinates, territory.coordinates);
    }
}
