import java.awt.*;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private GameWindow gameWindow;
    private Graph gameGraph;
    public Game(ArrayList<Player> players) {
        this.players = players;
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
