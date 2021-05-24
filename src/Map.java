import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Map extends JPanel {

    private Game game;
    private ArrayList<JButton> buttons;
    private JPanel panel;

    private final int BUTTON_WIDTH = 50, BUTTON_HEIGHT = 25;
    private final int BUTTON_MARGIN_LEFT = 20, BUTTON_MARGIN_TOP = 10;

    public Map(Game game) {
        this.setLayout(null);
        this.game = game;
        panel = this;

        buttons = new ArrayList<>();
        addButtons();
        setBackground(new Color(0, 145, 182));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backgroundImage = new ImageIcon("res/map.png");
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

    private void addButtons(){
        Graph gameGraph = game.getGameGraph();
        ArrayList<Territory> territories = gameGraph.getTerritories();
        for(Territory territory : territories) {
            JButton button = new JButton(String.valueOf(territory.getTroops()));
            Coordinates territoryCoordinates = territory.getCoordinates();
            button.setBounds(territoryCoordinates.getX()-BUTTON_MARGIN_LEFT, territoryCoordinates.getY()-BUTTON_MARGIN_TOP,
                    BUTTON_WIDTH, BUTTON_HEIGHT);
            button.setForeground(Color.WHITE);
            button.setBackground(territory.getOwner().getColor());
            panel.add(button);
            buttons.add(button);
        }
    }

}
