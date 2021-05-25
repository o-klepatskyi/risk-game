package gui.playerMenu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerMenu extends JPanel {
    private final Image bgImg = new ImageIcon("res/player-menu-bg-800-450.jpg")
            .getImage();

    public static final int WIDTH = 800;
    public static final int HEIGHT = 450;
    private int playerNumber = 3; // can not be bigger than ColorModel availableColors length
    private final ArrayList<PlayerPanel> panels = new ArrayList<>();

    public PlayerMenu() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(new FlowLayout());
        add(new HeaderPanel());
        for (int i = 0; i < playerNumber; i++) {
            PlayerPanel p = new PlayerPanel(i);
            panels.add(p);
            add(p);
        }
        ColorModel.updateAll();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Risk - Game settings");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new PlayerMenu());
        frame.validate();
    }
}
