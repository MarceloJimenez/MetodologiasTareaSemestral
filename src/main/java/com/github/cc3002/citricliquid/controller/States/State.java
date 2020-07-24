package com.github.cc3002.citricliquid.controller.States;

import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.model.unit.Player;
import org.jetbrains.annotations.NotNull;

/**
 * State Class from which the concrete States of the game will inherit.
 * This implementation of the State Pattern Design follows the example viewed in class(without an interface or Abstract class)
 */
public class State {
protected GameController controller;

public void setController(GameController controller){
    this.controller = controller;
}

    /**
     * calls the controller setState method
     * @param state
     */
    public void changeState(@NotNull State state){controller.setState(state);
}

private void error(String message) throws InvalidTransitionException {
    throw new InvalidTransitionException(message);
}

public void goToStart() throws InvalidTransitionException {
    error("Can't go to Start state from current state");}
    public void goToRecovery() throws InvalidTransitionException {
    error("Can't go to Recovery state from current state");}
public void goToMovePlayer() throws InvalidTransitionException {
    error("Can't go to Move Player state from current state");}
    public void goToIDLEHome() throws InvalidTransitionException {
        error("Can't go to IDLEHome state from current state");}
    public void goToIDLENextPanel() throws InvalidTransitionException {
        error("Can't go to IDLENextPanel state from current state");}
    public void goToIDLEBattle() throws InvalidTransitionException {
        error("Can't go to IDLEBattle state from current state");}
    public void goToBattle() throws InvalidTransitionException {
        error("Can't go to Battle state from current state");}
public void goToPanelEffect() throws InvalidTransitionException {
    error("Can't go to Panel Effect state from current state");}
public void goToEndTurn() throws InvalidTransitionException {
    error("Can't go to End Turn state from current state");}


public void doYourThing() {
    throw new RuntimeException("Can't do your thing");}

public boolean atStart(){return false;}
public boolean atRecovery(){return false;}
public boolean atMovePlayer(){return false;}
    public boolean atIDLEHome(){return false;}
    public boolean atIDLENextPanel(){return false;}
    public boolean atIDLEBattle() {return false;}
    public boolean atBattle(){return false;}
public boolean atPanelEffect(){return false;}
public boolean atEndTurn(){return false;}


}
