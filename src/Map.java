import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Map extends JPanel {

    public Map(Game game) {
        this.setLayout(null);
        setBackground(new Color(0, 145, 182));
        JButton b = new JButton("15");
        b.setBounds(300, 300, 50, 25);
        b.setBackground(Color.YELLOW);
        add(b);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon backgroundImage = new ImageIcon("res/map.png");
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

}
