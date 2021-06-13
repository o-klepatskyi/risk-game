package com.risk.gui.menus;

import com.risk.util.resources.Fonts;
import com.risk.util.resources.Images;
import com.risk.util.resources.SoundPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class RulesMenu extends JPanel {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 650;

    public static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);

    private JFrame frame;
    private JPanel panel, north, center, south, buttonPanel;
    private JButton prev, back, next;

    private int slide = 0;


    public RulesMenu(JFrame frame) {
        this.frame = frame;
        panel = this;

        setPreferredSize(SIZE);
        setLayout(new BorderLayout());

        initPanels();
        initButtons();
        changeSlide();
    }

    private void initPanels() {
        north = new JPanel();
        north.setPreferredSize(new Dimension(WIDTH, HEIGHT/12));
        add(north, BorderLayout.NORTH);

        center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setPreferredSize(new Dimension(WIDTH, HEIGHT*8/12));
//        center.setBackground(Color.yellow);
        add(center, BorderLayout.CENTER);

        south = new JPanel();
        south.setPreferredSize(new Dimension(WIDTH, HEIGHT*3/12));
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        add(south, BorderLayout.SOUTH);
    }

    private void initButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setMaximumSize(new Dimension(WIDTH, 45));
        ArrayList<JButton> buttons = new ArrayList<>();
        prev = new JButton("< PREV");
        prev.setEnabled(false);
        prev.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (prev.isEnabled()) {
                    SoundPlayer.buttonClickedSound();
                    prevSlide();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (prev.isEnabled()) {
                    SoundPlayer.optionChosenSound();
                }
            }
        });
        buttons.add(prev);
        prev.setEnabled(false);
        back = new JButton("BACK");
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                back();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
            }
        });
        buttons.add(back);
        next = new JButton("NEXT >");
        next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (next.isEnabled()) {
                    SoundPlayer.buttonClickedSound();
                    nextSlide();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (next.isEnabled()) {
                    SoundPlayer.optionChosenSound();
                }
            }
        });
        buttons.add(next);

        for (JButton button : buttons) {
            button.setFont(Fonts.BUTTON_FONT.deriveFont(20f));
            button.setPreferredSize(new Dimension(100,35));
            buttonPanel.add(button);
        }

        south.add(buttonPanel);
    }

    private void back() {
        panel.setVisible(false);
        frame.remove(panel);
        frame.add(new MainMenu());
        frame.pack();
    }

    private void prevSlide() {
        if (slide > 0) slide--;
        changeSlide();
    }

    private void nextSlide() {
        if (slide < titles.length - 1) slide++;
        changeSlide();
    }

    private void changeSlide() {
        prev.setEnabled(slide != 0);
        next.setEnabled(slide != titles.length - 1);
        north.removeAll();
        center.removeAll();
        south.removeAll();
        south.add(buttonPanel);
        addLabels();
        addImage();
        frame.revalidate();
        frame.repaint();
    }

    private void addImage() {
        Image img = Images.getSlideImage(slide);
        int height = center.getPreferredSize().height;
        int width = (int) ((float) img.getWidth(this)/img.getHeight(this) * height);
        img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        if (img == null) return;
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setPreferredSize(center.getPreferredSize());
        imageLabel.setBorder(new LineBorder(Color.red));
        imageLabel.setAlignmentX(0.5f);
        center.add(imageLabel);
    }

    private void addLabels() {
        JLabel title = new JLabel(titles[slide]);
        title.setFont(Fonts.LABEL_FONT.deriveFont(35f));
        title.setAlignmentX(0.5f);
        title.setAlignmentY(0.5f);
        title.setVisible(true);
        north.add(title);

        for (String str : lines[slide]) {
            JLabel line = new JLabel(str);
            line.setFont(Fonts.BUTTON_FONT.deriveFont(25f));
            line.setAlignmentX(0.5f);
            line.setVisible(true);
            south.add(line);
        }
    }

    private String[][] lines = new String[][] {
            {       "Welcome to Risk - the Ultimate Battle!",
                    "It is the game of 2 to 6 generals, represented by each player.",
                    "Your task will be to eliminate all other players and to stay the last on the global map.",
            "Game is developed by Makarov Ivan and Klepatskyi Oleh. 2021, Kyiv"},
            {       "At the start of each one of your turns, you will receive additional units, which",
                    "you will place on the board to reinforce your army.",
                    "Then you will attack your enemies, if you want.",
                    "After you are done with combat, you will get one chance to relocate some of your units."},
            {"1 - Player panel, which provides settings such as username and color.",
            "2 - Remove player buttons (they are here to regulate the number of players.",
            "3 - Add player, Start game and Back buttons (their usages are obvious too)."},
            {       "1 - Top panel which shows current player and turn phase.",
                    "2 - Side panel which is used to give your orders.",
                    "3 - World map where territories are represented by colored rectangles.",
                    "They are all clickable, you will use them to interact with the game."},
            {       "At the start of your turn, you gain additional units to reinforce your territories.",
                    "The number of units you get will be shown on the side panel.",
                    "The math behind it is simple - you get the 1/3 (without remainder) of territories",
                    "you conquered and additional units for controlling the continents."},
            {"Now choose your territory on the game map and the amount of troops to send there.",
                    "If everything goes OK, troops will appear in the chosen territory.",
                    "You can choose to put all of your reinforcements into one territory or spread them out",
                    "across your territories. Remember: You must place ALL of these reinforcements."},
            {"You can invade from any one territory you control into an adjacent enemy territory.",
                    "Territories are adjacent if they share a border, or a sea-line runs between them.",
                    "You can even attack more than one territory on your turn.",
                    "You can only invade an enemy's territory - not your own."},
            {"Click on your territory and all territories you can attack will be shown.",
                    "After considering winning chances click \"Attack\" button on side panel.",
                    "All your troops will now attack enemy. The results will appear simultaneously.",
                    "When you end your battles for this move, click \"End attack\" button to end phase."},
            {"After you are finished attacking, you get ONE fortification.",
                    "This is not an attack; it is simply a movement from one of your territories to another.",
                    "Territories are \"connected\" if all the territories in between are also",
                    "CONTROLLED BY YOU. You cannot pass through enemy territories."},
            {"Now choose one territory with at least 2 troops to transfer.",
                    "Remember: you MUST leave at least one unit behind — you cannot abandon a territory.",
                    "Then choose a destination from shown options and click Fortify to send them there.",
                    "Your move is now ended."},
            {"You win if you are the last player in the game,",
                    "meaning you control all 42 territories on the board.",
                    "You are the ruler of the world. Congrats!"}
    };

    private String[] titles = new String[] {
            "Welcome to Risk",
            "Overview",
            "Interface - Player lobby",
            "Interface - Game menu",
            "Step 1: receive and place reinforcements",
            "Step 1: reinforcement example",
            "Step 2: attack",
            "Step 2: attack example",
            "Step 3: fortify your position",
            "Step 3: fortifying example",
            "Winning"
    };
}
