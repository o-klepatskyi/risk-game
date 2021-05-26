package gui.playerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class PlayerPanel extends JPanel {
    private static final int WIDTH = PlayerMenu.WIDTH-50;
    private static final int HEIGHT = PlayerMenu.HEIGHT/10;

    private final PlayerNameField playerNameField;
    private final ColorComboBox colorComboBox;
    private final JCheckBox botCheckBox;
    private JButton removePlayerButton;
    private static int playerNumber = 1;
    private PlayerMenu parent;

    PlayerPanel(PlayerMenu parent) {
        this.parent = parent;
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        setOpaque(false);
        setBackground(new Color(255, 255, 255, 123));


        playerNameField = new PlayerNameField("Player " + playerNumber++);
        playerNameField.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT-10));
        add(playerNameField);

        colorComboBox = new ColorComboBox();
        colorComboBox.setPreferredSize(new Dimension((int) (WIDTH * (3.0 / 16.0)), HEIGHT - 10));
        colorComboBox.setRenderer(new ColorComboBoxRenderer());
        add(colorComboBox);
        ColorModel.addComboBox(colorComboBox);

        // TODO: make checkboxes bigger
        botCheckBox = new JCheckBox();
        botCheckBox.setOpaque(false);
        botCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        botCheckBox.setPreferredSize(new Dimension(WIDTH / 8 - 20, HEIGHT - 10));
        add(botCheckBox);


        add(getRemovePlayerButton());
        validate();
        setVisible(true);
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
            removePlayerButton.setHorizontalAlignment(SwingConstants.CENTER);
            removePlayerButton.setPreferredSize(new Dimension((int) (WIDTH * (3.0 / 16.0)), HEIGHT - 10));
            removePlayerButton.setEnabled(false);
            PlayerPanel currentPanel = this;
            removePlayerButton.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    parent.removePlayer(currentPanel);
                }
            });
        }
        return removePlayerButton;
    }
}
