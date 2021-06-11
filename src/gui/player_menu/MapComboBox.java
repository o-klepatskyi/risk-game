package gui.player_menu;

import gui.MainFrame;
import logic.maps.MapType;
import logic.network.Message;
import logic.network.MessageType;
import logic.network.MultiplayerManager;
import util.res.Fonts;

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
            System.out.println("map changed");
            oldValue = selectedValue;
            if (MainFrame.isMultiplayer()) {
                MainFrame.manager.sendMessage(new Message(MessageType.MAP_CHANGED, selectedValue));
            }
        }
    }

}
