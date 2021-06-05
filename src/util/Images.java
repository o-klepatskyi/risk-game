package util;

import javax.swing.*;
import java.awt.*;

public abstract class Images {

    public static final Image MAIN_MENU_BG      = new ImageIcon(Images.class.getResource("res/images/main-menu-logo.jpg")).getImage();
    public static final Image MENU_PANEL        = new ImageIcon(Images.class.getResource("res/images/panel-map.jpg")).getImage();
    public static final Image SMALL_LOGO        = new ImageIcon(Images.class.getResource("res/images/logo-small.png")).getImage();
    public static final Image EXPLOSION         = new ImageIcon(Images.class.getResource("res/images/explosion.png")).getImage();
    public static final Image MAP_EARTH         = new ImageIcon(Images.class.getResource("res/images/map-earth.png")).getImage();
    public static final Image PLAYER_MENU_BG    = new ImageIcon(Images.class.getResource("res/images/player-menu-bg.jpg")).getImage();
    public static final Image SIDE_PANEL_BG     = new ImageIcon(Images.class.getResource("res/images/side-panel-bg.jpg")).getImage();
    public static final Image GAME_MAP_BG       = new ImageIcon(Images.class.getResource("res/images/water.jpg")).getImage();
    public static final Image DISABLED_CHECKBOX = new ImageIcon(Images.class.getResource("res/images/disabledIcon.jpg")).getImage();
    public static final Image SELECTED_CHECKBOX = new ImageIcon(Images.class.getResource("res/images/selectedIcon.png")).getImage();
}
