package com.github.cc3002.citricliquid.model.board;


import com.github.cc3002.citricliquid.controller.Handlers.NormaClearHandler;
import com.github.cc3002.citricliquid.controller.Handlers.PlayerHomeHandler;
import com.github.cc3002.citricliquid.model.unit.Player;
import org.jetbrains.annotations.NotNull;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;


/**
 * Class that represents an Home panel in the board of the game.
 * When a player activates this panel his hit point's count restores by an amount.
 * @author Marcelo Jimenez
 */
public class HomePanel extends AbstractPanel{

    public PropertyChangeSupport NormaClearNotification = new PropertyChangeSupport(this);
    public PropertyChangeSupport PlayerHomeNotification = new PropertyChangeSupport(this);
    /**
     * Creates a new Home panel.
     *
     * @param id
     */
    public HomePanel(int id){
        super(id);
              }


    /**
     * Add's a Norma observer
     */
    public void addNormaObserver(NormaClearHandler handler){
        NormaClearNotification.addPropertyChangeListener(handler);
    }

    /**
     * Add's a PLayerHome observer
     */
    public void addPlayerHomeObserver(PlayerHomeHandler handler){
        PlayerHomeNotification.addPropertyChangeListener(handler);
    }


    /**
     * Adds a player to the panel
     * @param player
     */
    @Override
    public void addPlayer(Player player) {

        if(steadyPlayers.size()>0){
            PlayersMeetNotification.firePropertyChange(new PropertyChangeEvent(this, "More than one Player in this Panel",steadyPlayers,player));
        }
        if(this.equals(player.getHome())){
            PlayerHomeNotification.firePropertyChange(new PropertyChangeEvent(this,"Player at his HomePanel",null,player));
        }
        steadyPlayers.add(player);

    }



    /**
     * Restores a player's HP in amount.
     */
    public static void applyHealTo(final @NotNull Player player) {
        player.setCurrentHP(player.getCurrentHP() + 1);
    }

    @Override
    public void activatedBy(Player player) {
        this.applyHealTo(player);
        NormaClearNotification.firePropertyChange(new PropertyChangeEvent(this, "Home Panel Activated",null,player));
    }


    }

