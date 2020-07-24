package com.github.cc3002.citricliquid.controller.Handlers;

import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.model.board.IPanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

public class MultipleNextPanelsHandler implements PropertyChangeListener {
    private GameController controller;

    public MultipleNextPanelsHandler(GameController controller){this.controller = controller;}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    controller.onMultipleNextPanels((Set<IPanel>)evt.getNewValue());

    }
}
