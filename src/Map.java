import javax.swing.*;
import java.awt.*;

public class Map extends JPanel {

    public Map() {
        setBackground(new Color(0, 145, 182));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backgroundImage = new ImageIcon("res/map.png");
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
