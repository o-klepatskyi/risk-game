public class Territory {
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
}
