package gui.playerMenu;

import java.awt.*;
// TODO: add photos to colors in comboboxes
public class PlayerColor extends Color {
    private String colorName;

    public PlayerColor(int r, int g, int b, String colorName) {
        super(r, g, b);
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return colorName;
    }
}
