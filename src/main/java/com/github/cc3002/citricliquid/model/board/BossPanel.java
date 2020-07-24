package com.github.cc3002.citricliquid.model.board;

import com.github.cc3002.citricliquid.model.unit.Player;
/**
 * Class that represents a Boss panel in the board of the game.
 * When a player activates this panel he can face a Boss Unit.
 * @author Marcelo Jimenez
 */
public class BossPanel extends AbstractPanel{
    /**
     * Creates a new Boss panel.
     *
     * @param id
     */
    public BossPanel(int id) {
        super(id);
    }


    @Override
    public void activatedBy(Player player) {

    }
}
