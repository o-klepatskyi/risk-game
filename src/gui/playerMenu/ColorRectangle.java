package gui.playerMenu;

import javax.swing.*;
import java.awt.*;

public class ColorRectangle extends JPanel {

    private Color color;

    ColorRectangle(Color color, int size) {
        setSize(size,size);
        setBackground(color);
    }

    public Color getColor() {
        return color;
    }
}
