package logic;

import javax.swing.*;
import java.awt.*;

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
}
