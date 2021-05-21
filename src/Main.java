import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new Menu(frame));
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
