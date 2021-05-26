package gui.playerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ColorComboBox extends JComboBox<PlayerColor> implements ActionListener {
    private final int SIZE = PlayerMenu.HEIGHT/10;
    private PlayerColor oldSelectedColor;

    ColorComboBox() {
        setSize(SIZE, SIZE);
        setPreferredSize(new Dimension(SIZE+20,SIZE));
        setEditable(false);

        PlayerColor firstAvailableColor = ((ArrayList<PlayerColor>)ColorModel.getAvailableColors()).get(0);
        oldSelectedColor = firstAvailableColor;
        ColorModel.chooseColor(firstAvailableColor);

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        PlayerColor selectedColor = (PlayerColor) cb.getSelectedItem();
        PlayerColor previousSelectedColor = oldSelectedColor;
        if (selectedColor != null) {
            oldSelectedColor = selectedColor;
        }
        ColorModel.chooseColor(selectedColor, previousSelectedColor);
    }

    @Override
    public void setSelectedItem(Object anObject) {
        super.setSelectedItem(anObject);
        oldSelectedColor = (PlayerColor) anObject;
    }

    public PlayerColor getOldSelectedItem() {
        return oldSelectedColor;
    }
}
