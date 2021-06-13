package com.risk.util.resources;

import java.awt.*;
import java.io.*;

public abstract class Fonts {
    public static final float DEFAULT_SIZE = 25f;

    public static Font LABEL_FONT;
    public static Font BUTTON_FONT;
    public static Font FIELD_FONT = new Font("Serif", Font.BOLD, 20);

    static {
        try {
            // create fonts
            LABEL_FONT = Font.createFont(Font.TRUETYPE_FONT, getInputStream(("fonts/ITCBLKAD.TTF"))).deriveFont(DEFAULT_SIZE).deriveFont(Font.BOLD);
            BUTTON_FONT = Font.createFont(Font.TRUETYPE_FONT, getInputStream("fonts/FTLTLT.TTF")).deriveFont(DEFAULT_SIZE);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(LABEL_FONT);
            ge.registerFont(BUTTON_FONT);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private static InputStream getInputStream(String path) throws IOException {
        return Fonts.class.getResourceAsStream(path);
    }

}
