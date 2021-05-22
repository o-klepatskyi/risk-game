import javax.print.attribute.standard.JobKOctets;
import javax.swing.*;
import java.awt.*;

public class GameWindow extends JPanel {
    public GameWindow(JFrame frame) {
        final int WIDTH = 1280, HEIGHT = 720;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());

        JPanel gamePanel = new JPanel();
        gamePanel.setOpaque(true);
        gamePanel.setBackground(Color.WHITE);

        int mapX = (int) (WIDTH*0.25), mapY = (int) (HEIGHT*0.05);
        int mapWidth = (int) (WIDTH*0.75), mapHeight = (int) (HEIGHT*0.95);
        Map map = new Map();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.25;
        gbc.weighty = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel gameTitle = new JPanel();
        gameTitle.setOpaque(true);
        gameTitle.setBackground(Color.RED);
        this.add(gameTitle, gbc);

        JPanel gameFlow = new JPanel();
        gbc.weightx = 0.75;
        gbc.weighty = 0.05;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gameFlow.setOpaque(true);
        gameFlow.setBackground(Color.GREEN);
        this.add(gameFlow, gbc);


        gbc.gridx = 0;
        gbc.weightx = 0.25;
        gbc.weighty = 0.95;
        gbc.gridy = 1;
        this.add(gamePanel, gbc);

        gbc.weightx = 0.75;
        gbc.gridx = 1;
        this.add(map, gbc);


    }

}
