package gui.player_menu;

import logic.PlayerColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class ColorModel {

    private final ArrayList<PlayerColor> colors = new ArrayList<>(Arrays.asList(
            new PlayerColor(255,0,0, "red"),
            new PlayerColor(0, 0, 255, "blue"),
            new PlayerColor(34, 177,76, "green"),
            new PlayerColor(255, 201, 14, "yellow"),
            new PlayerColor(255,127,39, "orange"),
            new PlayerColor(255, 64, 127, "pink")
    ));

    private final ArrayList<PlayerColor> availableColors = new ArrayList<>(colors);
    private final ArrayList<ColorComboBox> comboBoxes = new ArrayList<>();

    public Collection<PlayerColor> getAvailableColors() {
        return availableColors;
    }

    public void chooseColor(PlayerColor newColor, PlayerColor oldColor) {
        if (availableColors.contains(newColor)
                && colors.contains(oldColor)
                && !newColor.equals(oldColor)) {

            availableColors.add(oldColor);
            availableColors.remove(newColor);
            updateAll();
        }
    }

    public void chooseColor(Color newColor) {
        availableColors.remove(newColor);
    }

    public void update(ColorComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem(comboBox.getOldSelectedItem());
        for (PlayerColor c : availableColors) {
            comboBox.addItem(c);
        }
    }

    public void updateAll() {
        for (ColorComboBox cb : comboBoxes) {
            update(cb);
        }
    }

    public void addComboBox(ColorComboBox cb) {
        System.out.println("addComboBox");
        System.out.println(availableColors);
        if (colors.size() == comboBoxes.size()) {
            return;
        }
        cb.addActionListener(cb);
        comboBoxes.add(cb);
        updateAll();
    }

    public void removeComboBox(ColorComboBox cb) {
        PlayerColor color = cb.getOldSelectedItem();
        if (colors.contains(color) && !availableColors.contains(color) && comboBoxes.contains(cb)) {
            availableColors.add(color);
            comboBoxes.remove(cb);
            updateAll();
        }
    }
}
