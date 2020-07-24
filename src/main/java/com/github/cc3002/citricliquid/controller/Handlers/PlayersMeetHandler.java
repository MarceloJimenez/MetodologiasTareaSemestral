package com.github.cc3002.citricliquid.controller.Handlers;

import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.model.unit.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class PlayersMeetHandler implements PropertyChangeListener {
    private GameController controller;

    public PlayersMeetHandler(GameController controller){this.controller = controller;}

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        controller.onMeetPlayer((ArrayList<Player>) event.getOldValue(),(Player)event.getNewValue());

    }
}
