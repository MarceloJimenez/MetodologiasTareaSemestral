package com.github.cc3002.citricliquid.controller.Handlers;

import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.model.unit.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayerHomeHandler implements PropertyChangeListener {
    private GameController controller;

    public PlayerHomeHandler(GameController controller){this.controller = controller;}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.onPlayerHome((Player) evt.getNewValue());

    }
}
