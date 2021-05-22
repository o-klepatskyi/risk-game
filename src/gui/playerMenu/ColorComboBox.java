package gui.playerMenu;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ColorComboBox extends JComboBox<ColorRectangle> {
    private final int SIZE = PlayerMenu.HEIGHT/10;

    ColorComboBox() {
        setSize(SIZE, SIZE);
        setPreferredSize(new Dimension(SIZE+20,SIZE));
        setEditable(false);
        Collection<Color> availableColors = ColorModel.getAvailableColors();
        for (Color color : availableColors) {
            addItem(new ColorRectangle(color, SIZE));
        }
    }
}
