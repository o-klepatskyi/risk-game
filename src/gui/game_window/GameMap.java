package gui.game_window;

import logic.*;
import util.res.Images;
import util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

// todo: fortifying with 1 troop
public class GameMap extends JPanel {
    private final Color DISABLED_COLOR = Color.LIGHT_GRAY;
    private final int BORDER_MARGIN = 5;

    private Graph gameGraph;
    private Game game;
    private ArrayList<JButton> buttons;
    private final JPanel panel;

    private boolean buttonClicked = false;
    private JButton src, dst;

    public GameMap(Game game) {
        this.setLayout(null);
        this.game = game;
        this.gameGraph = game.getGameGraph();
        panel = this;
        buttons = new ArrayList<>();
        drawField();

        panel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(!game.getGameOption().equals(GameOption.REINFORCEMENT)) {
                    resetButtons();
                    updateTerritories();
                }
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void updateTerritories() {
        game.getGameWindow().updateChosenTerritories(getSrcTerritory(), getDstTerritory());
    }

    /**
     * Call this method after any changes applied to map(attack, fortify, reinforcement)
     * Updates map
     */
    public void drawField(){
        clearField();
        buttons.clear();

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
            button.setBorder(null);

            panel.add(button);
            buttons.add(button);

        }
        if (game.isMultiplayer) {
            if (game.isCurrentPlayerActive()) {
                addListeners();
            }
        } else {
            addListeners();
        }

        if(game.getGameOption().equals(GameOption.REINFORCEMENT))
            showOptions(GameOption.REINFORCEMENT);
        if (game.getGameWindow() != null) {
            game.getGameWindow().updateChosenTerritories(getSrcTerritory(), getDstTerritory());
        }
    }

    /**
     *
     * @return territory from src button(by name)
     */
    public Territory getSrcTerritory() {
        if(src == null)
            return null;

        return gameGraph.getVertex(src.getName());
    }

    /**
     * @return territory from dst button(by name)
     */
    public Territory getDstTerritory() {
        if(dst == null)
            return null;

        return gameGraph.getVertex(dst.getName());
    }

    public void explosionEffect(Coordinates coordinates) {
        SoundPlayer.explosionSound();
        String territoryName = gameGraph.getVertex(coordinates).getName();
        JButton territoryButton = null;
        for(JButton button : buttons) {
            if(button.getName().equals(territoryName)) {
                territoryButton = button;
                break;
            }
        }
        if(territoryButton != null) {
            int SIZE = 50;
            ImageIcon explosionIcon = new ImageIcon(Images.EXPLOSION);
            JLabel explosion = new JLabel(explosionIcon);
            explosion.setBounds(coordinates.getX()-SIZE/2, coordinates.getY()-SIZE/2, SIZE, SIZE);
            territoryButton.setVisible(false);
            panel.add(explosion);

            JButton finalTerritoryButton = territoryButton;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            explosion.setVisible(false);
                            finalTerritoryButton.setVisible(true);
                        }
                    },
                    500
            );
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Images.GAME_MAP_BG, 0, 0,getWidth(),getHeight(), this);
        g.drawImage(Images.MAP_EARTH, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private void clearField() {
        resetButtons();
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    private void addListeners() {
        for(JButton button : buttons){
            button.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if(!buttonClicked && button.getBackground().equals(game.getCurrentPlayer().getColor())) {
                        resetButtons();
                        highlightButton(button, "src");
                        showOptions(game.getGameOption());
                        SoundPlayer.territoryClickedSound();
                    }
                    else {
                        if(button.getBackground() == DISABLED_COLOR) {
                            if( button.getForeground() == game.getCurrentPlayer().getColor()) {
                                if(!button.equals(src)) {
                                    resetButtons();
                                    highlightButton(button, "src");
                                    showOptions(game.getGameOption());
                                    SoundPlayer.territoryClickedSound();
                                }
                                else
                                    resetButtons();
                            }
                        } else {
                            if(game.getGameOption().equals(GameOption.REINFORCEMENT)) {
                                resetButtons();
                                highlightButton(button, "src");
                                showOptions(game.getGameOption());
                                SoundPlayer.territoryClickedSound();
                            }
                            else {
                                if (!button.equals(src) && src != null) {
                                    if (dst != null)
                                        dst.setBorder(null);
                                    if (dst != button) {
                                        highlightButton(button, "dst");
                                        SoundPlayer.territoryClickedSound();
                                    }
                                    else {
                                        button.setBorder(null);
                                        dst = null;
                                    }
                                }
                                else {
                                    resetButtons();
                                }
                            }
                        }
                    }
                    updateTerritories();
                }

                public void mousePressed(MouseEvent e) {

                }

                public void mouseReleased(MouseEvent e) {

                }

                public void mouseEntered(MouseEvent e) {
                    if(src == null || button.getBackground().equals(DISABLED_COLOR)) {
                        if(button.getBackground() == game.getCurrentPlayer().getColor()) {
                            resetButtons();
                            button.setBorder(BorderFactory.createLineBorder(Color.WHITE, BORDER_MARGIN));
                            showOptions(game.getGameOption());
                            SoundPlayer.territoryChosenSound();
                        }
                        else if(button.getForeground() == game.getCurrentPlayer().getColor()) {
                            button.setBorder(BorderFactory.createLineBorder(Color.WHITE, BORDER_MARGIN));
                            SoundPlayer.territoryChosenSound();
                        }
                    }
                    else {
                        if(src != button) {
                            SoundPlayer.territoryChosenSound();
                            if(game.getGameOption().equals(GameOption.REINFORCEMENT))
                                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, BORDER_MARGIN));
                            else
                                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_MARGIN));
                        }
                    }
                }

                public void mouseExited(MouseEvent e) {
                    if(!buttonClicked && !game.getGameOption().equals(GameOption.REINFORCEMENT))
                        resetButtons();
                    else {
                        if(!button.equals(src) && !button.equals(dst))
                            button.setBorder(null);
                    }
                }
            });
        }
    }


    private void showOptions(GameOption option){
        if(src != null || option.equals(GameOption.REINFORCEMENT)) {
            ArrayList<Territory> filter = new ArrayList<>();
            Territory territory = null;
            if(src != null)
                territory = gameGraph.getVertex(src.getName());
            switch (option) {
                case REINFORCEMENT:
                    filter = gameGraph.getTerritories(game.getCurrentPlayer());
                    break;
                case ATTACK:
                    filter = gameGraph.getAdjacentTerritories(territory);
                    break;
                case FORTIFY:
                    filter = gameGraph.getConnectedTerritories(territory);
                    break;

            }

            Territory dst;
            for(JButton b : buttons) {
                dst = gameGraph.getVertex(b.getName());
                if(!filter.contains(dst)){
                    makeButtonInactive(b);
                }
            }
        }
    }

    private void resetButtons() {
        for(JButton b : buttons) {
            b.setBackground(gameGraph.getVertex(b.getName()).getOwner().getColor());
            b.setBorder(null);
            b.setForeground(Color.WHITE);
        }
        buttonClicked = false;
        src = null;
        dst = null;
    }

    private void highlightButton(JButton button, String purpose) {
        switch (purpose) {
            case "src":
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, BORDER_MARGIN));
                buttonClicked = true;
                src = button;
                break;
            case "dst":
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_MARGIN));
                dst = button;
        }
    }

    private void makeButtonInactive(JButton button) {
        button.setForeground(button.getBackground());
        button.setBackground(DISABLED_COLOR);
    }
}
