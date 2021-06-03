package logic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerColor extends Color {
    private String colorName;
    private ImageIcon icon;

    public PlayerColor(int r, int g, int b, String colorName) {
        super(r, g, b);
        this.colorName = colorName;
        icon = createImageIcon(colorName);
    }

    private static ImageIcon createImageIcon(String name) {
        String path = "color-icons/" + name + ".png";
        return new ImageIcon(new ImageIcon(PlayerColor.class.getResource(path)).getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH));
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public String getName() {
        return colorName;
    }

    @Override
    public String toString() {
        return colorName;
    }

    public static PlayerColor getPlayerColor(Color c) {
        ArrayList<PlayerColor> colors = new ArrayList<>(Arrays.asList(RED,BLUE,GREEN,YELLOW,ORANGE,PINK));

        for (PlayerColor color : colors) {
            if (c.equals(color)) return color;
        }
        return null;
    }
    
    public static final PlayerColor RED = new PlayerColor(255,0,0, "red");
    public static final PlayerColor BLUE = new PlayerColor(0, 0, 255, "blue");
    public static final PlayerColor GREEN = new PlayerColor(34, 177,76, "green");
    public static final PlayerColor YELLOW = new PlayerColor(255, 201, 14, "yellow");
    public static final PlayerColor ORANGE = new PlayerColor(255,127,39, "orange");
    public static final PlayerColor PINK = new PlayerColor(255, 64, 127, "pink");
}
