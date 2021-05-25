package gui.playerMenu;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private static final int WIDTH = PlayerMenu.WIDTH-50;
    private static final int HEIGHT = PlayerMenu.HEIGHT/10;

    private PlayerNameField playerNameField;
    private ColorComboBox colorComboBox;
    private JCheckBox botCheckBox;

    PlayerPanel(int playerIndex) {
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        setOpaque(false);
        setBackground(new Color(255, 255, 255, 123));


        playerNameField = new PlayerNameField("Player " + ++playerIndex);
        playerNameField.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT-10));
        add(playerNameField);

        colorComboBox = new ColorComboBox();
        colorComboBox.setPreferredSize(new Dimension(WIDTH / 4 - 10, HEIGHT - 10));
        colorComboBox.setOpaque(true);
        add(colorComboBox);
        ColorModel.addComboBox(colorComboBox);

        botCheckBox = new JCheckBox();
        botCheckBox.setOpaque(false);
        //botCheckBox.setBackground(new Color(255, 255, 255, 123));
        botCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        botCheckBox.setPreferredSize(new Dimension(WIDTH / 4 - 10, HEIGHT - 10));
        add(botCheckBox);

        validate();
        setVisible(true);
    }

    protected void paintComponent(Graphics g)
    {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
