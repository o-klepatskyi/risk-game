package util;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Fonts {
    public static final float DEFAULT_SIZE = 25f;

    public static Font LABEL_FONT; // = new Font("Blackadder ITC", Font.BOLD, 25);
    public static Font BUTTON_FONT; //  = new Font("Footlight MT Light", Font.PLAIN, 25);
    public static Font FIELD_FONT = new Font("Serif", Font.BOLD, 20);

    static {
        try {
            // create fonts
            LABEL_FONT = Font.createFont(Font.BOLD, new File("res/fonts/ITCBLKAD.TTF")).deriveFont(DEFAULT_SIZE);
            BUTTON_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/FTLTLT.TTF")).deriveFont(DEFAULT_SIZE);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(LABEL_FONT);
            ge.registerFont(BUTTON_FONT);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
