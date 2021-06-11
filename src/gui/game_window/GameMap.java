package gui.game_window;

import logic.*;
import logic.maps.MapType;
import util.res.Images;
import util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameMap extends JPanel {
    private final Color DISABLED_COLOR = Color.LIGHT_GRAY;
    private final int BORDER_MARGIN = 5;

    private final Game game;
    private final ArrayList<JButton> buttons;
    private final JPanel panel;

    private boolean buttonClicked = false;
    private JButton src, dst;

    public GameMap(Game game) {
        this.setLayout(null);
        setPreferredSize(new Dimension((int) (GameWindow.WIDTH*0.75), (int) (GameWindow.HEIGHT*0.9)));
        this.game = game;
        panel = this;
        buttons = new ArrayList<>();

        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX() + ", " + e.getY());
                if(!game.getGamePhase().equals(GamePhase.REINFORCEMENT) && !game.getCurrentPlayer().isBot()) {
                    resetButtons();
                    updateTerritories();
                }
            }
        });

        drawField();
    }

    private void updateTerritories() {
        game.getGameWindow().updateChosenTerritories(getSrcTerritory(), getDstTerritory());
    }

    /**
     * Call this method after any changes applied to map(attack, fortify, reinforcement)
     * Updates map
     */
    public synchronized void drawField(){
        clearField();
        buttons.clear();

        ArrayList<Territory> territories = game.getGameGraph().getTerritories();
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
        if (game.isStarted()) {
            if (game.isMultiplayer) {
                if (game.isCurrentPlayerActive()) {
                    addListeners();
                }
            } else {
                if (!game.getCurrentPlayer().isBot()) {
                    addListeners();
                }
            }

            if(game.getGamePhase().equals(GamePhase.REINFORCEMENT))
                showOptions(GamePhase.REINFORCEMENT);
            if (game.getGameWindow() != null) {
                game.getGameWindow().updateChosenTerritories(getSrcTerritory(), getDstTerritory());
            }
        }

        revalidate();
        repaint();
    }

    /**
     *
     * @return territory from src button(by name)
     */
    public Territory getSrcTerritory() {
        if(src == null)
            return null;

        return game.getGameGraph().getVertex(src.getName());
    }

    /**
     * @return territory from dst button(by name)
     */
    public Territory getDstTerritory() {
        if(dst == null)
            return null;

        return game.getGameGraph().getVertex(dst.getName());
    }

    public void explosionEffect(Coordinates coordinates) {
        SoundPlayer.explosionSound();
        String territoryName = game.getGameGraph().getVertex(coordinates).getName();
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
        g.drawImage(Images.getMapImage(MapType.getByName(game.map.getName())), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private void clearField() {
        resetButtons();
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    private void addListeners() {
        for(JButton button : buttons){
            button.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if(!buttonClicked && button.getBackground().equals(game.getCurrentPlayer().getColor())) {
                        resetButtons();
                        highlightButton(button, "src");
                        showOptions(game.getGamePhase());
                        SoundPlayer.territoryClickedSound();
                    }
                    else {
                        if(button.getBackground().equals(DISABLED_COLOR)) {
                            if(button.getForeground().equals(game.getCurrentPlayer().getColor())) {
                                if(!button.equals(src)) {
                                    resetButtons();
                                    highlightButton(button, "src");
                                    showOptions(game.getGamePhase());
                                    SoundPlayer.territoryClickedSound();
                                } else
                                    resetButtons();
                            }
                        } else {
                            if(game.getGamePhase().equals(GamePhase.REINFORCEMENT)) {
                                resetButtons();
                                highlightButton(button, "src");
                                showOptions(game.getGamePhase());
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

                public void mouseEntered(MouseEvent e) {
                    if(src == null || button.getBackground().equals(DISABLED_COLOR)) {
                        if(button.getBackground() == game.getCurrentPlayer().getColor()) {
                            resetButtons();
                            button.setBorder(BorderFactory.createLineBorder(Color.WHITE, BORDER_MARGIN));
                            showOptions(game.getGamePhase());
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
                            if(game.getGamePhase().equals(GamePhase.REINFORCEMENT))
                                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, BORDER_MARGIN));
                            else
                                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_MARGIN));
                        }
                    }
                }

                public void mouseExited(MouseEvent e) {
                    if(!buttonClicked && !game.getGamePhase().equals(GamePhase.REINFORCEMENT))
                        resetButtons();
                    else {
                        if(!button.equals(src) && !button.equals(dst))
                            button.setBorder(null);
                    }
                }
            });
        }
    }


    private void showOptions(GamePhase option){
        if (game.isMultiplayer && !game.isCurrentPlayerActive()) return;
        if (game.getCurrentPlayer().isBot()) return;
        if(src != null || option.equals(GamePhase.REINFORCEMENT)) {
            ArrayList<Territory> filter = new ArrayList<>();
            Territory territory = null;
            if(src != null)
                territory = game.getGameGraph().getVertex(src.getName());
            switch (option) {
                case REINFORCEMENT:
                    filter = game.getGameGraph().getTerritories(game.getCurrentPlayer());
                    break;
                case ATTACK:
                    filter = game.getGameGraph().getAdjacentTerritories(territory);
                    break;
                case FORTIFY:
                    filter = game.getGameGraph().getConnectedTerritories(territory);
                    break;

            }

            Territory dst;
            for(JButton b : buttons) {
                dst = game.getGameGraph().getVertex(b.getName());
                if(!filter.contains(dst)){
                    makeButtonInactive(b);
                }
            }
        }
    }

    private void resetButtons() {
        for(JButton b : buttons) {
            b.setBackground(game.getGameGraph().getVertex(b.getName()).getOwner().getColor());
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
