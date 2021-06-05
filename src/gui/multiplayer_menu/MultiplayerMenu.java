package gui.multiplayer_menu;

import gui.main_menu.MainMenu;
import util.res.Fonts;
import util.res.Images;
import util.res.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
// todo add setKeyListener method to manipulate with mouse
public class MultiplayerMenu extends JPanel {

    private final JFrame frame;
    private final JPanel panel;
    private JButton serverButton, clientButton, backButton;
    private GridBagConstraints gbc;

    private final int SIZE = 500;
    private int menuOptionChosen;

    private static final Font LABEL_FONT = Fonts.LABEL_FONT.deriveFont(35f);

    public MultiplayerMenu(JFrame frame) {
        this.frame = frame;
        panel = this;

        initWindow();
        initButtons();

        //menuOptionChosen = 1;
        //highlightOption(menuOptionChosen);
    }

    private void initWindow() {
        setFocusable(true);
        setPreferredSize(new Dimension(SIZE,SIZE));
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(85,0,-70,0);
    }

    private void initButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();
        serverButton = new JButton("Create room");
        serverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openServerMenu();
                SoundPlayer.buttonClickedSound();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 1;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(serverButton);

        clientButton = new JButton("Connect");
        clientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openClientMenu();
                SoundPlayer.buttonClickedSound();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 2;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(clientButton);

        backButton = new JButton("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                back();
                SoundPlayer.buttonClickedSound();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SoundPlayer.optionChosenSound();
                menuOptionChosen = 3;
                highlightOption(menuOptionChosen);
            }
        });
        buttons.add(backButton);

        for (JButton button: buttons) {
            button.setFont(LABEL_FONT);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            add(button,gbc);
        }
    }

    private void highlightOption(int option) {
        resetButtons();
        switch (option) {
            case 1:
                serverButton.setForeground(Color.YELLOW);
                serverButton.setText("< Create room >");
                SoundPlayer.optionChosenSound();
                break;
            case 2:
                clientButton.setForeground(Color.YELLOW);
                clientButton.setText("< Connect >");
                SoundPlayer.optionChosenSound();
                break;
            case 3:
                backButton.setForeground(Color.YELLOW);
                backButton.setText("< Back >");
                SoundPlayer.optionChosenSound();
                break;
        }
    }

    private void resetButtons() {
        serverButton.setForeground(Color.WHITE);
        serverButton.setText("Create room");

        clientButton.setForeground(Color.WHITE);
        clientButton.setText("Connect");

        backButton.setForeground(Color.WHITE);
        backButton.setText("Back");
    }

    private void openServerMenu() {
        setVisible(false);
        frame.remove(this);
        frame.add(new ServerMenu(frame));
        frame.pack();
    }

    private void openClientMenu() {
        frame.remove(this);
        frame.add(new ClientMenu(frame));
        frame.revalidate();
        frame.repaint();
        frame.pack();
    }

    private void back() {
        panel.setVisible(false);
        frame.remove(panel);
        frame.add(new MainMenu(frame));
        frame.pack();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Images.MAIN_MENU_BG, 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(Images.MENU_PANEL, SIZE/6, SIZE/3 + SIZE/40, 3*SIZE/4 - SIZE / 12, SIZE/2+SIZE/20+SIZE/40, null);
    }
}

