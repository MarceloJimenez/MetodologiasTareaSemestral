package com.github.cc3002.citricliquid.controller.States;

import com.github.cc3002.citricliquid.model.unit.Player;

public class RecoveryState extends State {

    @Override
    public boolean atRecovery(){return true;}

    @Override
    public void goToEndTurn(){this.changeState(new EndTurnState());}

    @Override
    public void goToStart(){this.changeState(new StartState());}

    @Override
    public void doYourThing(){
        int recoveryRoll = Math.max(7 - controller.getChapter(),0);
        Player turnOwner = controller.getTurnOwner();
        if (turnOwner.roll()>= recoveryRoll ){
            turnOwner.setCurrentHP(turnOwner.getMaxHP());
            goToStart();
            controller.stateDoYourThing();
        }
        else goToEndTurn();
    }
}
