package com.risk.util.res;

import com.risk.logic.maps.MapType;

import javax.swing.*;
import java.awt.*;

public abstract class Images {

    public static final Image MAIN_MENU_BG      = new ImageIcon(Images.class.getResource("images/main-menu-logo.jpg")).getImage();
    public static final Image MENU_PANEL        = new ImageIcon(Images.class.getResource("images/panel-map.jpg")).getImage();
    public static final Image SMALL_LOGO        = new ImageIcon(Images.class.getResource("images/logo-small.png")).getImage();
    public static final Image EXPLOSION         = new ImageIcon(Images.class.getResource("images/explosion.png")).getImage();
    public static final Image PLAYER_MENU_BG    = new ImageIcon(Images.class.getResource("images/player-menu-bg.jpg")).getImage();
    public static final Image GAME_MAP_BG       = new ImageIcon(Images.class.getResource("images/water.jpg")).getImage();
    public static final Image DISABLED_CHECKBOX = new ImageIcon(Images.class.getResource("images/disabledIcon.jpg")).getImage();
    public static final Image SELECTED_CHECKBOX = new ImageIcon(Images.class.getResource("images/selectedIcon.png")).getImage();

    public static Image getSlideImage(int slide) {
        String path = "";
        switch (slide) {
            case 0:
                path = "images/main-menu-logo.jpg";
                break;
            default:
                path = "images/slides/slide" + slide + ".jpg";
        }
        return new ImageIcon(Images.class.getResource(path)).getImage();
    }

    public static Image getMapImage(MapType name) {
        String path = "images/maps/" + name + ".png";

        try {
            new ImageIcon(Images.class.getResource(path)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
            return GAME_MAP_BG;
        }

        return new ImageIcon(Images.class.getResource(path)).getImage();
    }

    public static Image getSidePanelBg(String name) {
        String path = "images/sidepanels/" + name + ".jpg";

        try {
            new ImageIcon(Images.class.getResource(path)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
            return GAME_MAP_BG;
        }

        return new ImageIcon(Images.class.getResource(path)).getImage();
    }
}
