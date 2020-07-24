package com.github.cc3002.citricliquid.controller.States;

import com.github.cc3002.citricliquid.model.board.IPanel;

public class PanelEffectState extends State {
    @Override
    public boolean atPanelEffect(){return true;}

    @Override
    public void goToEndTurn(){this.changeState(new EndTurnState());}
    @Override
    public void doYourThing(){
        IPanel panel = controller.getPlayerPanel(controller.getTurnOwner());
        panel.activatedBy(controller.getTurnOwner());
        goToEndTurn();
    }
}
