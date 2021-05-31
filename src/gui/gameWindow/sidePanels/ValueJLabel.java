package gui.gameWindow.sidePanels;

import javax.swing.*;

/**
 * Class for quicker change in JLabel.
 */
class ValueJLabel extends JLabel {
    private String text, value;

    public ValueJLabel(String text, String value) {
        this.text = text;
        this.value = value;
        setText(text+ " " + value);
    }

    public ValueJLabel(String text) {
        this(text, "<none>");
    }

    public String getTextWithoutValue() {
        return text;
    }

    public void setTextWithoutValue() {
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
}
