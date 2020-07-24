package com.github.cc3002.citricliquid.controller.States;

public class IDLENextPanelState extends State {
    @Override
    public boolean atIDLENextPanel() {return true;
    }
    @Override
    public void goToMovePlayer() { this.changeState(new MovePlayerState());
    }


}
