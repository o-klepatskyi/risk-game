package com.risk.gui.player_menu;

import com.risk.gui.Main;
import com.risk.logic.maps.MapType;
import com.risk.logic.network.Message;
import com.risk.logic.network.MessageType;
import com.risk.util.resources.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MapComboBox extends JComboBox<MapType> {
    private MapType oldValue;

    public MapComboBox() {
        super(MapType.values());
        setFont(Fonts.BUTTON_FONT.deriveFont(18f));
        setEnabled(false);
        setEditable(false);
        oldValue = (MapType) getSelectedItem();
        addActionListener(this);
        setForeground(Color.black);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        MapType selectedValue = (MapType) cb.getSelectedItem();

        if (selectedValue != null && !selectedValue.equals(oldValue)) {
            oldValue = selectedValue;
            if (Main.isMultiplayer()) {
                Main.manager.sendMessage(new Message(MessageType.MAP_CHANGED, selectedValue));
            }
        }
    }

}
