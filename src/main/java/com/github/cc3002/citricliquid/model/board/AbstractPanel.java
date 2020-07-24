package com.github.cc3002.citricliquid.model.board;


import com.github.cc3002.citricliquid.controller.Handlers.PlayersMeetHandler;
import com.github.cc3002.citricliquid.model.unit.Player;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Abstract Class that represents the common features and methods of a panel in the board of the game.
 *
 * @author Marcelo Jimenez
 */
public abstract class AbstractPanel implements IPanel {
    protected final int id;
    private final Set<IPanel> nextPanels = new LinkedHashSet<>();
    private final Dictionary positions = new Hashtable();
    protected ArrayList<Player> steadyPlayers = new ArrayList<>();
    public PropertyChangeSupport PlayersMeetNotification = new PropertyChangeSupport(this);


    /**
     * Creates a new Abstract panel.
     *
     * @param id
     *     the id of the panel.
     */
    public AbstractPanel(final int id) {
        this.id = id;
    }

    /**
     * Executes the action related to each id of panel
     * @param player
     */
    public abstract void activatedBy(Player player);

    /**
     * Returns the id of this panel
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a copy of this panel's next ones.
     */
    @NotNull
    public Set<IPanel> getNextPanels() {
        return  Set.copyOf(nextPanels);
    }

    /**
     * Adds a new adjacent panel to this one.
     *
     * @param panel
     *     the panel to be added.
     */
    public void addNextPanel(final IPanel panel) {
        nextPanels.add(panel);
    }

    /**
     * S
     */

    /**
     * Returns the list of players that are currently in the panel
     * @return
     */
    @Override
    public ArrayList<Player> getPlayers() {return steadyPlayers;}

    /**
     * Adds a player to the panel
     * @param player
     */
    @Override
    public void addPlayer(Player player) {

        if(steadyPlayers.size()>0){
            PlayersMeetNotification.firePropertyChange(new PropertyChangeEvent(this, "More than one Player in this Panel",steadyPlayers,player));
        }
        steadyPlayers.add(player);

    }

    /**
     * Add's an observer
     */
    public void addObserver(PlayersMeetHandler handler){
        PlayersMeetNotification.addPropertyChangeListener(handler);
    }

    /**
     * Removes a player from the panel
     * @param player
      */
    @Override
    public void removePlayer(Player player){
        this.steadyPlayers.remove(player);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPanel that = (AbstractPanel) o;
        return id == that.id &&
                Objects.equals(nextPanels, that.nextPanels);
    }

    /**
     * Sets the next panel position relative to this one
     * @param position panel
     */
    public void setNPPosition(IPanel panel, NPPos position){
        positions.put(panel, position);

    }
    /**
     * Gets the next panel position relative to this one
     * @return
     */
    public ArrayList<NPPos> getNPPosition(){
        Iterator it = nextPanels.iterator();
        ArrayList<NPPos> listPositions = new ArrayList<>();
        while(it.hasNext()){
            listPositions.add((NPPos) positions.get(it.next()));
        }
        return listPositions;
    }


}
