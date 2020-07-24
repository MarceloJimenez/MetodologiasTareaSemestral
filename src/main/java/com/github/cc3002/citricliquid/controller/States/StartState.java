package com.github.cc3002.citricliquid.controller.States;

import com.github.cc3002.citricliquid.gui.AlertBox;

public class StartState extends State {


    @Override
    public boolean atStart() {
        return true;
    }

    @Override
    public void goToRecovery() {
        this.changeState(new RecoveryState());
    }
    @Override
    public void goToMovePlayer(){this.changeState(new MovePlayerState());}

    @Override
    public void doYourThing() {
        AlertBox.display("Inicio de turno", controller.getTurnOwner().getName() + " es tu turno !");

        if (controller.getTurnOwner().isKO()) {
            goToRecovery();
            controller.stateDoYourThing();
        }
        else {
            int stars = (int)Math.floor(controller.getChapter()/5)+1;
            controller.getTurnOwner().increaseStarsBy(stars);
            AlertBox.display("Bonus de HomePanel", controller.getTurnOwner().getName() + " ganaste " + stars + " estrellas !!!");
            goToMovePlayer();
            controller.stateDoYourThing();
               }


    }
}