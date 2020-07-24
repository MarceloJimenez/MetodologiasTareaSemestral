package com.github.cc3002.citricliquid.model.board;

import com.github.cc3002.citricliquid.model.unit.Player;
import org.jetbrains.annotations.NotNull;
/**
 * Class that represents a Drop panel in the board of the game.
 * When a player activates this panel gets a penalty in his star count.
 * @author Marcelo Jimenez
 */
public class DropPanel extends AbstractPanel{
    /**
     * Creates a new Drop panel.
     *
     * @param id
     */
        public DropPanel(int id){
            super(id);
        }

    /**
     * Reduces the player's star count by the D6 roll multiplied by the player's norma level.
     */
    public static void applyDropTo(final @NotNull Player player) {
        player.reduceStarsBy(player.roll() * player.getNormaLevel());
    }

    @Override
    public void activatedBy(Player player) {
        this.applyDropTo(player);
    }
}
