package com.github.cc3002.citricliquid.model.board;

import com.github.cc3002.citricliquid.model.unit.Player;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeSupport;

/**
 * Class that represents a Bonus panel in the board of the game.
 * When a player activates this panel gets a star's bonus.
 * @author Marcelo Jimenez
 */
public class BonusPanel extends AbstractPanel{
    /**
     * Creates a new Bonus panel.
     *
     * @param id
     */
    public BonusPanel(int id){
        super(id);
    }

    /**
     * Increases the Star count of the player according to the
     * following rule: min{roll · Norma, 3 · roll}
     */
    public static void applyBonusTo(final @NotNull Player player) {
        player.increaseStarsBy(player.roll() * Math.min(player.getNormaLevel(), 3));
    }


    @Override
    public void activatedBy(Player player) {
         this.applyBonusTo( player);

    }
}
