package gui.playerMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ColorModel {
    private static ArrayList<Color> colors = new ArrayList<>(Arrays.asList(
            new PlayerColor(0,0,0, "black"),
            new PlayerColor(255, 255, 255, "white"),
            new PlayerColor(255,0,0, "red"),
            new PlayerColor(0, 0, 255, "blue"),
            new PlayerColor(255, 255, 0, "yellow"),
            new PlayerColor(20, 255,0, "green")
    ));

//            new Color(0,0,0),
//            new Color(255, 255, 255),
//            new Color(255,0,0),
//            new Color(0, 0, 255),
//            new Color(255, 255, 0),
//            new Color(20, 255,0)

    private static final ArrayList<Color> availableColors = new ArrayList<>(colors);
    private static final ArrayList<ColorComboBox> comboBoxes = new ArrayList<>();

    public static Collection<Color> getAvailableColors() {
        return availableColors;
    }

    public static void chooseColor(Color newColor, Color oldColor) {
        System.out.println("chooseColor");
        System.out.println(availableColors);
        if (availableColors.contains(newColor)
                && colors.contains(oldColor)
                && !newColor.equals(oldColor)) {

            availableColors.add(oldColor);
            availableColors.remove(newColor);
            updateAll();
        }
    }

    public static void chooseColor(Color newColor) {
        System.out.println("choose color");
        System.out.println(availableColors);
        availableColors.remove(newColor);
    }

    public static void update(ColorComboBox comboBox) {
        System.out.println("update");
        System.out.println("remove all");
        comboBox.removeAllItems();
        System.out.println("addItem");
        comboBox.addItem(comboBox.getOldSelectedItem());
        for (Color c : availableColors) {
            comboBox.addItem(c);
        }
    }

    public static void updateAll() {
        System.out.println("updateAll");
        System.out.println(availableColors);
        for (ColorComboBox cb : comboBoxes) {
            update(cb);
        }
    }

    public static void addComboBox(ColorComboBox cb) {
        System.out.println("addComboBox");
        if (colors.size() == comboBoxes.size()) {
            return;
        }
        cb.addActionListener(cb);
        comboBoxes.add(cb);
        updateAll();
    }
}
