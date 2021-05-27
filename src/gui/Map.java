package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Map extends JPanel {
    private final Color DISABLED_COLOR = Color.LIGHT_GRAY;
    private final int BORDER_MARGIN = 5;

    private final Game game;
    private ArrayList<JButton> buttons;
    private final JPanel panel;

    private boolean buttonClicked = false;
    private JButton attack, defend;

    public Map(Game game) {
        this.setLayout(null);
        this.game = game;
        panel = this;

        setBackground(new Color(0, 145, 182));
        buttons = new ArrayList<>();
        drawField();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backgroundImage = new ImageIcon("res/map.png");
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public void drawField(){
        Graph gameGraph = game.getGameGraph();
        ArrayList<Territory> territories = gameGraph.getTerritories();
        int BUTTON_WIDTH = 50;
        int BUTTON_HEIGHT = 25;
        int BUTTON_MARGIN_LEFT = 20;
        int BUTTON_MARGIN_TOP = 10;
        for(Territory territory : territories) {
            JButton button = new JButton(String.valueOf(territory.getTroops()));
            Coordinates territoryCoordinates = territory.getCoordinates();
            button.setBounds(territoryCoordinates.getX()- BUTTON_MARGIN_LEFT, territoryCoordinates.getY()- BUTTON_MARGIN_TOP,
                    BUTTON_WIDTH, BUTTON_HEIGHT);
            button.setForeground(Color.WHITE);
            button.setBackground(territory.getOwner().getColor());
            button.setName(territory.getName());
            button.setFocusPainted(false);

            panel.add(button);
            buttons.add(button);

        }
        addListeners();
    }

    private void addListeners() {
        for(JButton button : buttons){
            button.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if(!buttonClicked) {
                        highlighButton(button);
                    }
                    else {
                        if(button.getBackground() == DISABLED_COLOR) {
                            resetButtons();
                            highlighButton(button);
                        }
                        else {
                            if(!button.equals(attack)){
                                if(defend != null)
                                    defend.setBorder(null);

                                if(defend != button) {
                                    defend = button;
                                    button.setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_MARGIN));
                                }
                                else {
                                    button.setBorder(null);
                                }
                            }
                            else {
                                resetButtons();
                            }
                        }
                    }
                }

                public void mousePressed(MouseEvent e) {

                }

                public void mouseReleased(MouseEvent e) {

                }

                public void mouseEntered(MouseEvent e) {
                    if(attack == null || button.getBackground().equals(DISABLED_COLOR)) {
                        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, BORDER_MARGIN));
                    }
                    else {
                        if(attack != button)
                            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_MARGIN));
                    }
                }

                public void mouseExited(MouseEvent e) {
                    if(!buttonClicked)
                        resetButtons();
                    else {
                        if(!button.equals(attack) && !button.equals(defend))
                            button.setBorder(null);
                    }
                }
            });
        }
    }

    private void paintAdjacent(JButton button) {
        Graph gameGraph = game.getGameGraph();
        Territory src = gameGraph.getVertex(button.getName());
        Territory dst;
        for(JButton b : buttons) {
            dst = gameGraph.getVertex(b.getName());
            if(!src.equals(dst)) {
                if(!gameGraph.hasEdge(src, dst) || button.getBackground().equals(b.getBackground())){
                    b.setForeground(b.getBackground());
                    b.setBackground(DISABLED_COLOR);
                }
            }
        }
    }

    private void resetButtons() {
        Graph gameGraph = game.getGameGraph();
        for(JButton b : buttons) {
            b.setBackground(gameGraph.getVertex(b.getName()).getOwner().getColor());
            b.setBorder(null);
            b.setForeground(Color.WHITE);
        }
        buttonClicked = false;
        attack = null;
        defend = null;
    }

    private void highlighButton(JButton button) {
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, BORDER_MARGIN));
        paintAdjacent(button);
        buttonClicked = true;
        attack = button;
    }
}
