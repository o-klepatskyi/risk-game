package gui.player_menu;

import gui.Main;
import logic.PlayerColor;
import logic.network.Message;
import logic.network.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;

class ColorComboBox extends JComboBox<PlayerColor> implements ActionListener {
    private final int SIZE = PlayerMenu.HEIGHT/10;
    private PlayerColor oldSelectedColor;
    private final ColorModel colorModel;

    ColorComboBox(final ColorModel colorModel, Color color) {
        this.colorModel = colorModel;

        setSize(SIZE, SIZE);
        setPreferredSize(new Dimension(SIZE+20,SIZE));
        setEditable(false);

        PlayerColor playerColor = PlayerColor.getPlayerColor(color);

        if (playerColor != null && colorModel.getAvailableColors().contains(color)) {
            oldSelectedColor = playerColor;
            colorModel.chooseColor(playerColor);
        } else throw new InvalidParameterException("Color is invalid");

        addActionListener(this);
    }

    ColorComboBox(final ColorModel colorModel) {
        setSize(SIZE, SIZE);
        setPreferredSize(new Dimension(SIZE+20,SIZE));
        setEditable(false);

        this.colorModel = colorModel;
        PlayerColor firstAvailableColor = ((ArrayList<PlayerColor>)colorModel.getAvailableColors()).get(0);
        oldSelectedColor = firstAvailableColor;
        colorModel.chooseColor(firstAvailableColor);

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        PlayerColor selectedColor = (PlayerColor) cb.getSelectedItem();
        PlayerColor previousSelectedColor = oldSelectedColor;

        if (selectedColor != null && !selectedColor.equals(oldSelectedColor)) {
            oldSelectedColor = selectedColor;
            colorModel.chooseColor(selectedColor, previousSelectedColor);
            if (Main.isMultiplayer()) {
                Main.manager.sendMessage(new Message(MessageType.COLOR_CHANGED, Main.manager.playerMenu.getPlayers()));
            }
        }
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