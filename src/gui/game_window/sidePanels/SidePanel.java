package gui.game_window.sidePanels;

import gui.MainFrame;
import gui.menus.main_menu.MainMenu;
import logic.Territory;
import logic.network.MultiplayerManager;
import logic.network.NetworkMode;
import util.res.Fonts;
import gui.game_window.GameWindow;
import util.res.Images;
import util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidePanel extends JPanel {
    private final int WIDTH = (int) (GameWindow.WIDTH*0.25);
    private final int HEIGHT = (int) (GameWindow.HEIGHT*0.9);
    protected final int LABEL_WIDTH = WIDTH, LABEL_HEIGHT = 30;

    protected final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont((float) LABEL_HEIGHT);
    protected final Font BUTTON_FONT = Fonts.BUTTON_FONT.deriveFont((float) LABEL_HEIGHT-5);

    protected Territory src, dst;
    protected final GameWindow gameWindow;
    protected final MultiplayerManager manager;

    protected final Color FONT_COLOR = Color.white;


    public SidePanel(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.manager = gameWindow.game.manager;
        setOpaque(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0,10)));
        add(getLeaveButton());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Images.SIDE_PANEL_BG, 0, 0,WIDTH, HEIGHT+100, this);
    }

    public void updateTerritories(Territory src, Territory dst) {
        this.src = src;
        this.dst = dst;
    }

    protected JButton getLeaveButton() {
        JButton leave = new JButton("Leave game");
        leave.setFont(Fonts.BUTTON_FONT);
        leave.setPreferredSize(new Dimension(WIDTH/3, LABEL_HEIGHT));
        leave.setAlignmentX(0.5f);
        leave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoundPlayer.buttonClickedSound();
                String msg = "Are you sure you want to leave game?";
                if (manager!= null && manager.networkMode == NetworkMode.SERVER) {
                    msg += "\nYou are the server, and all progress of the game will be lost!";
                }
                Object[] options = {"Stay in game",
                        "Leave"};
                int n = JOptionPane.showOptionDialog(null,
                        msg,
                        "Leave confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if (n != 0) {
                    if (manager!= null) manager.closeClient();
                    else {
                        MainFrame.openMainMenu();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
            }
        });
        return leave;
    }
}
