package gui.playerMenu;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class HeaderPanel extends JPanel {

    private static final int WIDTH = PlayerMenu.WIDTH-50;
    private static final int HEIGHT = PlayerMenu.HEIGHT/10;
    private static final Font FONT = new Font("Arial", Font.BOLD, 25);

    HeaderPanel() {
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        setBackground(new Color(255, 255, 255, 123));

        add(getLabel("Players", new Dimension(WIDTH / 2, HEIGHT-10)));
        add(getLabel("Army",    new Dimension((int) (WIDTH * (3.0 / 16.0)) - 10, HEIGHT-10)));
        add(getLabel("Bot?",    new Dimension(WIDTH / 8 - 10, HEIGHT-10)));
        add(getLabel("Remove",  new Dimension((int) (WIDTH * (3.0 / 16.0)) - 10, HEIGHT-10)));

        setVisible(true);
    }

    private JLabel getLabel(String text, Dimension size) {
        JLabel label = new JLabel(text);
        label.setFont(FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new BevelBorder(BevelBorder.LOWERED));
        label.setPreferredSize(size);
        return label;
    }
}
