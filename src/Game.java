import java.awt.*;
import java.util.ArrayList;

public class Game {
    private int troopsPerPlayer;
    private final int numberOfTerritories = 42;

    private ArrayList<Player> players;
    private final String[] namesOfTerritories = {
            "Afghanistan", "Alaska", "Alberta", "Argentina", "Brazil", "Central America",
            "China", "Congo", "East Africa", "Eastern Australia", "Eastern United States", "Egypt",
            "Great Britain", "Greenland", "Iceland", "India", "Indonesia", "Irkutsk",
            "Japan", "Kamchatka", "Madagascar", "Middle East", "Mongolia", "New Guinea",
            "North Africa", "Northern Europe", "Northwest Territory", "Ontario", "Peru", "Quebec",
            "Scandinavia", "Siam", "Siberia", "South Africa", "Southern Europe", "Ukraine",
            "Ural", "Venezuela", "Western Australia", "Western Europe", "Western United States", "Yakutsk"
    };
    private final Coordinates[] coordinates = {
            new Coordinates(640, 240), new Coordinates(60, 105), new Coordinates(150, 155), new Coordinates(245, 510), new Coordinates(300, 430), new Coordinates(165, 315),
            new Coordinates(750, 290), new Coordinates(515, 480), new Coordinates(550, 430), new Coordinates(880, 535), new Coordinates(220, 250), new Coordinates(515, 370),
            new Coordinates(385, 210), new Coordinates(330, 70), new Coordinates(405, 135), new Coordinates(680, 330), new Coordinates(780, 475), new Coordinates(750, 175),
            new Coordinates(870, 235), new Coordinates(850, 90), new Coordinates(600, 570), new Coordinates(580, 345), new Coordinates(770, 230), new Coordinates(865, 450),
            new Coordinates(440, 400), new Coordinates(475, 220), new Coordinates(150, 100), new Coordinates(210, 165), new Coordinates(220, 445), new Coordinates(275, 165),
            new Coordinates(475, 140), new Coordinates(765, 365), new Coordinates(700, 115), new Coordinates(520, 570), new Coordinates(480, 275), new Coordinates(550, 195),
            new Coordinates(650, 150), new Coordinates(220, 365), new Coordinates(820, 565), new Coordinates(400, 305), new Coordinates(145, 225), new Coordinates(770, 85)
    };
    private int numberOfPlayers;
    private GameWindow gameWindow;
    private Graph gameGraph;
    public Game(ArrayList<Player> players) {
        this.players = players;
        //numberOfPlayers = players.size();
        gameGraph = new Graph();

        gameWindow = new GameWindow(this);
    }


    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public Graph getGameGraph() {
        return gameGraph;
    }
}
