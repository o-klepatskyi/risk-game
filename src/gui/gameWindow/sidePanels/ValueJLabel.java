package gui.gameWindow.sidePanels;

import javax.swing.*;
import java.awt.*;

/**
 * Class for quicker change in JLabel.
 */
class ValueJLabel extends JLabel {
    private String text, value;

    public ValueJLabel() {
        this("<none>");
    }

    public ValueJLabel(String text, String value) {
        this.text = text;
        this.value = value;
        setText(text+ " " + value);
    }

    public ValueJLabel(String text) {
        this(text, "<none>");
    }

    public ValueJLabel(String text, int playerBonus) {
        this(text, String.valueOf(playerBonus));
    }

    public String getTextWithoutValue() {
        return text;
    }

    public void setTextWithoutValue(String text) {
        this.text = text;
        setText(text + " " + value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        setText(text + " " + value);
    }

    public void setValue(int value) {
        setValue(String.valueOf(value));
    }

    public void clear() {
        text = "<none>";
        value = "<none>";
        setText(text + " " + value);
    }
}
