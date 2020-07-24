package com.github.cc3002.citricliquid.controller.States;

public class EndTurnState extends State{

    @Override
    public boolean atEndTurn(){return true;}

    @Override
    public void goToStart(){this.changeState(new StartState());}

    @Override
    public void doYourThing(){
        controller.endTurn();
        goToStart();
    }
}

