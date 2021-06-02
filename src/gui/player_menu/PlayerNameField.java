package gui.player_menu;

import util.Fonts;
import gui.HintTextField;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

class PlayerNameField extends HintTextField {

    private static final int MAX_CHARACTERS = 20;

    public PlayerNameField(final String hint) {
        super(hint);
        // uncomment all lines to make this field nontransparent
        //setForeground(HINT_TEXT_COLOR);
        //setOpaque(false);
        setFont(Fonts.FIELD_FONT);
        setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null)
                    return;

                if ((getLength() + str.length()) <= MAX_CHARACTERS) {
                    super.insertString(offset, str, attr);
                }
            }
        });
        setText(hint);
    }

    public String getPlayerName() {
        return super.getText().isEmpty() ? getHint() : super.getText();
    }



//    @Override
//    public void focusGained(FocusEvent e) {
//        if(this.getText().isEmpty()) {
//            super.setText("");
//            showingHint = false;
//            setForeground(REGULAR_TEXT_COLOR);
//        }
//    }
//
//    @Override
//    public void focusLost(FocusEvent e) {
//        if(this.getText().isEmpty()) {
//            super.setText(hint);
//            showingHint = true;
//            setForeground(HINT_TEXT_COLOR);
//        }
//    }

    private static final Color REGULAR_TEXT_COLOR = new Color(0, 0, 0);
    private static final Color HINT_TEXT_COLOR = new Color(179, 179, 179);
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255, 64);
}
