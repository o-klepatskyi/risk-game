import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Menu menu = new Menu(frame);
        menu.setFocusable(true);
        frame.add(menu);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
