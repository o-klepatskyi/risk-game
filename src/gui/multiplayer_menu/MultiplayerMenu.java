package gui.multiplayer_menu;

import gui.main_menu.MainMenu;
import logic.network.MultiplayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// todo: design
public class MultiplayerMenu extends JPanel {

    private final JFrame frame;

    public MultiplayerMenu(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(500,500));
        setLayout(new GridLayout(3,1,0,30));
        add(new FillerButton(100,0));
        add(getServerButton());
        add(getClientButton());
        add(getBackButton());
        setVisible(true);
    }

    private JButton getClientButton() {
        JButton clientButton = new JButton("Connect");
        clientButton.setHorizontalAlignment(SwingConstants.CENTER);
        clientButton.setSize(new Dimension(150,35));
        clientButton.setPreferredSize(new Dimension(150,35));
        clientButton.setVisible(true);
        clientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openClientMenu();
            }
        });
        return clientButton;
    }

    private JButton getServerButton() {
        JButton serverButton = new JButton("Create room");
        serverButton.setHorizontalAlignment(SwingConstants.CENTER);
        serverButton.setPreferredSize(new Dimension(150,35));
        serverButton.setVisible(true);
        serverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openServerMenu();
            }
        });
        return serverButton;
    }

    private JButton getBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setSize(new Dimension(100,35));
        backButton.setVisible(true);
        JPanel currentMenu = this;
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.remove(currentMenu);
                frame.add(new MainMenu(frame));
                frame.pack();
            }
        });
        return backButton;
    }

    private void openServerMenu() {
        frame.remove(this);
        frame.add(new ServerMenu(frame));
        frame.pack();
    }

    private void openClientMenu() {
        frame.remove(this);
        frame.add(new ClientMenu(frame));
        frame.pack();
    }
}

