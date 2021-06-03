package logic;

import java.util.ArrayList;

public class Bot {
    private Game game;
    private Player player;

    public Bot(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public void reinforce() {
        ArrayList<Territory> territories = game.getGameGraph().getTerritories(player);
        int troopsLeft = player.getBonus();
        while(troopsLeft != 0) {
            Territory territoryToIncrement = Game.getRandomTerritory(territories);
            territoryToIncrement.setTroops(territoryToIncrement.getTroops()+1);
            troopsLeft--;
        }
        game.getGameMap().drawField();
    }

    public void attack() {

    }

    public void fortify() {

    }

}
