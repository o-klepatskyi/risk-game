package gui.playerMenu;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ColorChooser extends JComboBox<ColorRectangle> {
    private final int SIZE = PlayerMenu.HEIGHT/10;

    ColorChooser() {
        setSize(SIZE, SIZE);
        setPreferredSize(new Dimension(SIZE*3,SIZE));
        setEditable(false);
        Collection<Color> availableColors = ColorModel.getAvailableColors();
        for (Color color : availableColors) {
            addItem(new ColorRectangle(color, SIZE));
        }
    }
}
