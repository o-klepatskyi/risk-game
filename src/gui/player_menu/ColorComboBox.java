package gui.player_menu;

import logic.PlayerColor;
import logic.network.Message;
import logic.network.MessageType;
import logic.network.MultiplayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class ColorComboBox extends JComboBox<PlayerColor> implements ActionListener {
    private final int SIZE = PlayerMenu.HEIGHT/10;
    private PlayerColor oldSelectedColor;
    private final ColorModel colorModel;
    private MultiplayerManager manager;

    ColorComboBox(final ColorModel colorModel, Color color, MultiplayerManager manager) throws Exception {
        this.colorModel = colorModel;
        this.manager = manager;
        setSize(SIZE, SIZE);
        setPreferredSize(new Dimension(SIZE+20,SIZE));
        setEditable(false);

        PlayerColor playerColor = PlayerColor.getPlayerColor(color);

        if (playerColor != null && colorModel.getAvailableColors().contains(color)) {
            oldSelectedColor = playerColor;
            colorModel.chooseColor(playerColor);
        } else throw new Exception("Color is invalid");

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
            if (manager != null ) {
                try {
                    manager.sendMessage(new Message(MessageType.COLOR_CHANGED, manager.playerMenu.getPlayers()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
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