package gui.playerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ColorComboBox extends JComboBox<Color> implements ActionListener {
    private final int SIZE = PlayerMenu.HEIGHT/10;
    private Color oldSelectedColor;

    ColorComboBox() {
        setSize(SIZE, SIZE);
        setPreferredSize(new Dimension(SIZE+20,SIZE));
        setEditable(false);

        Color firstAvailableColor = ((ArrayList<Color>)ColorModel.getAvailableColors()).get(0);
        oldSelectedColor = firstAvailableColor;
        ColorModel.chooseColor(firstAvailableColor);

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("actionPerformed " + e.getActionCommand());
        JComboBox cb = (JComboBox)e.getSource();
        Color selectedColor = (Color) cb.getSelectedItem();
        //System.out.println("Selected color: " + selectedColor);
        //System.out.println("Old selected color: " + oldSelectedColor);
        Color previousSelectedColor = oldSelectedColor;
        if (selectedColor != null) {
            oldSelectedColor = selectedColor;
        }
        ColorModel.chooseColor(selectedColor, previousSelectedColor);
    }

    @Override
    public void setSelectedItem(Object anObject) {
        super.setSelectedItem(anObject);
        oldSelectedColor = (Color) anObject;
    }

    public Color getOldSelectedItem() {
        return oldSelectedColor;
    }
}
