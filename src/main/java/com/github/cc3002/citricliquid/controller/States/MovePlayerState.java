package com.github.cc3002.citricliquid.controller.States;


public class MovePlayerState extends State {
    @Override
    public boolean atMovePlayer(){return true;}


    @Override
    public void goToIDLEBattle(){this.changeState(new IDLEBattleState());}

    @Override
    public void goToIDLEHome(){this.changeState(new IDLEHomeState());}

    @Override
    public void goToIDLENextPanel(){this.changeState(new IDLENextPanelState());}

    @Override
    public void goToMovePlayer(){this.changeState(new MovePlayerState());}

    @Override
    public void goToPanelEffect(){this.changeState(new PanelEffectState());}

    public void doYourThing(){

        if(controller.getMovesLeft()>0) {
            controller.movePlayer(controller.getMovesLeft());
            controller.setMovesLeft(0);
        }
        else{controller.movePlayer();}




    }

}
