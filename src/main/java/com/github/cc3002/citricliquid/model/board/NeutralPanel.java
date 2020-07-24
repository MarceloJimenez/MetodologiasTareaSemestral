package com.github.cc3002.citricliquid.model.board;

import com.github.cc3002.citricliquid.model.unit.Player;
/**
 * Class that represents an Neutral panel in the board of the game.
 * This panel does nothing.
 * @author Marcelo Jimenez
 */
public class NeutralPanel extends AbstractPanel {
    /**
     * Creates a new panel.
     *
     */
    public NeutralPanel(int id){
        super(id);
    }

    @Override
    public void activatedBy(Player player) {
    }
}
