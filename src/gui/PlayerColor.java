package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PlayerColor extends Color {
    private String colorName;
    private ImageIcon icon;

    public PlayerColor(int r, int g, int b, String colorName) {
        super(r, g, b);
        this.colorName = colorName;
        icon = createImageIcon(colorName);
    }

    private static ImageIcon createImageIcon(String name) {
        String path = "res" + File.separator + "color-icons" + File.separator + name + ".png";
        ImageIcon imageIcon = new ImageIcon(path); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        return imageIcon;
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
