package com.github.cc3002.citricliquid.controller.States;

/**
 * State that ask the User whether wants to battle the other player or keep moving
 */
public class IDLEBattleState extends State {

    @Override
    public boolean atIDLEBattle(){return true;}

    @Override
    public void goToMovePlayer(){this.changeState(new MovePlayerState());}

    @Override
    public void goToBattle(){this.changeState(new BattleState());}

    public boolean wannaBattle(){return false;}

    @Override
    public void doYourThing(){
        /*
        if(wannaBattle())goToBattle();
        else {
            goToMovePlayer();
            controller.movePlayer();

        }         */

    }
}
