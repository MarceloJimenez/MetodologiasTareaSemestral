package com.github.cc3002.citricliquid.controller;

import com.github.cc3002.citricliquid.model.board.*;

import java.util.ArrayList;

/**
 * Board builder Class that builds the board used in the implementation of the game
 */
public class BoardBuilder extends GameController{

    private ArrayList<IPanel> bonusPanels;
    private ArrayList<IPanel> bossPanels;
    private ArrayList<IPanel> dropPanels;
    private ArrayList<IPanel> encounterPanels;
    private ArrayList<IPanel> homePanels;
    private ArrayList<IPanel> neutralPanels;
    private ArrayList<IPanel> board;


    public BoardBuilder(GameController controller ){
        bonusPanels = new ArrayList<>();
        bossPanels = new ArrayList<>();
        dropPanels = new ArrayList<>();
        encounterPanels = new ArrayList<>();
        homePanels = new ArrayList<>();
        neutralPanels = new ArrayList<>();
        board = new ArrayList<>();
    }

    public IPanel getHomePanels(int num){
        return homePanels.get(num);
    }
    private void bonusBuilder(){
        for(int i = 1 ; i <= 12; i++ ){
            var panel = createBonusPanel(i);
            bonusPanels.add(panel);
            board.add(panel);
        }
    }

    private void dropBuilder(){
        for(int i = 1 ; i <= 12; i++ ){
            var panel = createDropPanel(i);
            dropPanels.add(panel);
            board.add(panel);
        }
    }
    private void encounterBuilder(){
        for(int i = 1 ; i <= 8; i++ ){
            var panel = createEncounterPanel(i);
            encounterPanels.add(panel);
            board.add(panel);
        }
    }
    private void bossBuilder(){
        for(int i = 1 ; i <= 4; i++ ){
            var panel = createBossPanel(i);
            bossPanels.add(panel);
            board.add(panel);
        }
    }
    private void homeBuilder(){
        for(int i = 1 ; i <= 4; i++ ){
            var panel = createHomePanel(i);
            homePanels.add(panel);
            board.add(panel);
        }
    }
    private void neutralBuilder(){
        for(int i = 1 ; i <= 4; i++ ){
            var panel = createNeutralPanel(i);
            neutralPanels.add(panel);
            board.add(panel);
        }
    }


    public ArrayList<IPanel> build(){
        bonusBuilder();
        dropBuilder();
        encounterBuilder();
        bossBuilder();
        homeBuilder();
        neutralBuilder();

        //First Squared UP LEFT CORNER
        setNextPanel(homePanels.get(0),bonusPanels.get(0),NPPos.LEFT);
        setNextPanel(bonusPanels.get(0),dropPanels.get(0),NPPos.LEFT);
        setNextPanel(dropPanels.get(0),encounterPanels.get(0),NPPos.UP);
        setNextPanel(encounterPanels.get(0),neutralPanels.get(0),NPPos.UP);
        setNextPanel(neutralPanels.get(0),bonusPanels.get(1),NPPos.RIGHT);
        setNextPanel(bonusPanels.get(1),dropPanels.get(1),NPPos.RIGHT);
        setNextPanel(dropPanels.get(1),bossPanels.get(0),NPPos.DOWN);
        setNextPanel(bossPanels.get(0),homePanels.get(0),NPPos.DOWN);




        //Second Squared DOWN LEFT CORNER
        setNextPanel(homePanels.get(1),bonusPanels.get(2),NPPos.DOWN);
        setNextPanel(bonusPanels.get(2),dropPanels.get(2),NPPos.DOWN);
        setNextPanel(dropPanels.get(2),encounterPanels.get(1),NPPos.LEFT);
        setNextPanel(encounterPanels.get(1),neutralPanels.get(1),NPPos.LEFT);
        setNextPanel(neutralPanels.get(1),bonusPanels.get(3),NPPos.UP);
        setNextPanel(bonusPanels.get(3),dropPanels.get(3),NPPos.UP);
        setNextPanel(dropPanels.get(3),bossPanels.get(1),NPPos.RIGHT);
        setNextPanel(bossPanels.get(1),homePanels.get(2),NPPos.RIGHT);

        //Third Squared DOWN RIGHT CORNER
        setNextPanel(homePanels.get(2),bonusPanels.get(4),NPPos.RIGHT);
        setNextPanel(bonusPanels.get(4),dropPanels.get(4),NPPos.RIGHT);
        setNextPanel(dropPanels.get(4),encounterPanels.get(2),NPPos.DOWN);
        setNextPanel(encounterPanels.get(2),neutralPanels.get(2),NPPos.DOWN);
        setNextPanel(neutralPanels.get(2),bonusPanels.get(5),NPPos.LEFT);
        setNextPanel(bonusPanels.get(5),dropPanels.get(5),NPPos.LEFT);
        setNextPanel(dropPanels.get(5),bossPanels.get(2),NPPos.UP);
        setNextPanel(bossPanels.get(2),homePanels.get(2),NPPos.UP);

        //Fourth Squared UP RIGHT CORNER
        setNextPanel(homePanels.get(3),bonusPanels.get(6),NPPos.UP);
        setNextPanel(bonusPanels.get(6),dropPanels.get(6),NPPos.UP);
        setNextPanel(dropPanels.get(6),encounterPanels.get(3),NPPos.RIGHT);
        setNextPanel(encounterPanels.get(3),neutralPanels.get(3),NPPos.RIGHT);
        setNextPanel(neutralPanels.get(3),bonusPanels.get(7),NPPos.DOWN);
        setNextPanel(bonusPanels.get(7),dropPanels.get(7),NPPos.DOWN);
        setNextPanel(dropPanels.get(7),bossPanels.get(3),NPPos.LEFT);
        setNextPanel(bossPanels.get(3),homePanels.get(3),NPPos.LEFT);

        //First column

        setNextPanel(bonusPanels.get(0),encounterPanels.get(4),NPPos.DOWN);
        setNextPanel(encounterPanels.get(4),dropPanels.get(8),NPPos.DOWN);
        setNextPanel(dropPanels.get(8),bonusPanels.get(8),NPPos.DOWN);
        setNextPanel(bonusPanels.get(8),bossPanels.get(1),NPPos.DOWN);



        //second column

        setNextPanel(bonusPanels.get(2),encounterPanels.get(5),NPPos.RIGHT);
        setNextPanel(encounterPanels.get(5),dropPanels.get(9),NPPos.RIGHT);
        setNextPanel(dropPanels.get(9),bonusPanels.get(9),NPPos.RIGHT);
        setNextPanel(bonusPanels.get(9),bossPanels.get(2),NPPos.RIGHT);

        //third column

        setNextPanel(bonusPanels.get(4),encounterPanels.get(6),NPPos.UP);
        setNextPanel(encounterPanels.get(6),dropPanels.get(10),NPPos.UP);
        setNextPanel(dropPanels.get(10),bonusPanels.get(10),NPPos.UP);
        setNextPanel(bonusPanels.get(10),bossPanels.get(3),NPPos.UP);

        //fourth column

        setNextPanel(bonusPanels.get(6),encounterPanels.get(7),NPPos.LEFT);
        setNextPanel(encounterPanels.get(7),dropPanels.get(11),NPPos.LEFT);
        setNextPanel(dropPanels.get(11),bonusPanels.get(11),NPPos.LEFT);
        setNextPanel(bonusPanels.get(11),bossPanels.get(0),NPPos.LEFT);

        return board;





    }
}
