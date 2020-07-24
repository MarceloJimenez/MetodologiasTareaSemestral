package com.github.cc3002.citricliquid.controller.Handlers;

import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.model.unit.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class NormaClearHandler implements PropertyChangeListener {
    private GameController controller;

    public NormaClearHandler(GameController controller){this.controller = controller;}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.onNormaClear((Player) evt.getNewValue());

    }
}
