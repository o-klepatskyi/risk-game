package com.risk.gui.player_menu;

import com.risk.gui.common_elements.HintTextField;

public class PlayerNameField extends HintTextField {

    public static final int MAX_CHARACTERS = 15;

    public PlayerNameField(final String hint) {
        super(hint, MAX_CHARACTERS);
    }

    String getPlayerName() {
        return super.getText().isEmpty() ? getHint() : super.getText();
    }
}
