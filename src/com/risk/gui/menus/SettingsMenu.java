package com.risk.gui.menus;

import com.risk.gui.common_elements.JCheckBoxCustomIcon;
import com.risk.gui.Main;
import com.risk.util.resources.Fonts;
import com.risk.util.resources.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsMenu extends Menu {
    private JButton backButton;
    private GridBagConstraints gbc;

    private final int SIZE = 500;

    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);
    private static final Font BUTTON_FONT = Fonts.BUTTON_FONT.deriveFont(35f);

    public SettingsMenu() {
        initWindow();
        initButtons();
    }

    private void initWindow() {
        setFocusable(true);
        setPreferredSize(new Dimension(SIZE,SIZE));
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(85,0,-70,0);
    }

    private void initButtons() {
        JPanel panel1 = new JPanel();
        panel1.setOpaque(false);
        JLabel label1 = new JLabel("Mute sounds: ");
        label1.setFont(BUTTON_FONT);
        label1.setForeground(Color.WHITE);
        JCheckBoxCustomIcon checkBoxMute = new JCheckBoxCustomIcon(35, 35);
        checkBoxMute.setSelected(SoundPlayer.getMuted());
        checkBoxMute.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                SoundPlayer.setMuted(checkBoxMute.isSelected());
            }
        });
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.add(label1);
        panel1.add(checkBoxMute);

        JPanel panel2 = new JPanel();
        panel2.setOpaque(false);
        JLabel label2 = new JLabel("Shuffle players: ");
        label2.setFont(BUTTON_FONT);
        label2.setForeground(Color.WHITE);
        JCheckBoxCustomIcon checkBoxShuffle = new JCheckBoxCustomIcon(35, 35);
        checkBoxShuffle.setSelected(Main.isShuffling());
        checkBoxShuffle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.setShuffledOption(checkBoxShuffle.isSelected());
            }
        });
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel2.add(label2);
        panel2.add(checkBoxShuffle);






        add(panel1, gbc);
        add(panel2, gbc);

        backButton = new JButton("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                Main.openMainMenu();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                backButton.setForeground(Color.YELLOW);
                backButton.setText("< Back >");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setForeground(Color.WHITE);
                backButton.setText("Back");
            }
        });

        backButton.setFont(LABEL_FONT);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setForeground(Color.WHITE);
        add(backButton,gbc);

    }
}

