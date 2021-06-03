package gui.player_menu;

import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class PlayerPanel extends JPanel {
    private static final int WIDTH = PlayerMenu.WIDTH-50;
    private static final int HEIGHT = PlayerMenu.HEIGHT/10;

    private PlayerNameField playerNameField;
    private final ColorComboBox colorComboBox;
    private JCheckBox botCheckBox;
    private JButton removePlayerButton;
    private static int playerNumber = 1;
    private final PlayerMenu parent;

    private final Font TEXTFIELD_FONT = new Font("Footlight MT Light", Font.PLAIN, HEIGHT-20);
    private final Font BUTTON_FONT  = new Font("Footlight MT Light", Font.PLAIN, HEIGHT-32);

    private final ColorModel colorModel;


    PlayerPanel(PlayerMenu parent, final ColorModel colorModel) {
        this.parent = parent;
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        setOpaque(false);
        setBackground(new Color(255, 255, 255, 123));

        this.colorModel = colorModel;

        add(getPlayerNameField());

        colorComboBox = new ColorComboBox(colorModel);
        colorComboBox.setPreferredSize(new Dimension((int) (WIDTH * (3.0 / 16.0)), HEIGHT - 10));
        colorComboBox.setRenderer(new ColorComboBoxRenderer());
        add(colorComboBox);
        colorModel.addComboBox(colorComboBox);



        add(getBotCheckBox());


        add(getRemovePlayerButton());
        validate();
        setVisible(true);
    }

    public JCheckBox getBotCheckBox() {
        if (botCheckBox == null) {
            botCheckBox = new JCheckBoxCustomIcon(WIDTH / 8 - 20,HEIGHT - 10);
        }
        return botCheckBox;
    }

    public JTextField getPlayerNameField() {
        if (playerNameField == null) {
            playerNameField = new PlayerNameField("Player " + playerNumber++);
            playerNameField.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT-10));
            playerNameField.setFont(TEXTFIELD_FONT);
        }
        return playerNameField;
    }

    protected void paintComponent(Graphics g)
    {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public ColorComboBox getColorComboBox() {
        return colorComboBox;
    }

    public JButton getRemovePlayerButton() {
        if (removePlayerButton == null) {
            removePlayerButton = new JButton("Remove player");
            removePlayerButton.setFont(BUTTON_FONT);
            removePlayerButton.setHorizontalAlignment(SwingConstants.CENTER);
            removePlayerButton.setPreferredSize(new Dimension((int) (WIDTH * (3.0 / 16.0)), HEIGHT - 10));
            removePlayerButton.setEnabled(false);
            PlayerPanel currentPanel = this;
            removePlayerButton.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    parent.removePlayer(currentPanel);
                }
            });
        }
        return removePlayerButton;
    }

    public Player getPlayerInfo() {
        return new Player(playerNameField.getPlayerName(), colorComboBox.getOldSelectedItem(), botCheckBox.isSelected());
    }
}
