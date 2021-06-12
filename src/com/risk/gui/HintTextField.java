package com.risk.gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


/**
 * @author https://stackoverflow.com/a/1739037
 */
public class HintTextField extends JTextField implements FocusListener {

    protected final String hint;
    protected boolean showingHint;

    public HintTextField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }

    public HintTextField (final String hint, final int maxCharacters) {
        this(hint);
        setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null)
                    return;

                if ((getLength() + str.length()) <= maxCharacters) {
                    super.insertString(offset, str, attr);
                }
            }
        });
        setText(hint);
        showingHint = true;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }

    public String getHint() {return hint;}

    @Override
    public void setText(String t) {
        super.setText(t);
        showingHint = false;
    }
}
