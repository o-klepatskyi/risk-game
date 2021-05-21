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
        setAlignmentX(0.5f);
        setVisible(true);
        setBackground(new Color(255, 255, 255, 123));



        JLabel players = new JLabel("Players");
        players.setFont(FONT);
        players.setBorder(new BevelBorder(BevelBorder.LOWERED));
        players.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT-10));
        add(players);

        JLabel army = new JLabel("Army");
        army.setFont(FONT);
        army.setHorizontalAlignment(SwingConstants.CENTER);
        army.setBorder(new BevelBorder(BevelBorder.LOWERED));
        army.setPreferredSize(new Dimension(WIDTH / 4 - 10, HEIGHT-10));
        add(army);

        JLabel bot = new JLabel("Bot?");
        bot.setFont(FONT);
        bot.setHorizontalAlignment(SwingConstants.CENTER);
        bot.setBorder(new BevelBorder(BevelBorder.LOWERED));
        bot.setPreferredSize(new Dimension(WIDTH / 4 - 10, HEIGHT-10));
        add(bot);
    }
}
