import gui.menus.main_menu.MainMenu;
import util.res.SoundPlayer;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        openMainMenu();
        SoundPlayer.menuBackgroundMusic();
    }

    private static void openMainMenu() {
        JFrame frame = new JFrame();
        JPanel menu = new MainMenu(frame);
        frame.add(menu);
        frame.pack();
        frame.setVisible(true);
        menu.setFocusable(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
