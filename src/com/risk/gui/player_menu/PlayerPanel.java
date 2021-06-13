package com.risk.gui.player_menu;

import com.risk.gui.common_elements.JCheckBoxCustomIcon;
import com.risk.gui.Main;
import com.risk.logic.Player;
import com.risk.util.resources.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class PlayerPanel extends JPanel {
    private static final int WIDTH = PlayerMenu.WIDTH-50;
    private static final int HEIGHT = PlayerMenu.HEIGHT/10;

    private PlayerNameField playerNameField;
    private ColorComboBox colorComboBox;
    private JCheckBox botCheckBox;
    private JButton removePlayerButton;
    private final PlayerMenu parent;

    private final Font TEXTFIELD_FONT = Fonts.BUTTON_FONT.deriveFont((float) HEIGHT-20);
    private final Font BUTTON_FONT  = Fonts.BUTTON_FONT.deriveFont((float) HEIGHT-32);

    /**
     * MULTIPLAYER
     */
    PlayerPanel(PlayerMenu parent, final ColorModel colorModel, Player player) {
        this.parent = parent;
        init();

        add(getPlayerNameField());
        playerNameField.setText(player.getName());
        playerNameField.setEditable(false);
        add(playerNameField);

        try {
            colorComboBox = new ColorComboBox(colorModel, player.getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }

        colorComboBox.setPreferredSize(new Dimension((int) (WIDTH * (3.0 / 16.0)), HEIGHT - 10));
        colorComboBox.setRenderer(new ColorComboBoxRenderer());

        add(colorComboBox);
        colorModel.addComboBox(colorComboBox);

        add(getBotCheckBox());
        botCheckBox.setEnabled(false);
        botCheckBox.setSelected(player.isBot());

        add(getRemovePlayerButton());

        repaint();
        validate();
        setVisible(true);
    }

    PlayerPanel(PlayerMenu parent, final ColorModel colorModel) {
        this.parent = parent;
        init();

        add(getPlayerNameField());

        colorComboBox = new ColorComboBox(colorModel);
        colorComboBox.setPreferredSize(new Dimension((int) (WIDTH * (3.0 / 16.0)), HEIGHT - 10));
        colorComboBox.setRenderer(new ColorComboBoxRenderer());
        add(colorComboBox);
        colorModel.addComboBox(colorComboBox);
        add(getBotCheckBox());
        add(getRemovePlayerButton());

        repaint();
        validate();
        setVisible(true);
    }

    private void init() {
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        setOpaque(false);
        setBackground(new Color(255, 255, 255, 123));
    }

    public JCheckBox getBotCheckBox() {
        if (botCheckBox == null) {
            botCheckBox = new JCheckBoxCustomIcon(WIDTH / 8 - 20,HEIGHT - 10);
        }
        return botCheckBox;
    }

    public PlayerNameField getPlayerNameField() {
        if (playerNameField == null) {
            int i = parent.getTotalNumberOfPlayers() + 1;
            playerNameField = new PlayerNameField("Player " + i);
            playerNameField.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT-10));
            playerNameField.setFont(TEXTFIELD_FONT);
        }
        return playerNameField;
    }

    protected void paintComponent(Graphics g)
    {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public ColorComboBox getColorComboBox() {
        return colorComboBox;
    }

    public JButton getRemovePlayerButton() {
        if (removePlayerButton == null) {
            removePlayerButton = new JButton("Remove player");
            removePlayerButton.setFont(BUTTON_FONT);
            removePlayerButton.setHorizontalAlignment(SwingConstants.CENTER);
            removePlayerButton.setPreferredSize(new Dimension((int) (WIDTH * (3.0 / 16.0)), HEIGHT - 10));
            removePlayerButton.setEnabled(false);

            if (!Main.isMultiplayer() || Main.isServer()) {
                PlayerPanel currentPanel = this;
                removePlayerButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(removePlayerButton.isEnabled()) {
                            parent.removePlayer(currentPanel);
                        }
                    }
                });
            } else {
                removePlayerButton.setToolTipText("Only room owner can remove players");
            }
        }
        return removePlayerButton;
    }

    public Player getPlayer() {
        return new Player(playerNameField.getPlayerName(), colorComboBox.getOldSelectedItem(), botCheckBox.isSelected());
    }
}
