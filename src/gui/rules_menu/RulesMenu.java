package gui.rules_menu;

import util.res.Fonts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RulesMenu extends JPanel {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 650;

    public static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);

    private JFrame frame;
    private JPanel panel, north, center, south;
    private JButton prev, back, next;

    private int slide = 0;


    public RulesMenu(JFrame frame) {
        this.frame = frame;
        panel = this;

        setPreferredSize(SIZE);
        setLayout(new BorderLayout());

        initPanels();
        initButtons();
        addLabels();
    }

    private void initPanels() {
        north = new JPanel();
        north.setPreferredSize(new Dimension(WIDTH, HEIGHT/12));
        add(north, BorderLayout.NORTH);

        center = new JPanel();
        center.setPreferredSize(new Dimension(WIDTH, HEIGHT*8/12));
        center.setBackground(Color.yellow);
        add(center, BorderLayout.CENTER);

        south = new JPanel();
        south.setPreferredSize(new Dimension(WIDTH, HEIGHT*3/12));
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        add(south, BorderLayout.SOUTH);
    }

    private void initButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setMaximumSize(new Dimension(WIDTH, 45));
        ArrayList<JButton> buttons = new ArrayList<>();
        prev = new JButton("< PREV");
        buttons.add(prev);
        prev.setEnabled(false);
        back = new JButton("BACK");
        buttons.add(back);
        next = new JButton("NEXT >");
        buttons.add(next);

        for (JButton button : buttons) {
            button.setFont(Fonts.BUTTON_FONT.deriveFont(20f));
            button.setPreferredSize(new Dimension(100,35));
            buttonPanel.add(button);
        }

        south.add(buttonPanel);
    }

    private void addLabels() {
        JLabel title = new JLabel(titles[slide]);
        title.setFont(Fonts.LABEL_FONT.deriveFont(35f));
        title.setAlignmentX(0.5f);
        title.setAlignmentY(0.5f);
        north.add(title);

        for (String str : lines[slide]) {
            JLabel line = new JLabel(str);
            line.setFont(Fonts.BUTTON_FONT.deriveFont(25f));
            line.setAlignmentX(0.5f);
            south.add(line);
        }
    }

    private String[][] lines = new String[][] {
            {"Welcome to Risk - the Ultimate Battle!","It is the game of 2 to 6 generals, represented by each player.",
                    "Your task will be to eliminate all other players and to stay the last on the global map."}
    };

    private String[] titles = new String[] {
            "Welcome to Risk"
    };

    public static void main(String[] args) {
        JFrame frame = new JFrame("RULES");
        frame.add(new RulesMenu(frame));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
