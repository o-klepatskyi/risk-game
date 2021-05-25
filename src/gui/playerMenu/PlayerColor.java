package gui.playerMenu;

import java.awt.*;


// TODO: temporary class

public class PlayerColor extends Color {
    private String colorName;

    public PlayerColor(int r, int g, int b, String colorName) {
        super(r, g, b);
    }

    @Override
    public String toString() {
        return colorName;
    }
}
