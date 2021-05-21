package gui.playerMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ColorModel {
    private ArrayList<Color> colors = new ArrayList<>(Arrays.asList(
            new Color(0,0,0),
            new Color(255, 255, 255),
            new Color(255,0,0),
            new Color(0, 55, 255),
            new Color(255, 227, 0),
            new Color(20, 255,0)
    ));

    private ArrayList<Color> availableColors = new ArrayList<>(colors);

    public Collection<Color> getAvailableColors() {
        return availableColors;
    }


}
