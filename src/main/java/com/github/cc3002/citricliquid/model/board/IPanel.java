package com.github.cc3002.citricliquid.model.board;

import com.github.cc3002.citricliquid.controller.Handlers.PlayersMeetHandler;
import com.github.cc3002.citricliquid.model.unit.Player;

import java.util.ArrayList;
import java.util.Set;
/**
 * This interface lists all methods available in the Panel clases.
 *
 * @author Marcelo Jimenez
 */

public interface IPanel {
    void activatedBy(Player player);
    int getId();
    Set<IPanel> getNextPanels();
    void addNextPanel(final IPanel panel);

    ArrayList<Player> getPlayers();
    void addPlayer(Player player);
    void removePlayer(Player player);
    void addObserver(PlayersMeetHandler handler);
    void setNPPosition(IPanel panel, NPPos position);
    ArrayList<NPPos> getNPPosition();


}
