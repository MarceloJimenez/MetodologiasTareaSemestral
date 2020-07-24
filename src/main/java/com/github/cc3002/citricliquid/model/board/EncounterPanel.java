package com.github.cc3002.citricliquid.model.board;

import com.github.cc3002.citricliquid.model.unit.Player;
/**
 * Class that represents an Encounter panel in the board of the game.
 * When a player activates this panel he can face an WildUnit Enemy.
 * @author Marcelo Jimenez
 */
public class EncounterPanel extends AbstractPanel{
    /**
     * Creates a new Encounter panel.
     *
     * @param id
     */
    public EncounterPanel(int id) {
        super(id);
    }

    @Override
    public void activatedBy(Player player) {

    }
}
